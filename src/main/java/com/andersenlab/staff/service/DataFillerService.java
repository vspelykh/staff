package com.andersenlab.staff.service;

import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.EmployeeType;
import com.andersenlab.staff.repository.EmployeeRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Profile("test")
@Service
@RequiredArgsConstructor
public class DataFillerService {

    private final EmployeeRepository employeeRepository;
    private final Faker faker = new Faker();

    public void fillDatabaseWithFakeData(int numberOfEmployees) {
        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < numberOfEmployees; i++) {
            Employee employee = new Employee();
            employee.setFirstName(faker.name().firstName());
            employee.setLastName(faker.name().lastName());
            employee.setMiddleName(faker.name().fullName());

            java.util.Date birthDate = faker.date().birthday();
            employee.setBirthDate(new Date(birthDate.getTime()));

            java.util.Date hireDate = faker.date().past(3650, java.util.concurrent.TimeUnit.DAYS);
            employee.setHireDate(new Date(hireDate.getTime()));
            employee.setType(EmployeeType.WORKER);
            employees.add(employee);
        }
        employeeRepository.saveAll(employees);
    }
}
