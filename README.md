# 📚 Course Management System

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)
![Status](https://img.shields.io/badge/Status-Active-success.svg)

**Backend API mạnh mẽ được xây dựng trên Spring Boot, cung cấp các dịch vụ RESTful cho việc quản lý khóa học, sinh viên, giảng viên và chương trình đào tạo.**

[English](#english-version) | [Demo](#-demo) | [Báo Lỗi](https://github.com/longvuvn/coursemanagement/issues) | [Yêu Cầu Tính Năng](https://github.com/longvuvn/coursemanagement/issues)

</div>

---

## 📋 Mục Lục

- [Giới Thiệu](#-giới-thiệu)
- [Tính Năng Nổi Bật](#-tính-năng-nổi-bật)
- [Công Nghệ Sử Dụng](#-công-nghệ-sử-dụng)
- [Kiến Trúc Hệ Thống](#-kiến-trúc-hệ-thống)
- [Demo](#-demo)
- [Hướng Dẫn Cài Đặt](#-hướng-dẫn-cài-đặt)
- [Cấu Hình](#-cấu-hình)
- [Sử Dụng API](#-sử-dụng-api)
- [Đóng Góp](#-đóng-góp)
- [Giấy Phép](#-giấy-phép)
- [Liên Hệ](#-liên-hệ)

---

## 🎯 Giới Thiệu

**Course Management System** là một nền tảng quản lý khóa học toàn diện, được thiết kế để đơn giản hóa quy trình quản lý giáo dục trực tuyến. Hệ thống cung cấp các công cụ mạnh mẽ cho việc tạo, quản lý và theo dõi các khóa học, sinh viên, bài tập và đánh giá.

### 🌟 Điểm Nổi Bật

- ✅ **RESTful API** hoàn chỉnh với tài liệu Swagger/OpenAPI
- ✅ **Bảo mật cao** với JWT Authentication & Role-based Authorization
- ✅ **Kiến trúc sạch** với mô hình phân lớp rõ ràng
- ✅ **Tối ưu hiệu suất** với JPA/Hibernate
- ✅ **Email tự động** cho thông báo người dùng
- ✅ **Xử lý lỗi toàn cục** với custom exceptions

---

## ✨ Tính Năng Nổi Bật

### 👥 Quản Lý Người Dùng
- 🔐 **Đăng ký/Đăng nhập** với xác thực JWT
- 👤 **Quản lý hồ sơ** Admin và Learner
- 🎭 **Phân quyền** dựa trên vai trò (Role-based Access Control)
- 🔄 **Refresh Token** tự động gia hạn phiên làm việc

### 📖 Quản Lý Khóa Học
- ➕ **CRUD khóa học** đầy đủ (Tạo, Đọc, Cập nhật, Xóa)
- 📑 **Chương trình học** với Chapters và Lessons
- ⭐ **Hệ thống đánh giá** và review từ học viên
- 📊 **Thống kê** rating và số lượng học viên

### 📝 Quản Lý Đào Tạo
- 📚 **Đăng ký khóa học** cho học viên
- 📤 **Nộp bài tập** (Submission Management)
- 🎓 **Theo dõi tiến độ** học tập
- 💳 **Quản lý đơn hàng** (Order & Payment)

### 🔧 Tính Năng Kỹ Thuật
- 🔍 **Tìm kiếm & Lọc** dữ liệu
- 📄 **Phân trang** kết quả
- 📧 **Email notifications** 
- 📝 **Validation** dữ liệu đầy đủ
- 🛡️ **Global Exception Handling**

---

## 🛠️ Công Nghệ Sử Dụng

<table>
<tr>
<td>

### Backend Framework
- **Spring Boot** `3.4.4`
- **Spring Security** - Bảo mật
- **Spring Data JPA** - Truy xuất dữ liệu
- **Spring Mail** - Gửi email

</td>
<td>

### Database & ORM
- **MySQL** `8.0+`
- **Hibernate** - ORM
- **MySQL Connector** `9.0.0`

</td>
</tr>
<tr>
<td>

### Security & Authentication
- **JWT (JJWT)** `0.11.5` - Token-based auth
- **BCrypt** - Mã hóa mật khẩu
- **Spring Security** - Authorization

</td>
<td>

### Documentation & Tools
- **SpringDoc OpenAPI** `2.8.9`
- **Swagger UI** - API Documentation
- **Lombok** - Code reduction
- **ModelMapper** `3.1.1` - DTO mapping

</td>
</tr>
<tr>
<td>

### Development Tools
- **Spring DevTools** - Hot reload
- **Maven** - Build tool
- **Java** `21` - Programming language

</td>
<td>

### Testing
- **Spring Boot Test**
- **Spring Security Test**
- **JUnit** - Unit testing

</td>
</tr>
</table>

---

## 🏗️ Kiến Trúc Hệ Thống

```
coursemanagement/
│
├── 📁 config/                    # Cấu hình ứng dụng
│   ├── SecurityConfig.java       # Bảo mật & JWT
│   ├── OpenAPIConfig.java        # Swagger configuration
│   ├── CustomUserDetailsService  # User authentication
│   └── AdminInitializer.java     # Khởi tạo admin mặc định
│
├── 📁 controllers/                # REST API Controllers
│   ├── AuthController            # Authentication endpoints
│   ├── CourseController          # Course management
│   ├── LearnerController         # Learner management
│   ├── AdminController           # Admin operations
│   ├── ChapterController         # Chapter management
│   ├── LessonController          # Lesson management
│   ├── ReviewController          # Review & Rating
│   ├── RegistrationController    # Course registration
│   ├── SubmissionController      # Assignment submission
│   └── OrderController           # Payment & Orders
│
├── 📁 services/                   # Business Logic Layer
│   ├── impl/                     # Service implementations
│   └── exceptions/               # Custom exceptions & handling
│
├── 📁 repositories/               # Data Access Layer
│   └── JPA Repositories          # Database queries
│
├── 📁 models/                     # Domain Models & DTOs
│   ├── entities/                 # JPA Entities
│   │   ├── User, Admin, Learner
│   │   ├── Course, Chapter, Lesson
│   │   ├── Review, Registration
│   │   ├── Order, OrderDetail
│   │   └── Submission
│   └── dto/                      # Data Transfer Objects
│
├── 📁 enums/                      # Enumerations
│   ├── UserStatus, RoleStatus
│   ├── CourseStatus, ChapterStatus
│   ├── ReviewStatus, OrderStatus
│   └── SubmissionStatus
│
└── 📁 util/                       # Utility Classes
    └── JWTUtil.java              # JWT helper methods
```

### 🔄 Request Flow
```
Client Request
    ↓
JWT Filter (Authentication)
    ↓
Security Filter Chain (Authorization)
    ↓
Controller (REST Endpoints)
    ↓
Service Layer (Business Logic)
    ↓
Repository (Data Access)
    ↓
MySQL Database
```

---

## 📸 Demo

### 🖥️ Swagger API Documentation
<!-- Để trống cho người dùng tự điền -->
```
Thêm ảnh Swagger UI tại đây
```

### 📱 Dashboard Preview
<!-- Để trống cho người dùng tự điền -->
```
Thêm ảnh giao diện Dashboard tại đây
```

### 🎓 Course Management Interface
<!-- Để trống cho người dùng tự điền -->
```
Thêm ảnh giao diện quản lý khóa học tại đây
```

---

## 🚀 Hướng Dẫn Cài Đặt

### Yêu Cầu Hệ Thống

- ☑️ **Java Development Kit (JDK)** 21 hoặc cao hơn
- ☑️ **Maven** 3.6+ 
- ☑️ **MySQL** 8.0+
- ☑️ **Git** (để clone repository)
- ☑️ **IDE**: IntelliJ IDEA / Eclipse / VS Code (khuyến nghị IntelliJ IDEA)

### Bước 1️⃣: Clone Repository

```bash
git clone https://github.com/longvuvn/coursemanagement.git
cd coursemanagement
```

### Bước 2️⃣: Cài Đặt MySQL Database

1. **Khởi động MySQL Server**
2. **Tạo database mới:**

```sql
CREATE DATABASE coursemanagement;
CREATE USER 'course_admin'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON coursemanagement.* TO 'course_admin'@'localhost';
FLUSH PRIVILEGES;
```

### Bước 3️⃣: Cấu Hình Biến Môi Trường

Tạo file `.env` hoặc thiết lập biến môi trường:

```properties
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/coursemanagement
DB_USERNAME=course_admin
DB_PASSWORD=your_password

# JWT Configuration
JWT_SECRET=your-256-bit-secret-key-here-make-it-long-and-random

# Email Configuration
EMAIL_USERNAME=your-email@gmail.com
EMAIL_PASSWORD=your-app-specific-password
```

**💡 Lưu ý:** 
- Sử dụng **App Password** cho Gmail (không phải mật khẩu thường)
- JWT Secret phải dài ít nhất 256 bits

### Bước 4️⃣: Build & Run Application

#### Sử Dụng Maven Wrapper (Khuyến nghị)

**Windows:**
```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw clean install
./mvnw spring-boot:run
```

#### Sử Dụng Maven Trực Tiếp

```bash
mvn clean install
mvn spring-boot:run
```

### Bước 5️⃣: Kiểm Tra Ứng Dụng

✅ Ứng dụng sẽ chạy tại: **http://localhost:8081**

✅ Swagger UI: **http://localhost:8081/swagger-ui.html**

✅ API Docs: **http://localhost:8081/v3/api-docs**

### 🔐 Tài Khoản Admin Mặc Định

```
Email: admin@gmail.com
Password: admin123
```

**⚠️ Quan trọng:** Đổi mật khẩu admin ngay sau lần đăng nhập đầu tiên!

---

## ⚙️ Cấu Hình

### Application Configuration (`application.yml`)

```yaml
server:
  port: 8081

spring:
  application:
    name: coursemanagement
  
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  expiration:
    access-token: 1800000    # 30 minutes
    refresh-token: 604800000  # 7 days
```

### Tùy Chỉnh Cấu Hình

#### Thay đổi Port Server
```yaml
server:
  port: 8080  # Đổi sang port khác
```

#### Tắt SQL Logging (Production)
```yaml
spring:
  jpa:
    show-sql: false
```

#### Điều chỉnh JWT Expiration
```yaml
jwt:
  expiration:
    access-token: 3600000  # 1 hour
```

---

## 📡 Sử Dụng API

### Authentication Endpoints

#### 🔐 Đăng Nhập
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin@gmail.com",
  "password": "admin123"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 🔄 Refresh Token
```http
POST /api/v1/auth/refresh
Content-Type: application/json

{
  "refreshToken": "your-refresh-token"
}
```

### Course Management

#### 📚 Lấy Danh Sách Khóa Học
```http
GET /api/v1/courses
Authorization: Bearer {access_token}
```

#### ➕ Tạo Khóa Học Mới (Admin Only)
```http
POST /api/v1/courses
Authorization: Bearer {access_token}
Content-Type: application/json

{
  "title": "Spring Boot Fundamentals",
  "description": "Learn Spring Boot from scratch",
  "level": "BEGINNER",
  "price": "99.99"
}
```

#### 📖 Lấy Chi Tiết Khóa Học
```http
GET /api/v1/courses/{courseId}
Authorization: Bearer {access_token}
```

### Learner Operations

#### 📝 Đăng Ký Khóa Học
```http
POST /api/v1/registration
Authorization: Bearer {access_token}
Content-Type: application/json

{
  "learnerId": "uuid-here",
  "courseId": "uuid-here"
}
```

#### ⭐ Đánh Giá Khóa Học
```http
POST /api/v1/reviews
Authorization: Bearer {access_token}
Content-Type: application/json

{
  "learnerId": "uuid-here",
  "courseId": "uuid-here",
  "rating": "5",
  "comment": "Excellent course!"
}
```

### 📖 Tài Liệu API Đầy Đủ

Truy cập **Swagger UI** để xem tài liệu API đầy đủ và test endpoints:

🔗 **http://localhost:8081/swagger-ui.html**

---

## 🤝 Đóng Góp

Chúng tôi rất hoan nghênh mọi đóng góp! Dưới đây là quy trình đóng góp:

### 🌿 Quy Trình Đóng Góp

1. **Fork** repository này
2. **Clone** repository về máy local:
   ```bash
   git clone https://github.com/your-username/coursemanagement.git
   ```
3. **Tạo branch mới** cho feature/bugfix:
   ```bash
   git checkout -b feature/amazing-feature
   ```
4. **Commit** thay đổi:
   ```bash
   git commit -m "Add some amazing feature"
   ```
5. **Push** lên branch:
   ```bash
   git push origin feature/amazing-feature
   ```
6. **Tạo Pull Request** trên GitHub

### 📝 Coding Standards

- ✅ Tuân thủ Java Code Conventions
- ✅ Viết Javadoc cho public methods
- ✅ Đảm bảo test cases pass
- ✅ Format code trước khi commit
- ✅ Viết commit message rõ ràng

### 🐛 Báo Lỗi

Nếu bạn phát hiện lỗi, vui lòng tạo **Issue** mới với:
- Mô tả chi tiết lỗi
- Các bước để reproduce
- Expected vs Actual behavior
- Screenshots (nếu có)

---

## 📄 Giấy Phép

Dự án này được phân phối dưới giấy phép **MIT License**. Xem file [LICENSE](LICENSE) để biết thêm chi tiết.

```
MIT License

Copyright (c) 2025 Long Vu

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files...
```

---

## 📞 Liên Hệ

<div align="center">

**Long Vu**

[![GitHub](https://img.shields.io/badge/GitHub-longvuvn-181717?style=for-the-badge&logo=github)](https://github.com/longvuvn)
[![Email](https://img.shields.io/badge/Email-Contact-D14836?style=for-the-badge&logo=gmail)](mailto:longvuvn@example.com)

**Repository:** https://github.com/longvuvn/coursemanagement

</div>

---

## 🙏 Acknowledgments

- Spring Boot Community
- Spring Security Documentation
- JWT.io for token debugging
- Swagger/OpenAPI Community
- MySQL Documentation

---

<div align="center">

### ⭐ Nếu dự án này hữu ích, đừng quên cho một Star! ⭐

**Made with ❤️ by Long Vu**

---

# English Version

</div>

## 📋 Table of Contents

- [Introduction](#-introduction-en)
- [Key Features](#-key-features-en)
- [Tech Stack](#️-tech-stack-en)
- [Architecture](#️-architecture-en)
- [Getting Started](#-getting-started-en)
- [API Usage](#-api-usage-en)
- [Contributing](#-contributing-en)
- [License](#-license-en)

---

## 🎯 Introduction (EN)

**Course Management System** is a comprehensive course management platform designed to simplify online education management processes. The system provides powerful tools for creating, managing, and tracking courses, students, assignments, and assessments.

### 🌟 Highlights

- ✅ **Complete RESTful API** with Swagger/OpenAPI documentation
- ✅ **High security** with JWT Authentication & Role-based Authorization
- ✅ **Clean architecture** with clear layered model
- ✅ **Performance optimized** with JPA/Hibernate
- ✅ **Automatic email** notifications
- ✅ **Global error handling** with custom exceptions

---

## ✨ Key Features (EN)

### 👥 User Management
- 🔐 **Registration/Login** with JWT authentication
- 👤 **Profile management** for Admin and Learner
- 🎭 **Role-based Access Control**
- 🔄 **Refresh Token** for automatic session renewal

### 📖 Course Management
- ➕ **Full CRUD operations** for courses
- 📑 **Curriculum structure** with Chapters and Lessons
- ⭐ **Rating system** and reviews from learners
- 📊 **Statistics** for ratings and student enrollment

### 📝 Training Management
- 📚 **Course registration** for learners
- 📤 **Assignment submission**
- 🎓 **Progress tracking**
- 💳 **Order management** (Payment system)

---

## 🚀 Getting Started (EN)

### Prerequisites

- ☑️ **Java Development Kit (JDK)** 21 or higher
- ☑️ **Maven** 3.6+ 
- ☑️ **MySQL** 8.0+
- ☑️ **Git**

### Installation

#### Step 1: Clone Repository

```bash
git clone https://github.com/longvuvn/coursemanagement.git
cd coursemanagement
```

#### Step 2: Setup MySQL Database

```sql
CREATE DATABASE coursemanagement;
CREATE USER 'course_admin'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON coursemanagement.* TO 'course_admin'@'localhost';
FLUSH PRIVILEGES;
```

#### Step 3: Configure Environment Variables

Create `.env` file or set environment variables:

```properties
DB_URL=jdbc:mysql://localhost:3306/coursemanagement
DB_USERNAME=course_admin
DB_PASSWORD=your_password
JWT_SECRET=your-256-bit-secret-key
EMAIL_USERNAME=your-email@gmail.com
EMAIL_PASSWORD=your-app-password
```

#### Step 4: Build & Run

**Windows:**
```bash
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

**Linux/Mac:**
```bash
./mvnw clean install
./mvnw spring-boot:run
```

#### Step 5: Access Application

- **Application:** http://localhost:8081
- **Swagger UI:** http://localhost:8081/swagger-ui.html
- **API Docs:** http://localhost:8081/v3/api-docs

### Default Admin Account

```
Email: admin@gmail.com
Password: admin123
```

⚠️ **Important:** Change the admin password immediately after first login!

---

## 📡 API Usage (EN)

### Authentication

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin@gmail.com",
  "password": "admin123"
}
```

#### Refresh Token
```http
POST /api/v1/auth/refresh
Content-Type: application/json

{
  "refreshToken": "your-refresh-token"
}
```

### Course Operations

#### Get All Courses
```http
GET /api/v1/courses
Authorization: Bearer {access_token}
```

#### Create Course (Admin only)
```http
POST /api/v1/courses
Authorization: Bearer {access_token}
Content-Type: application/json

{
  "title": "Spring Boot Fundamentals",
  "description": "Learn Spring Boot from scratch",
  "level": "BEGINNER",
  "price": "99.99"
}
```

### 📖 Full API Documentation

Visit **Swagger UI** for complete API documentation:

🔗 **http://localhost:8081/swagger-ui.html**

---

## 🤝 Contributing (EN)

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Coding Standards

- ✅ Follow Java Code Conventions
- ✅ Write Javadoc for public methods
- ✅ Ensure all tests pass
- ✅ Format code before committing
- ✅ Write clear commit messages

---

## 📄 License (EN)

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) file for details.

---

<div align="center">

**⭐ Star this repository if you find it helpful! ⭐**

**Made with ❤️ by Long Vu**

[![GitHub followers](https://img.shields.io/github/followers/longvuvn?style=social)](https://github.com/longvuvn)
[![GitHub stars](https://img.shields.io/github/stars/longvuvn/coursemanagement?style=social)](https://github.com/longvuvn/coursemanagement/stargazers)

</div>

