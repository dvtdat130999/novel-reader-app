package com.novelreader.demo.exception;

import com.novelreader.demo.dto.response.ApiResponse;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId"; // Key trùng với trong Filter

    // 1. Hứng các lỗi do chính mình tạo ra (AppException)
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setTraceId(MDC.get(CORRELATION_ID_LOG_VAR_NAME));
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // 2. Hứng tất cả các lỗi còn lại (RuntimeException mặc định)
    // Ví dụ: NullPointer, Database connection fail...
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        apiResponse.setTraceId(MDC.get(CORRELATION_ID_LOG_VAR_NAME));
        // Có thể log lỗi ra console để dev fix
        // exception.printStackTrace();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        // Lấy cái message mà mình đã define ở DTO (VD: "USERNAME_INVALID")
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            // Tìm trong Enum xem có cái key này không
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {
            // Nếu dev quên define trong Enum thì dùng lỗi mặc định
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        apiResponse.setTraceId(MDC.get(CORRELATION_ID_LOG_VAR_NAME));

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
