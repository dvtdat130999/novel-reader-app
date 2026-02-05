package com.novelreader.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(4001, "Token is expired", HttpStatus.UNAUTHORIZED),
    USER_NOT_EXISTED(4002, "User is not existed", HttpStatus.NOT_FOUND),
    NULL_EXCEPTION(9001, "Must not be null", HttpStatus.BAD_REQUEST),
    EMPTY_EXCEPTION(9002, "Must not be empty", HttpStatus.BAD_REQUEST),
    BLANK_EXCEPTION(9003, "Must not be blank", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1000, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User already existed", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1002, "Email already existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 6 characters", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1005, "Email has invalid format", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
