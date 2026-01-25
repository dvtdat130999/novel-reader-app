package com.novelreader.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Tắt CSRF (Vì chúng ta dùng REST API stateless, không dùng Session cookie kiểu
                // cũ)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Cấu hình quyền truy cập
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        // Cho phép truy cập tự do vào các API auth (login,
                                        // register)
                                        .requestMatchers("/api/v1/auth/**")
                                        .permitAll()
                                        // Các API khác bắt buộc phải đăng nhập
                                        .anyRequest()
                                        .authenticated());

        return http.build();
    }
}
