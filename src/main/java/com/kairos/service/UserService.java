package com.kairos.service;

import com.kairos.request.LoginRequest;
import com.kairos.response.RestResponse;

/**
 * @author kaijiang
 * @date 2025/10/13 10:39
 * @description
 */
public interface UserService {
    RestResponse<String> register(LoginRequest req);
}
