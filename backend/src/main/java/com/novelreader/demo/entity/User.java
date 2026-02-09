package com.novelreader.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter // Tự sinh Getter
@Setter // Tự sinh Setter
@NoArgsConstructor // Tự sinh Constructor không tham số (Bắt buộc cho JPA)
@AllArgsConstructor // Tự sinh Constructor đầy đủ tham số
@Builder
public class User extends BaseEntity implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Chuyển đổi Role (String/Enum) sang GrantedAuthority
        // Giả sử bạn lưu role dạng String. Nếu dùng Enum thì .name()
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    // Các hàm dưới đây để true hết (vì ta tự quản lý logic này rồi)
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}
