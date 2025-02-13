package com.andersenlab.staff.service;

import com.andersenlab.staff.exception.ResourceNotFoundException;
import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.ManagerDto;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing relationships between managers and their subordinates.
 * Provides operations for retrieving, assigning, and removing subordinates.
 */
public interface ManagerService {

    /**
     * Retrieves a list of subordinates (employees) for a given manager.
     *
     * @param managerId the ID of the manager whose subordinates are to be retrieved
     * @return a list of {@link EmployeeDto} objects representing the subordinates
     * @throws ResourceNotFoundException if the manager with the given ID does not exist or is inactive
     */
    List<EmployeeDto> getSubordinates(UUID managerId);

    /**
     * Retrieves the manager's details based on the manager's ID.
     *
     * @param managerId the ID of the manager to be retrieved
     * @return a {@link ManagerDto} object containing the manager's details
     * @throws ResourceNotFoundException if the manager with the given ID does not exist or is inactive
     */
    ManagerDto getManagerById(UUID managerId);

    /**
     * Assigns an employee as a subordinate to a manager.
     *
     * @param managerId the ID of the manager to whom the employee will be assigned
     * @param employeeId the ID of the employee to be assigned as a subordinate
     * @return the updated {@link ManagerDto} object representing the manager with the assigned subordinate
     * @throws ResourceNotFoundException if the manager or employee with the given ID does not exist or is inactive
     */
    ManagerDto assignSubordinate(UUID managerId, UUID employeeId);

    /**
     * Removes a subordinate from a manager.
     *
     * @param managerId the ID of the manager whose subordinate is to be removed
     * @param employeeId the ID of the employee to be removed from the manager's subordinates
     * @throws ResourceNotFoundException if no relationship exists between the given manager and employee
     */
    void removeSubordinate(UUID managerId, UUID employeeId);

    /**
     * Removes all subordinates of a specific employee.
     *
     * @param employeeId the ID of the employee whose subordinates are to be removed
     * @throws ResourceNotFoundException if no manager-subordinate relationships exist for the given employee
     */
    void removeAllSubordinates(UUID employeeId);
}
