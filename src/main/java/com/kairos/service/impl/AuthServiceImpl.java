package com.kairos.service.impl;

import com.kairos.entity.UserEntity;
import com.kairos.repository.UserEntityRepository;
import com.kairos.service.AuthService;
import com.kairos.service.UserService;
import com.kairos.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author kaijiang
 * @date 2025/10/13 14:18
 * @description 认证服务
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserEntityRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String USER_SESSION_KEY_PREFIX = "user_session:";

    public String login(String email, String rawPassword){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new RuntimeException("密码错误");
        }

        String newToken = jwtUtil.generateToken(user.getId());

        // 写入redis，覆盖旧token（自动实现"踢下线"）
        String sessionKey = USER_SESSION_KEY_PREFIX + user.getId();

        redisTemplate.opsForValue().set(
                sessionKey,
                newToken,
                jwtUtil.getExpirationTime(),
                TimeUnit.MILLISECONDS
        );

        return newToken;
    }

    public boolean isTokenValidForUser(String uerId, String token){
        String sessionKey = USER_SESSION_KEY_PREFIX + uerId;
        String storeToken = redisTemplate.opsForValue().get(sessionKey);
        return storeToken != null && storeToken.equals(token) && jwtUtil.validateToken(token);
    }

    public void logout(String userId){
        String sessionKey = USER_SESSION_KEY_PREFIX + userId;
        redisTemplate.delete(sessionKey);
    }

}
