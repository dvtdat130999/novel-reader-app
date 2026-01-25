package com.novelreader.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter // Tự sinh Getter
@Setter // Tự sinh Setter
@NoArgsConstructor // Tự sinh Constructor không tham số (Bắt buộc cho JPA)
@AllArgsConstructor // Tự sinh Constructor đầy đủ tham số
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private UserRole role;
}
