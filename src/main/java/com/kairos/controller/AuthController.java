package com.kairos.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kaijiang
 * @date 2025/10/11 11:23
 * @description
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController{
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        // TODO: 验证用户，生成 JWT，Set-Cookie
        return "Login success";
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }
}
