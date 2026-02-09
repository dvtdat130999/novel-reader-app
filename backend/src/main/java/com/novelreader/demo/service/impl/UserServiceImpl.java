package com.novelreader.demo.service.impl;


import com.novelreader.demo.dto.request.AuthenticationRequest;
import com.novelreader.demo.dto.request.UserRegistrationRequest;
import com.novelreader.demo.dto.response.AuthenticationResponse;
import com.novelreader.demo.dto.response.UserResponse;
import com.novelreader.demo.entity.User;
import com.novelreader.demo.entity.UserRole;
import com.novelreader.demo.exception.AppException;
import com.novelreader.demo.exception.ErrorCode;
import com.novelreader.demo.repository.UserRepository;
import com.novelreader.demo.security.JwtService;
import com.novelreader.demo.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {
        // 1. Kiểm tra Email đã tồn tại chưa
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        // 2. Mapping & BẢO MẬT: Mã hóa mật khẩu trước khi lưu
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                // QUAN TRỌNG: Dùng BCrypt để mã hóa mật khẩu
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER) // Mặc định là USER
                .build();

        // 3. Lưu vào Database
        try {
            user = userRepository.save(user);
        } catch (Exception e) {
//            log.error("Error saving user: ", e);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        // 4. Trả về Response DTO
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            // 1. Nhờ AuthenticationManager kiểm tra User/Pass
            // Nếu sai pass -> Nó tự ném lỗi BadCredentialsException
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            // Catch lỗi của Spring Security và ném ra lỗi của App mình (để đồng bộ format JSON)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // 2. Nếu code chạy đến đây nghĩa là đăng nhập thành công.
        // Tìm user để lấy thông tin tạo Token
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // 3. Tạo Token
        var token = jwtService.generateToken(user);

        // 4. Trả về
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

}
