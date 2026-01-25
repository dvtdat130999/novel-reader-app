# --- Giai đoạn 1: Build---
# Sử dụng Maven kèm JDK 21
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
# Lệnh đóng gói
RUN mvn clean package -DskipTests

# --- Giai đoạn 2: Run---
# Sử dụng JRE 21 siêu nhẹ
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy file .jar thành phẩm.
# Lưu ý: Lệnh này sẽ lấy file .jar đầu tiên tìm thấy trong target.
COPY --from=build /app/target/*.jar app.jar

# Mở cổng 8080
EXPOSE 8080
# Lệnh chạy app
ENTRYPOINT ["java", "-jar", "app.jar"]
