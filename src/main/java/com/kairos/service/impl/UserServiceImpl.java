package com.kairos.service.impl;

import com.kairos.entity.UserEntity;
import com.kairos.exception.BusinessException;
import com.kairos.repository.UserEntityRepository;
import com.kairos.request.LoginRequest;
import com.kairos.response.RestResponse;
import com.kairos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
        validRequest(req);
        // 密码加密
        req.setPassword(passwordEncoder.encode(req.getPassword()));
        // 保存用户
        UserEntity userEntity = UserEntity.create(req);
        userRepository.save(userEntity);
        return RestResponse.success("注册成功");
    }

    /**
     * 用户校验
     */
    private void validRequest(LoginRequest req) {
        // 校验内容是否存在
        Assert.hasText(req.getUsername(), "用户名不能为空");
        Assert.hasText(req.getEmail(), "邮箱不能为空");
        Assert.hasText(req.getPassword(), "密码不能为空");
        // 校验用户名是否存在
        boolean hasUsername = userRepository.containsByUsername(req.getUsername());
        if (hasUsername){
            throw new BusinessException("用户名已存在");
        }
        // 校验邮箱是否存在
        boolean haseEmail = userRepository.containsByEmail(req.getEmail());
        if (haseEmail){
            throw new BusinessException("邮箱已存在");
        }
    }

}
