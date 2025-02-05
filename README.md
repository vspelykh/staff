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

## **Database Schema (Simplified)**

The system uses inheritance or composition for structuring employees in the database:

- **Approach 1: Inheritance (****`@MappedSuperclass`**** or ****`@Inheritance`****)**
- **Approach 2: Composition (****`@Embedded`****)**

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
 git clone https://github.com/your-repository/employee-management.git
 cd employee-management
```

### **2. Configure the database:**

Update `application.yml` or `application.properties` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/employee_db
    username: user
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
curl -X GET http://localhost:8080/employees
```

## **Future Improvements:**

- Role-based access control (RBAC)
- Pagination for employee lists
- Advanced filtering options
- GraphQL API support

---

This project serves as a technical assessment and can be further extended based on real-world needs.

напиши так, чтобы я мог скопировать вместе с разметкой md

