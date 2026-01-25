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
    @Builder.Default private int code = 200; // Mã nghiệp vụ (Ex: 1000 là thành công)

    private String message; // Thông báo (ex: "Register success")

    private T result; // Dữ liệu chính (ex: UserResponse)
}
