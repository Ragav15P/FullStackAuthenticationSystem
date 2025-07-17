# 🔐 Spring Boot Authentication System

A full-fledged backend authentication system built using **Spring Boot**. It includes secure user management, JWT-based login, OTP verification, email notifications, and account verification.

---

## 🚀 Features

- ✅ User Registration with Email Verification
- 🔑 Login with JWT Token Generation
- ✉️ OTP-based Email Verification
- 🔁 Forgot Password & Password Reset via Email OTP
- 🔐 Secured Endpoints with Role-Based Access
- 📩 Automated Email Notifications (JavaMailSender)
- 🧪 Fully Testable APIs (Postman-compatible)
- 📦 Future-Ready for Additional Microservices or Frontend Integration

---

## 🛠️ Tech Stack

| Layer      | Tech |
|------------|------|
| Language   | Java 17 |
| Framework  | Spring Boot |
| Security   | Spring Security + JWT |
| Database   | MySQL |
| ORM        | Spring Data JPA |
| Mail       | JavaMailSender |
| Build Tool | Maven |

---

## 📁 Project Structure

src/
├── main/
│ ├── java/
│ │ └── com.example.auth/
│ │ ├── controller/
│ │ ├── service/
│ │ ├── model/
│ │ ├── repository/
│ │ ├── config/
│ │ └── security/
│ └── resources/
│ ├── application.properties
│ └── templates/ (for email templates if any)
└── test/













---

## 🔧 Configuration

This project uses environment variables for sensitive configuration.  
Create your own `application.properties` file based on the below sample:

```properties
# Database
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${EMAIL_USERNAME}
spring.mail.password=${EMAIL_PASSWORD}

# JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000






| Endpoint             | Method | Description                  |
| -------------------- | ------ | ---------------------------- |
| `/api/auth/register` | POST   | Register new user            |
| `/api/auth/verify`   | POST   | Verify OTP/email             |
| `/api/auth/login`    | POST   | Login and get JWT            |
| `/api/auth/forgot`   | POST   | Request password reset       |
| `/api/auth/reset`    | POST   | Reset password with OTP      |
| `/api/user/profile`  | GET    | (Protected) Get user profile |







📦 Future Plans
🔄 Token refresh mechanism

🧩 Integration with Google OAuth

🌐 Swagger/OpenAPI documentation

💼 Admin panel endpoints

🔍 Account lockout & rate limiting

🤝 Contributing
Contributions are welcome! Open an issue or submit a pull request for feature requests or bug fixes.




📄 License
This project is licensed under the MIT License.

