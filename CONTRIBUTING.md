# Hướng dẫn đóng góp (Contributing Guide)

Chào mừng bạn đến với dự án **Novel Reader**! Để đảm bảo chất lượng code và sự đồng nhất trong dự án, vui lòng tuân thủ
quy trình sau:

## 1. Trước khi code

* **Cập nhật code mới nhất:** `git pull origin main`
* **Tạo nhánh mới:** `git checkout -b feature/ten-tinh-nang`

---

## 2. Quy tắc Code (Conventions)

* Chúng tôi sử dụng **Google Java Format**.
* Chạy lệnh sau để tự động format code trước khi commit:

```bash
make format
# Hoặc: ./mvnw spotless:apply

```

---

## 3. Trước khi tạo Pull Request (PR)

Vui lòng chạy lệnh kiểm tra tổng thể để đảm bảo chất lượng:

```bash
make check
```

**Lệnh này sẽ đảm bảo:**

* Code đã được format đúng chuẩn.
* Tất cả Unit Test đều vượt qua (Pass).
* Test Coverage đạt tối thiểu **70%**.

---

## 4. Cam kết (Guidelines)

* **Không push** file cấu hình IDE (`.idea/`, `.vscode/`, `.settings/`) lên Git.
* Đảm bảo file `application.properties` và các file configure khác không chứa thông tin nhạy cảm (mật khẩu cá nhân).
* Viết mô tả PR rõ ràng, ngắn gọn về những thay đổi bạn đã thực hiện.

---

# Quy trình phát triển (Updated 2026)

## 1. Công cụ yêu cầu

- IntelliJ IDEA Ultimate (Plugin: JPA Buddy, EnvFile, google-java-format).
- Docker Desktop (Chạy PostgreSQL).
- Make (Cài qua Git Bash/Chocolatey).

## 2. Quản lý Database

- KHÔNG dùng `ddl-auto=update`. Sử dụng **Flyway Migration**.
- Luôn tạo file SQL mới tại `backend/src/main/resources/db/migration/`.
- Tên file chuẩn: `V<Number>__<Description>.sql` (2 dấu gạch dưới).
- Dùng **JPA Buddy -> Diff** để sinh file SQL tự động từ Entity Java.

## 3. Câu lệnh hằng ngày (Makefile)

- `make format`: Tự động căn chỉnh code theo Google Style.
- `make test`: Chạy Unit Test và kiểm tra độ phủ (Coverage > 70%).
- `make run`: Khởi động ứng dụng (Tự động nạp biến môi trường từ .env).
