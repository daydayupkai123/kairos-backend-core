package com.kairos.exception;

/**
 * @author kaijiang
 * @date 2025/10/13 10:52
 * @description 资源未找到异常
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
