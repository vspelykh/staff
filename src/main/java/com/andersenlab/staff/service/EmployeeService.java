package com.andersenlab.staff.service;

import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.GetEmployeesRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface EmployeeService {

    Page<EmployeeDto> getAllEmployees(GetEmployeesRequest request);

    EmployeeDto getEmployeeById(UUID id);

}