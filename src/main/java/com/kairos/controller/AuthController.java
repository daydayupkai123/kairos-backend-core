package com.kairos.controller;

import com.kairos.filter.JwtAuthFilter;
import com.kairos.request.LoginRequest;
import com.kairos.response.RestResponse;
import com.kairos.service.AuthService;
import com.kairos.service.UserService;
import com.kairos.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
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
@Tag(name = "认证接口", description = "用户认证")
public class AuthController{

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口，成功后返回JWT Token并设置到Cookie中")
    public RestResponse<String> login(
            @RequestBody LoginRequest req,
            HttpServletResponse response) {
        // 登录
        String token = authService.login(req.getEmail(), req.getPassword());
        // 设置httpOnly + Secure + Path
        ResponseCookie cookie = ResponseCookie.from("kairos_token", token)
                .httpOnly(true)
                .secure(false) // 测试用，生产环境请设置为true
                .path("/")
                .maxAge(jwtUtil.getExpirationTime() / 1000) // 24小时
                .sameSite("Lax")
                .build();

        return RestResponse.success("Login success");
    }


    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出，设置token过期")
    public RestResponse<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtAuthFilter.extractTokenFromCookie(request);
        if (token != null && jwtUtil.validateToken(token)){
            String userId = jwtUtil.extractUserId(token);
            authService.logout(userId);
        }
        // 清除cookie
        ResponseCookie cookie = ResponseCookie.from("kairos_token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // 立即过期
                .build();

        return RestResponse.success("Logout success");
    }

    // 用户注册
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public RestResponse<String> register(@RequestBody LoginRequest req) {
        return userService.register(req);
    }

}
