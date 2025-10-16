package com.kairos.service;

/**
 * @author kaijiang
 * @date 2025/10/13 14:18
 * @description
 */
public interface AuthService {

    String login(String email, String rawPassword);

    void logout(String userId);

    boolean isTokenValidForUser(String uerId, String token);
}
