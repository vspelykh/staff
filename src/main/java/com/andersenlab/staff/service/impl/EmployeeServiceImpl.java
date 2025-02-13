package com.andersenlab.staff.service.impl;

import com.andersenlab.staff.exception.ResourceNotFoundException;
import com.andersenlab.staff.model.dto.*;
import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.EmployeeDetails;
import com.andersenlab.staff.model.entity.EmployeeType;
import com.andersenlab.staff.repository.EmployeeDetailsRepository;
import com.andersenlab.staff.repository.EmployeeRepository;
import com.andersenlab.staff.service.EmployeeFactory;
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
    private final EmployeeFactory employeeFactory;
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
        Employee employee = employeeFactory.createEmployee(request);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created: {}", savedEmployee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(UUID id, UpdateEmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employeeMapper.updateEntityFromRequest(request, existingEmployee);
        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        if (updatedEmployee.getType().equals(EmployeeType.OTHER)) {
            EmployeeDetails employeeDetails = employeeDetailsRepository.findByEmployeeId(id).orElseThrow(
                    () -> new ResourceNotFoundException("Employee details not found with id: " + id));
            employeeDetails.setDescription(request.getDescription());
        }
        return employeeMapper.toDto(updatedEmployee);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployeeType(UUID id, UpdateEmployeeType request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        EmployeeType previousType = employee.getType();
        if (request.getType().equals(previousType)){
            return employeeMapper.toDto(employee);
        }
        employee.setType(request.getType());

        if (previousType.equals(EmployeeType.OTHER)) {
         employeeDetailsRepository.deleteByEmployeeId(id);
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
