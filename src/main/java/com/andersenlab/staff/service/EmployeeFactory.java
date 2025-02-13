package com.andersenlab.staff.service;

import com.andersenlab.staff.model.dto.CreateEmployeeRequest;
import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.EmployeeDetails;
import com.andersenlab.staff.model.entity.EmployeeType;
import com.andersenlab.staff.model.entity.OtherEmployee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Slf4j
@Service
public class EmployeeFactory {

    public Employee createEmployee(CreateEmployeeRequest request) {
        log.info("Create employee with type: {}", request.getType());
        EmployeeType employeeType = request.getType();
        switch (employeeType) {
            case WORKER, MANAGER:
                return Employee.builder()
                        .firstName(request.getFirstName())
                        .middleName(request.getMiddleName())
                        .lastName(request.getLastName())
                        .birthDate(request.getBirthDate())
                        .hireDate(request.getHireDate())
                        .type(employeeType)
                        .active(true)
                        .build();
            case OTHER:
                OtherEmployee otherEmployee = from(
                        request.getFirstName(),
                        request.getMiddleName(),
                        request.getLastName(),
                        request.getBirthDate(),
                        request.getHireDate()
                );

                EmployeeDetails employeeDetails = new EmployeeDetails();
                employeeDetails.setEmployee(otherEmployee);
                otherEmployee.setDetails(employeeDetails);

                return otherEmployee;

            default:
                throw new IllegalArgumentException("Unknown employee type: " + employeeType);
        }
    }

    private static OtherEmployee from(String firstName, String middleName, String lastName, Date birthDate, Date hireDate) {
        OtherEmployee employee = new OtherEmployee();
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setLastName(lastName);
        employee.setBirthDate(birthDate);
        employee.setHireDate(hireDate);
        employee.setType(EmployeeType.OTHER);
        employee.setActive(true);
        return employee;
    }
}