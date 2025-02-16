# **Employee Management System**

## **Overview**

The **Employee Management System** is a Spring Boot-based RESTful API designed for managing employees in an organization. It supports various operations, such as CRUD, assigning subordinates to managers, and sorting employees. The system stores data in a relational database and ensures maintainability through technologies like Liquibase and MapStruct.

---

## **Features**

1. **Employee Management**
   - Add, remove, update employees.
   - Change employee type.
   - Retrieve employees sorted by name or hire date.

2. **Manager Operations**
   - Assign workers to managers.
   - Retrieve and manage subordinates.

3. **Technical Capabilities**
   - Database versioning with Liquibase.
   - Data transfer via DTOs (using MapStruct).
   - RESTful API endpoints for seamless integration.

---

## **Employee Types**

1. **Worker**
   - Full Name
   - Date of Birth
   - Date of Hire

2. **Manager**
   - Full Name
   - Date of Birth
   - Date of Hire
   - Subordinates (list of employees assigned to this manager)

3. **Other Employees** (e.g., Executives, Secretaries)
   - Full Name
   - Date of Birth
   - Date of Hire
   - Textual Description

---

## **Technology Stack**

- **Java 17**
- **Spring Boot 3**
   - Spring Web
   - Spring Data JPA
- **PostgreSQL** (or MySQL)
- **Liquibase** (Database migrations)
- **MapStruct** (DTO mapping)
- **Lombok** (Reducing boilerplate code)
- **Redis** (Caching)
- **JUnit 5** / **Mockito** (Testing)
- **SpringDoc** (API documentation)

---

## **Database Schema**

The schema supports polymorphic employee types with relationships for manager-subordinate assignments. It uses normalized structures for scalability and data consistency.

---

## **API Documentation**

### **Endpoints**

| **Method** | **Endpoint**                                   | **Description**                          |
|------------|-----------------------------------------------|------------------------------------------|
| **GET**    | `/employees`                                  | Retrieve all employees.                  |
| **GET**    | `/employees/{id}`                             | Get an employee by ID.                   |
| **POST**   | `/employees`                                  | Create a new employee.                   |
| **PUT**    | `/employees/{id}`                             | Update an existing employee.             |
| **PATCH**  | `/employees/{id}`                             | Change employee type.                    |
| **DELETE** | `/employees/{id}`                             | Delete an employee by ID.                |
| **PUT**    | `/employees/{workerId}/assign-manager/{managerId}` | Assign a worker to a manager.            |
| **GET**    | `/employees/sorted?by=name,hireDate`          | Retrieve sorted employees by criteria.   |
| **GET**    | `/managers/{managerId}/subordinates`          | Get subordinates of a manager.           |
| **POST**   | `/managers/{managerId}/subordinates/{employeeId}` | Add a subordinate to a manager.          |
| **DELETE** | `/managers/{managerId}/subordinates/{employeeId}` | Remove a subordinate from a manager.     |

---
## **Setup & Installation**

1. **Clone the Repository**
   ```bash
   git clone https://github.com/vspelykh/staff.git
   cd staff

