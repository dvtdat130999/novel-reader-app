package com.novelreader.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "genres")
@Getter // Tự sinh Getter
@Setter // Tự sinh Setter
@NoArgsConstructor // Tự sinh Constructor không tham số (Bắt buộc cho JPA)
@AllArgsConstructor // Tự sinh Constructor đầy đủ tham số
@Builder // Hỗ trợ khởi tạo Object theo style: Genre.builder().name("ABC").build()
public class Genre extends com.demo.entity.BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 100)
  private String name;

  @Column(length = 255)
  private String description;
}
