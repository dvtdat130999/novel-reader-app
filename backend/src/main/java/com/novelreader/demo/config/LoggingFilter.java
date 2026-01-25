package com.novelreader.demo.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // 1. Lấy ID từ Header của Frontend gửi lên (nếu có)
        String correlationId = req.getHeader("X-Correlation-ID");

        // 2. Nếu không có (Frontend quên gửi hoặc gọi trực tiếp), tự sinh mới
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }

        // 3. Bỏ vào "túi chứa" MDC (Mapped Diagnostic Context). Mọi log trong Thread này sẽ tự động có ID này.
        MDC.put("correlationId", correlationId);

        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("X-Correlation-ID", correlationId);
        try {
            // 4. Cho request đi tiếp vào Controller
            chain.doFilter(request, response);
        } finally {
            // 5. Dọn dẹp sau khi xong việc (Rất quan trọng để tránh rò rỉ bộ nhớ)
            MDC.remove("correlationId");
        }
    }
}
