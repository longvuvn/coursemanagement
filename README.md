Course Management

Quản lý khóa học, lớp học, giảng viên, sinh viên và lịch học. Dự án backend (Spring Boot) cung cấp API để quản trị đào tạo, ghi danh, điểm danh, chấm điểm, và báo cáo.
✨ Tính năng chính
- Quản lý Khóa học: tạo/sửa/xóa, mã học phần, tín chỉ, mô tả.
- Quản lý Lớp học theo học kỳ, lịch học, phòng học.
- Quản lý Giảng viên và Sinh viên.
- Đăng ký môn và kiểm tra điều kiện tiên quyết.
- Điểm danh và chấm điểm theo thành phần.
- Báo cáo: danh sách sinh viên theo lớp, bảng điểm, tỉ lệ đậu/rớt.
- Phân quyền: Admin, Lecturer, Student.
- API Docs với Swagger/OpenAPI.
* TODO: Cập nhật danh sách tính năng theo đúng phạm vi project của bạn.

🧱 Kiến trúc & Công nghệ
- Java 17+, Maven
- Spring Boot: Web, Data JPA, Validation, Security (JWT/Session, tùy chọn)
- Database: PostgreSQL hoặc MySQL
- Migration: Flyway hoặc Liquibase (tùy chọn)
- OpenAPI/Swagger: springdoc-openapi
- Test: JUnit 5, Spring Boot Test
* TODO: Điều chỉnh cho trùng khớp với pom.xml và code thực tế.

coursemanagement/
├─ src/
│  ├─ main/
│  │  ├─ java/com/example/coursemanagement/
│  │  │  ├─ config/         # Security, Swagger, CORS
│  │  │  ├─ controller/     # REST controllers
│  │  │  ├─ dto/            # Request/Response DTO
│  │  │  ├─ entity/         # JPA entities
│  │  │  ├─ exception/      # Global exception handler
│  │  │  ├─ mapper/         # MapStruct/ModelMapper (tuỳ chọn)
│  │  │  ├─ repository/     # Spring Data JPA
│  │  │  ├─ service/        # Business logic
│  │  │  └─ CourseManagementApplication.java
│  │  └─ resources/
│  │     ├─ application.yml # cấu hình
│  │     └─ db/migration/   # Flyway scripts (V1__init.sql, ...)
│  └─ test/java/...         # Unit/Integration tests
├─ pom.xml
└─ README.md


⚙️ Cài đặt & Chạy dự án
1) Yêu cầu
- Java 17
- Maven 3.9+
- PostgreSQL hoặc MySQL đang chạy cục bộ
2) Cấu hình môi trường
  spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/course_management
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update   # khuyến nghị: dùng flyway và để none/validate ở môi trường prod
    show-sql: true
    properties:
      hibernate.format_sql: true

server:
  port: 8080

# Swagger UI (springdoc)
springdoc:
  api-docs:
    enabled: true
  swagger-ui:
    enabled: true

3) Chạy
  
 # build
mvn clean package

# chạy app
mvn spring-boot:run
# hoặc
java -jar target/coursemanagement-*.jar



