package com.profileeM.profileeM;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> ok() {
        return of(HttpStatus.OK, "성공", null);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, "성공", data);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return of(status, message, null);
    }
}