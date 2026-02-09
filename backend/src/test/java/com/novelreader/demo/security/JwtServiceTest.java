package com.novelreader.demo.security;


import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.util.List;

// Dùng MockitoExtension: Không khởi động Spring Context -> Siêu nhanh
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    // InjectMocks: Tạo instance của JwtService và tiêm các mock vào (nếu có)
    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    // Chuẩn bị dữ liệu giả trước mỗi test case
    @BeforeEach
    void setUp() {
        // Vì JwtService dùng @Value để lấy biến môi trường
        // Trong Unit Test không load file properties, ta dùng Reflection để set giá trị thủ công
        String secretKey = "test_key_rat_dai_test_key_rat_dai_test_key_rat_dai_hon_64_byte_1234567890_du_64_byte_chua_nhi";
        ReflectionTestUtils.setField(jwtService, "SIGNER_KEY", secretKey);
        ReflectionTestUtils.setField(jwtService, "VALID_DURATION", 10000L); // 10 giây
    }

    @Test
    void generateToken_ShouldReturnString_WhenUserDetailsIsValid() {
        // 1. GIVEN (Giả lập hành vi của UserDetails)
        Mockito.when(userDetails.getUsername()).thenReturn("test@gmail.com");
        Mockito.doReturn(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .when(userDetails).getAuthorities();

        // 2. WHEN (Thực thi hàm cần test)
        String token = jwtService.generateToken(userDetails);

        // 3. THEN (Kiểm tra kết quả)
        Assertions.assertNotNull(token);
        System.out.println("Generated Token: " + token);
    }

    @Test
    void verifyToken_ShouldSuccess_WhenTokenIsValid() throws ParseException, JOSEException {
        // 1. GIVEN: Tạo ra một token hợp lệ
        Mockito.when(userDetails.getUsername()).thenReturn("test@gmail.com");
        Mockito.doReturn(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .when(userDetails).getAuthorities();
        String token = jwtService.generateToken(userDetails);

        // 2. WHEN: Verify token đó
        var signedJWT = jwtService.verifyToken(token);

        // 3. THEN: Không ném lỗi và lấy ra đúng subject
        Assertions.assertEquals("test@gmail.com", signedJWT.getJWTClaimsSet().getSubject());
    }
}
