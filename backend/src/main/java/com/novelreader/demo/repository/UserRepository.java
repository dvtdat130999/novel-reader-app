package com.novelreader.demo.repository;

import com.novelreader.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 1. Magic Method: Spring tự hiểu tên hàm và biến thành SQL
    // SQL sinh ra: SELECT * FROM users WHERE email = ?
    boolean existsByEmail(String email);

    // 2. Tìm User theo username
    // Trả về Optional để tránh lỗi NullPointerException nếu không tìm thấy
    Optional<User> findByUsername(String username);

    // Spring Data JPA sẽ tự sinh câu SQL: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);
}
