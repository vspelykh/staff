package com.andersenlab.staff.service.impl;

import com.andersenlab.staff.exception.ResourceNotFoundException;
import com.andersenlab.staff.model.dto.*;
import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.EmployeeDetails;
import com.andersenlab.staff.model.entity.EmployeeType;
import com.andersenlab.staff.repository.EmployeeDetailsRepository;
import com.andersenlab.staff.repository.EmployeeRepository;
import com.andersenlab.staff.service.EmployeeService;
import com.andersenlab.staff.service.ManagerService;
import com.andersenlab.staff.service.mapper.EmployeeMapper;
import com.andersenlab.staff.service.specification.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final ManagerService managerService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeDetailsRepository employeeDetailsRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDto> getAllEmployees(GetEmployeesRequest request) {
        Specification<Employee> spec = new EmployeeSpecification(request);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        return employeeRepository.findAll(spec, pageable).map(employeeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(UUID id) {
        return employeeMapper.toDto(employeeRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id:" + id + " not found")));
    }

    @Override
    @Transactional
    public EmployeeDto createEmployee(CreateEmployeeRequest request) {
        Employee employee = employeeMapper.toEntity(request);

        if (request.getType().equals(EmployeeType.OTHER)) {
            EmployeeDetails employeeDetails = employee.getEmployeeDetails();
            log.info("Set employee details: {}, request: {}", employeeDetails, request);
            employeeDetails.setEmployee(employee);
        }

        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(UUID id, UpdateEmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employeeMapper.updateEntityFromRequest(request, existingEmployee);
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return employeeMapper.toDto(updatedEmployee);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployeeType(UUID id, UpdateEmployeeType request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        EmployeeType previousType = employee.getType();
        employee.setType(request.getType());
        EmployeeDetails employeeDetails = employee.getEmployeeDetails();
        if (request.getType().equals(EmployeeType.WORKER) && employeeDetails != null) {
            log.info("Delete employee details for employee with id: {}", id);
            employeeDetailsRepository.delete(employeeDetails);
            employee.setEmployeeDetails(null);
        }

        if (EmployeeType.MANAGER.equals(previousType)) {
            managerService.removeAllSubordinates(id);
        }

        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public void deactivateEmployee(UUID id) {
        Employee employeeToDeactivate = employeeRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id:" + id + " not found"));

        employeeToDeactivate.setActive(false);
        employeeRepository.save(employeeToDeactivate);
        log.info("Employee with id: {} deactivated", id);
    }
}
