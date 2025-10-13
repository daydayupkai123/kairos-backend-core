package com.kairos.service.impl;

import com.kairos.repository.UserEntityRepository;
import com.kairos.request.LoginRequest;
import com.kairos.response.RestResponse;
import com.kairos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author kaijiang
 * @date 2025/10/13 10:40
 * @description 用户服务
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserEntityRepository userRepository;

    @Override
    public RestResponse<String> register(LoginRequest req) {
        // 用户验证
        validatRequest(req);
        // 密码加密
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        // 保存用户
        return null;
    }

    /**
     * 用户校验
     */
    private void validatRequest(LoginRequest req) {
        // 校验内容是否存在

        // 校验用户名是否存在

        // 校验邮箱是否存在


    }


}
