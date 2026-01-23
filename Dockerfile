# 1. Chọn nền móng (Base Image)
FROM nginx:latest

# 2. Thông tin người tạo (Optional)
LABEL maintainer="Dat Doan"

# 3. Copy code từ máy thật vào trong Image luôn
# (Để sau này đem Image này qua máy chủ khác chạy không cần mang theo code nguồn)
COPY ./index.html /usr/share/nginx/html/index.html

# 4. (Optional) Thông báo Port sẽ dùng
EXPOSE 80