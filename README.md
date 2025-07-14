# CRM-backend

# CRM Backend System

The CRM Backend System is a robust, secure, and scalable API platform built using Spring Boot for managing customer relationships in businesses of all sizes. It allows organizations to streamline their sales, marketing, and support processes by tracking leads, managing customer information, and automating communications.This backend provides RESTful APIs that support secure user authentication, role-based access, customer and lead management, and integration with email for password recovery and future notifications.

---

## ✅ Features Completed

- **User Authentication & Authorization**
  - JWT-based login/register
  - Role-based access (ADMIN, MANAGER, SALES, USER, CUSTOMER)
- **User Management**
  - CRUD APIs for managing users (admin/manager roles)
- **Lead Management**
  - Create/update/view/delete leads based on role
- **Customer Management**
  - Secure CRUD access for admin, manager, user roles
- **Forgot/Reset Password**
  - Secure password reset flow with email support
- **Spring Security Configuration**
  - Stateless JWT setup
  - CustomUserDetailsService
- **Password Encoding**
  - Using BCrypt

---

## 🔄 Planned Features

- ✅ Swagger/OpenAPI API documentation with springdoc
- 🔔 Email Notifications (lead follow-up, customer updates)
- 📞 Meeting & Call Scheduling (via email/calendar integration)
- 🌐 Frontend Integration (React/Angular planned)
- 📊 Analytics Dashboard (lead conversion rates, user metrics)
- 🛠️ Admin Settings Panel (configurable email templates, user roles)


---

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Security 6.5**
- **Spring Data JPA**
- **Hibernate**
- **JWT (io.jsonwebtoken)**
- **MySQL**
- **Spring Mail**
- **Maven**

---

## 📦 How to Run

1. **Clone the repo**
   ```bash
   git clone https://github.com/RamYerva/CRM-backend.git
   cd CRM-backend

2. **Configure MySQL DB
    Update application.properties

   spring.datasource.url=jdbc:mysql://localhost:3306/db
   spring.datasource.username=username
   spring.datasource.password=yourpassword

3. **Run the application
    mvn spring-boot:run

