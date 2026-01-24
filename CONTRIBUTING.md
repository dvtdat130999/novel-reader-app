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
