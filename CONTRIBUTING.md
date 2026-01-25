# Hướng dẫn đóng góp (Contributing Guide) - Novel Reader Project

Chào mừng bạn! Để dự án vận hành trơn tru và tránh xung đột Database, vui lòng tuân thủ quy trình sau.

## 1. Yêu cầu môi trường

- **IDE:** IntelliJ IDEA Ultimate (Plugin: JPA Buddy, EnvFile, google-java-format).
- **Hạ tầng:** Docker Desktop (PostgreSQL 15+).
- **Công cụ:** Make (chạy trên Git Bash hoặc WSL).
- **JDK:** 21.
- **Framework:** Spring Boot 4.0.2.

## 2. Quy tắc Code (Conventions)

- **Format:** Tuân thủ Google Java Format.
- **Lệnh thực hiện:**
    - `make format`: Tự động sửa code cho đẹp.
    - `make check`: Kiểm tra format và chạy Unit Test (Yêu cầu Coverage > 70%).

## 3. Quản lý Database (Flyway Migration)

Chúng ta tuyệt đối **không** dùng `ddl-auto=update`. Mọi thay đổi DB phải thông qua Migration script.

### Quy trình tạo Migration:

1. Sửa Entity Java (Thêm field, thay đổi annotation).
2. Generate lệnh sql từ diff giữa entity và database: **Chuột phải folder `db/migration` -> New -> Flyway Migration (
   Versioned)**.
3. Dùng `make migrate name=description` để tạo file mới, timestamp sẽ tự động tạo, phải điền description chính xác.
4. **Quy tắc đặt tên:** Sử dụng **Timestamp** để tránh xung đột version.
    - Định dạng: `VYYYYMMDDHHMMSS__description.sql` (Ví dụ: `V202601242300__add_note_to_users.sql`).
5. **Kiểm tra:** Luôn review lại file SQL, đảm bảo không có lệnh `DROP` ngoài ý muốn.

### Xử lý lỗi Migration:

Nếu `make run` báo lỗi **Migration Failed**:

1. Sửa lỗi cú pháp trong file `.sql`.
2. Truy cập Database (DBeaver), chạy lệnh xóa bản ghi lỗi:
   `DELETE FROM flyway_schema_history WHERE success = false;`
3. Chạy lại `make run`.

## 4. Cam kết (Guidelines)

- **Security:** Không commit file `.env`. Sử dụng `${VAR}` trong cấu hình.
- **Clean Code:** Xóa các import thừa, không dùng System.out.println (dùng Log).
- **PR:** Mô tả ngắn gọn thay đổi và đảm bảo `make check` đã Pass 100%.