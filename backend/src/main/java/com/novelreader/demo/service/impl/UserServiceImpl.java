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
        // 1. Validate (Giữ nguyên như bài trước)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        // 2. Mapping: DTO -> Entity (Thủ công)
        // Sau này dùng MapStruct sẽ nhanh hơn
        User user =
                User.builder()
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .password(
                                request.getPassword()) // Lưu ý: Chưa mã hóa (sẽ làm ở bài Security)
                        .role(UserRole.USER) // Mặc định luôn là USER
                        .build();

        // 3. Save
        User savedUser = userRepository.save(user);

        // 4. Mapping: Entity -> Response DTO
        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
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
