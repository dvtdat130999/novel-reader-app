# Lệnh nạp file .env
ifneq ("$(wildcard .env)","")
    include .env
endif

# Export từng biến ra môi trường của hệ thống (để Spring Boot hiểu)
# Cách này giúp tránh lỗi ký tự lạ khi parse hàng loạt bằng shell
export DB_URL
export DB_USERNAME
export DB_PASSWORD


# Lệnh kiểm tra biến môi trường (Để sửa lỗi admin=)
debug:
	@echo "--- DEBUG ENVIRONMENT VARIABLES ---"
	@echo "DB_URL: $(DB_URL)"
	@echo "DB_USERNAME: $(DB_USERNAME)"
	@echo "DB_PASSWORD: $(DB_PASSWORD)"
	@echo "----------------------------------"

# Chạy app
run:
	cd backend && ./mvnw spring-boot:run

# Chạy test
test:
	cd backend && ./mvnw test

# Format code cho đẹp
format:
	cd backend && ./mvnw spotless:apply

# Dọn dẹp dự án
clean:
	cd backend && ./mvnw clean

# Build file .jar
build:
	cd backend && ./mvnw package -DskipTests

# Kiểm tra tất cả
check:
	cd backend && ./mvnw spotless:check
	cd backend && ./mvnw test