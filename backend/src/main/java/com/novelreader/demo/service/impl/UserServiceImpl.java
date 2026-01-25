package com.novelreader.demo.service.impl;

import com.novelreader.demo.entity.User;
import com.novelreader.demo.repository.UserRepository;
import com.novelreader.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // Đánh dấu đây là Bean Service (Logic)
@RequiredArgsConstructor // Tự tạo Constructor cho các biến final (Best Practice cho DI)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User registerUser(User request) {
        // 1. Validate: Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        // 2. Validate: Kiểm tra username
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken: " + request.getUsername());
        }

        // 3. Logic: Ở đây sau này sẽ mã hóa password (BCrypt)
        // Tạm thời giữ nguyên password text (Sẽ học Security sau)

        // 4. Gọi Thủ kho lưu vào DB
        return userRepository.save(request);
    }
}
