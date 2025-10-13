package com.kairos.exception;

/**
 * @author kaijiang
 * @date 2025/10/13 10:51
 * @description 业务异常
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}




