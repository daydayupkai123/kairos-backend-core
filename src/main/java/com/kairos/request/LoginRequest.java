package com.kairos.request;

import lombok.Data;

/**
 * @author kaijiang
 * @date 2025/10/11 16:32
 * @description
 */
@Data
public class LoginRequest {

    public String email;

    public String password;

    public String username;
}
