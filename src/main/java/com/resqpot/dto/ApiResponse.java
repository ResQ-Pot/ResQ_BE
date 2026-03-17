package com.resqpot.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final String status;
    private final T data;

    private ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data);
    }

    public static <T> ApiResponse<T> error(T data) {
        return new ApiResponse<>("error", data);
    }
}