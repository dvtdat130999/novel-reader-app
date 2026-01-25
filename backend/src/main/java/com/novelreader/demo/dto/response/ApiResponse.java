package com.novelreader.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Nếu trường nào null thì bỏ qua, không trả về
public class ApiResponse<T> {
    @Builder.Default private int code = 10000; // Mã nghiệp vụ (Ex: 1000 là thành công)

    private String message; // Thông báo (ex: "Register success")

    private T result; // Dữ liệu chính (ex: UserResponse)

    private String traceId;

    // --- THÊM HÀM NÀY ---
    // Hàm tiện ích để trả về thành công nhanh gọn
    public static <T> ApiResponse<T> success(T result) {
        return ApiResponse.<T>builder()
                .code(10000) // Hoặc code thành công bạn quy định
                .result(result)
                .build();
    }
}
