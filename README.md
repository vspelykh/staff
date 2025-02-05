# Employee Management System (Assessment Task)

## Description

This is a Spring Boot application designed for an assessment task. The application provides a RESTful API for managing employees in a company. Employees are categorized into different types, and the system allows CRUD operations, sorting, and assignment of subordinates to managers.

## Technical Specification

### **Employee Types:**

1. **Worker**

    - Full Name
    - Date of Birth
    - Date of Hire

2. **Manager**

    - Full Name
    - Date of Birth
    - Date of Hire
    - List of subordinates (workers under this manager)

3. **Other Employees** (e.g., Executives, Secretaries, etc.)

    - Full Name
    - Date of Birth
    - Date of Hire
    - Textual description of the employee

### **Features:**

- Add, remove, update employees
- Change employee type
- Assign workers to a manager
- Sort employees by name and hire date
- Store employee data in a relational database
- Database migration and initialization using Liquibase
- RESTful API for employee management

## **Technology Stack:**

- **Java 17**
- **Spring Boot 3** (Spring Web, Spring Data JPA)
- **PostgreSQL / MySQL** (Relational Database)
- **Liquibase** (Database versioning)
- **Lombok** (Reducing boilerplate code)
- **MapStruct** (DTO mapping)
- **JUnit 5 / Mockito** (Unit and integration testing)

## **Database Schema**

## **API Endpoints**

| Method     | Endpoint                                           | Description                  |                              |
| ---------- | -------------------------------------------------- | ---------------------------- | ---------------------------- |
| **GET**    | `/employees`                                       | Retrieve all employees       |                              |
| **GET**    | `/employees/{id}`                                  | Get an employee by ID        |                              |
| **POST**   | `/employees`                                       | Create a new employee        |                              |
| **PUT**    | `/employees/{id}`                                  | Update an employee           |                              |
| **DELETE** | `/employees/{id}`                                  | Delete an employee           |                              |
| **PUT**    | `/employees/{workerId}/assign-manager/{managerId}` | Assign a worker to a manager |                              |
| **GET**    | \`/employees/sorted?by=name                        | hireDate\`                   | Get sorted list of employees |

## **Setup & Installation**

### **1. Clone the repository:**

```bash
 git clone https://github.com/vspelykh/staff.git
 cd employee-management
```

### **2. Configure the database:**

Update `application.yml` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/staff_db
    username: postgres
    password: password
```

### **3. Build & Run:**

```bash
mvn clean install
mvn spring-boot:run
```

### **4. API Testing:**

Use Postman or `curl` to test API endpoints.

```bash
curl -X GET http://localhost:8900/echo
```