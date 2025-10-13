package com.kairos.response;

/**
 * @author kaijiang
 * @date 2025/10/13 10:07
 * @description
 */
public class RestResponse<T> {

    private String code;
    private String message;
    private T data;

    public static <T> RestResponse<T> success(T data) {
        RestResponse<T> response = new RestResponse<>();
        response.setCode("200");
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static <T> RestResponse<T> fail(String code, String message) {
        RestResponse<T> response = new RestResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
