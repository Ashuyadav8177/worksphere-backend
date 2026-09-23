# WorkSphere Backend

WorkSphere is a full-stack employee and office management platform designed to manage employees, departments, attendance, leaves, tasks, documents, notifications, and audit activities through secure REST APIs.

This repository contains the backend service of the WorkSphere application, built with Spring Boot and secured using Spring Security and JWT-based authentication.

## 🚀 Features

- JWT-based authentication and authorization
- Role-based access control
- Employee management
- Department management
- Attendance management
- Leave management
- Task management
- Document upload and management
- Notifications
- Audit logs
- Dashboard summary APIs
- Employee soft delete
- Request validation
- Global exception handling
- Pagination and filtering
- MySQL database integration
- RESTful API architecture

## 🛠️ Tech Stack

### Backend
- Java 24
- Spring Boot 4.1.1
- Spring Security
- Spring Data JPA
- Hibernate
- JWT
- Lombok
- Maven

### Database
- MySQL 8

### API Testing
- Postman

## 🔐 Security

WorkSphere uses Spring Security with JWT-based authentication.

Security features include:

- User authentication using JWT
- Password hashing using BCrypt
- Role-based authorization
- Protected REST APIs
- Stateless authentication
- CORS configuration
- Public authentication endpoints
- Centralized authentication handling

## 📦 Backend Modules

### Authentication
- User registration
- User login
- JWT token generation
- JWT token validation

### Employees
- Create employee
- View employees
- Update employee
- Soft delete employee
- Employee status management

### Departments
- Department management
- Department-based employee organization

### Attendance
- Employee check-in
- Employee check-out
- Personal attendance history
- Employee attendance history
- Attendance status tracking

### Leaves
- Leave application
- Leave management
- Leave status tracking

### Tasks
- Task creation and management
- Task status tracking
- Task assignment

### Documents
- PDF document upload
- Document management
- File validation
- Maximum upload size: 10 MB

### Notifications
- User notifications
- Notification read/unread status
- Test notification support for administrators

### Audit Logs
- Administrative activity tracking
- Action-based filtering
- Resource-based filtering
- Pagination

### Dashboard
- Employee statistics
- Attendance summary
- Leave summary
- Task statistics
- Recent activity information

## 🏗️ Project Structure

```text
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── worksphere/
│   │   │           └── backend/
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## ⚙️ Environment Variables

Sensitive configuration is not stored in the repository.

The backend requires the following environment variables:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

Example:

```text
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=your_secure_jwt_secret
```

> Never commit real database passwords or JWT secrets to GitHub.

## 🗄️ Database Configuration

WorkSphere uses MySQL as its relational database.

Create the database:

```sql
CREATE DATABASE worksphere;
```

Database configuration:

```text
Database: MySQL
Database Name: worksphere
Port: 3306
```

Database credentials should be provided through environment variables.

## ▶️ Run the Backend Locally

### 1. Clone the repository

```bash
git clone https://github.com/Ashuyadav8177/worksphere-backend.git
```

### 2. Navigate to the project

```bash
cd worksphere-backend
```

### 3. Configure environment variables

Set the following variables:

```text
DB_USERNAME
DB_PASSWORD
JWT_SECRET
```

### 4. Start the application

#### Windows

```bash
mvnw.cmd spring-boot:run
```

#### Linux/macOS

```bash
./mvnw spring-boot:run
```

The backend will run on:

```text
http://localhost:8080
```

## 🧪 API Testing

The WorkSphere REST APIs were tested using Postman.

### Authentication Flow

```text
Register
   ↓
Login
   ↓
Receive JWT
   ↓
Send JWT with protected requests
   ↓
Access authorized resources
```

Protected API requests use the JWT token in the `Authorization` header:

```text
Authorization: Bearer <JWT_TOKEN>
```

## 📌 API Highlights

| Module | Example Endpoint |
|---|---|
| Authentication | `/api/auth/**` |
| Employees | `/api/employees/**` |
| Departments | `/api/departments/**` |
| Attendance | `/api/attendance/**` |
| Leaves | `/api/leaves/**` |
| Tasks | `/api/tasks/**` |
| Documents | `/api/documents/**` |
| Notifications | `/api/notifications/**` |
| Audit Logs | `/api/audit-logs` |
| Dashboard | `/api/dashboard/summary` |

## 🧩 Architecture

WorkSphere follows a layered backend architecture:

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

### Security Flow

```text
Client
   ↓
JWT Token
   ↓
JWT Filter
   ↓
Spring Security
   ↓
Authentication & Authorization
   ↓
Controller
   ↓
Service
   ↓
Database
```

## 🔄 Request Flow

```text
Frontend
   ↓
REST API Request
   ↓
JWT Authentication
   ↓
Controller
   ↓
Service Layer
   ↓
Repository Layer
   ↓
MySQL
   ↓
Response
   ↓
Frontend
```

## 🌐 Frontend

The WorkSphere frontend is maintained in a separate repository:

https://github.com/Ashuyadav8177/worksphere-frontend

## 📊 Project Status

The WorkSphere backend is implemented and integrated with the React frontend.

Core modules including:

- Authentication
- Employees
- Departments
- Attendance
- Leaves
- Tasks
- Documents
- Notifications
- Audit Logs
- Dashboard

are implemented and integrated with the application.

## 🔮 Future Enhancements

- Email notification integration
- Advanced reporting
- Cloud file storage
- Production deployment
- Automated CI/CD pipeline
- Advanced analytics
- Improved monitoring and observability

## 👨‍💻 Author

**Ashutosh Yadav**

B.Tech Information Technology

---

⭐ If you find this project useful, consider giving the repository a star.