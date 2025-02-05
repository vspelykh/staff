package com.andersenlab.staff.service.impl;

import com.andersenlab.staff.exception.ResourceNotFoundException;
import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.GetEmployeesRequest;
import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.repository.EmployeeRepository;
import com.andersenlab.staff.service.EmployeeService;
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

    private final EmployeeRepository employeeRepository;
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
    public void deactivateEmployee(UUID id) {
        Employee employeeToDeactivate = employeeRepository.findByIdAndActiveIsTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id:" + id + " not found"));
        employeeToDeactivate.setActive(false);
        employeeRepository.save(employeeToDeactivate);
        log.info("Employee with id: {} deactivated", id);
    }
}
