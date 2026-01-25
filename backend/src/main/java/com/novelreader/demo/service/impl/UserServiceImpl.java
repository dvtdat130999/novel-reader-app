package com.novelreader.demo.service.impl;

import com.novelreader.demo.dto.request.UserRegistrationRequest;
import com.novelreader.demo.dto.response.UserResponse;
import com.novelreader.demo.entity.User;
import com.novelreader.demo.entity.UserRole;
import com.novelreader.demo.repository.UserRepository;
import com.novelreader.demo.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {
        // 1. Validate (Giữ nguyên như bài trước)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
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
}
