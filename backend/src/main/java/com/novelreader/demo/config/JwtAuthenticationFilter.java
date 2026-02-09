package com.novelreader.demo.config;

import com.novelreader.demo.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    // Chúng ta cần Bean này để load user từ DB (sẽ config ở bước 3)
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Kiểm tra Header có chứa Bearer Token không
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Không có token -> Cho qua (để bộ lọc sau xử lý)
            return;
        }

        // 2. Lấy Token ra (Bỏ chữ "Bearer " ở đầu)
        jwt = authHeader.substring(7);

        try {
            // 3. Trích xuất Email từ Token
            userEmail = jwtService.extractUsername(jwt);

            // 4. Nếu có Email và chưa được xác thực trong Context hiện tại
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Lấy thông tin User từ DB
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Kiểm tra Token có hợp lệ không (Check chữ ký + Hết hạn)
                // Lưu ý: jwtService.verifyToken sẽ throw exception nếu không hợp lệ
                jwtService.verifyToken(jwt);

                // 5. Tạo đối tượng Authentication và set vào Context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // ĐÁNH DẤU: User này đã đăng nhập thành công
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
            // Không throw exception ở đây để filter chain chạy tiếp
            // Nếu token lỗi, request sẽ bị chặn ở các bước sau vì SecurityContext rỗng
        }

        // 6. Chuyển tiếp request cho filter tiếp theo
        filterChain.doFilter(request, response);
    }
}