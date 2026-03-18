# User Service

## Overview
The **User Service** is a Spring Boot-based microservice designed to manage user-related operations in an e-commerce application. It includes features such as user authentication, role-based access control, and integration with external services like Eureka for service discovery.

---

## Features
- **User Management**: CRUD operations for users.
- **Authentication & Authorization**: JWT-based security with role-based access control.
- **Database Integration**: PostgreSQL as the database with Liquibase for schema management.
- **Service Discovery**: Eureka client for service registration and discovery.
- **API Documentation**: Swagger UI for API exploration.
- **Actuator Endpoints**: Health checks and metrics for monitoring.

---

## Technologies Used
- **Programming Language**: Java 21
- **Frameworks**: Spring Boot, Spring Security, Spring Data JPA, Spring Cloud
- **Database**: PostgreSQL
- **Build Tool**: Maven
- **Version Control**: Git
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Database Versioning**: Liquibase

---

## Prerequisites
- **Java**: JDK 21
- **Maven**: 3.8+
- **PostgreSQL**: 13+
- **Eureka Server**: Running on `http://localhost:8761`

---

## Configuration
### `application.yml`
The application is configured using the `application.yml` file. Key configurations include:
- **Server Port**: `8081`
- **Database**: PostgreSQL connection details.
- **JWT**: Secret key and expiration time.
- **Eureka**: Service discovery configuration.
- **Liquibase**: Database changelog management.

---

## Getting Started
### Clone the Repository
```bash
git clone <repository-url>
cd user-service