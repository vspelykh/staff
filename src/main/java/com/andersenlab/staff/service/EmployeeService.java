package com.andersenlab.staff.service;

import com.andersenlab.staff.exception.ResourceNotFoundException;
import com.andersenlab.staff.model.dto.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Service interface for managing employee-related operations.
 * Provides methods to retrieve, create, update, and deactivate employees.
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
     * Creates a new employee based on the provided request data.
     *
     * @param request the {@link CreateEmployeeRequest} containing employee details
     * @return the created {@link EmployeeDto} object
     */
    EmployeeDto createEmployee(CreateEmployeeRequest request);

    /**
     * Updates an existing employee's details.
     *
     * @param id the UUID of the employee to update
     * @param request the {@link UpdateEmployeeRequest} containing updated employee details
     * @return the updated {@link EmployeeDto} object
     * @throws ResourceNotFoundException if no employee with the given id is found
     */
    EmployeeDto updateEmployee(UUID id, UpdateEmployeeRequest request);

    /**
     * Updates the employee's type (e.g., Worker, Manager, Executive).
     *
     * @param id      the UUID of the employee whose type is to be updated
     * @param request the {@link UpdateEmployeeType} containing the new employee type
     * @throws ResourceNotFoundException if no employee with the given id is found
     */
    void updateEmployeeType(UUID id, UpdateEmployeeType request);

    /**
     * Deactivates an employee by setting their 'active' status to false.
     *
     * @param id the UUID of the employee to deactivate
     * @throws ResourceNotFoundException if no active employee with the given id is found
     */
    void deactivateEmployee(UUID id);

    /**
     * Activates an employee by setting their 'active' status to true.
     *
     * @param id the UUID of the employee to deactivate
     * @throws ResourceNotFoundException if no active employee with the given id is found
     */
    EmployeeDto activateEmployee(UUID id);
}
