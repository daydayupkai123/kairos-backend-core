package com.kairos.filter;

import com.kairos.service.AuthService;
import com.kairos.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author kaijiang
 * @date 2025/10/11 16:42
 * @description
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromCookie(request);

        if (token != null && jwtUtil.validateToken(token)){
            String userId = jwtUtil.extractUserId(token);
            // 检查redis中是否为当前有效token
            if (authService.isTokenValidForUser(userId, token)){
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userId, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }

    public String extractTokenFromCookie(HttpServletRequest request){
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()){
            if ("kairos_token".equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
