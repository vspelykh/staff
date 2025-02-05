package com.andersenlab.staff.service;

import com.andersenlab.staff.exception.ResourceNotFoundException;
import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.GetEmployeesRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Service interface for managing employee-related operations.
 * Provides methods to retrieve and update employee information.
 */
public interface EmployeeService {

    /**
     * Retrieves a paginated list of employees based on the provided filter criteria.
     *
     * @param request the request object containing filter and pagination information
     * @return a {@link Page} of {@link EmployeeDto} objects matching the filter criteria
     */
    Page<EmployeeDto> getAllEmployees(GetEmployeesRequest request);

    /**
     * Retrieves an employee by their unique identifier.
     *
     * @param id the UUID of the employee to retrieve
     * @return the {@link EmployeeDto} corresponding to the provided id
     * @throws ResourceNotFoundException if no employee with the given id is found
     */
    EmployeeDto getEmployeeById(UUID id);

    /**
     * Deactivates an employee by setting their 'active' status to false.
     *
     * @param id the UUID of the employee to deactivate
     * @throws ResourceNotFoundException if no active employee with the given id is found
     */
    void deactivateEmployee(UUID id);
}
