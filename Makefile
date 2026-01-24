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