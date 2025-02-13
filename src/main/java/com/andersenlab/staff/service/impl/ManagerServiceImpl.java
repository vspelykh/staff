package com.andersenlab.staff.service.impl;

import com.andersenlab.staff.exception.ResourceNotFoundException;
import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.ManagerDto;
import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.Manager;
import com.andersenlab.staff.model.entity.ManagerEmployee;
import com.andersenlab.staff.repository.ManagerRepository;
import com.andersenlab.staff.repository.EmployeeRepository;
import com.andersenlab.staff.repository.ManagerEmployeeRepository;
import com.andersenlab.staff.service.ManagerService;
import com.andersenlab.staff.service.mapper.EmployeeMapper;
import com.andersenlab.staff.service.mapper.ManagerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final ManagerMapper managerMapper;

    private final ManagerEmployeeRepository managerEmployeeRepository;
    private final ManagerRepository managerRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getSubordinates(UUID managerId) {
        List<ManagerEmployee> managerEmployees = managerEmployeeRepository
                .findByManagerIdOrderByEmployeeLastName(managerId);

        List<UUID> employeeUuids = managerEmployees.stream()
                .map(managerEmployee -> managerEmployee.getEmployee().getId())
                .toList();

        return employeeMapper.toDto(employeeRepository.findAllByIdIn(employeeUuids));
    }

    @Override
    @Transactional(readOnly = true)
    public ManagerDto getManagerById(UUID managerId) {
        return managerRepository.findByIdAndActiveIsTrue(managerId).map(managerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found by id " + managerId));
    }

    @Override
    @Transactional
    public ManagerDto assignSubordinate(UUID managerId, UUID employeeId) {
        Manager manager = managerRepository.findByIdAndActiveIsTrue(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found by id " + managerId));

        Employee employee = employeeRepository.findByIdAndActiveIsTrue(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found by id " + employeeId));

        manager.getSubordinates().add(employee);
        return managerMapper.toDto(manager);
    }

    @Override
    @Transactional
    public void removeSubordinate(UUID managerId, UUID employeeId) {
        int deletedCount = managerEmployeeRepository.removeByManagerIdAndEmployeeId(managerId, employeeId);

        if (deletedCount == 0) {
            log.warn("ManagerEmployee not found by managerId {} and employeeId {}", managerId, employeeId);
            throw new ResourceNotFoundException("ManagerEmployee not found by managerId " + managerId);
        }
    }

    @Override
    @Transactional
    public void removeAllSubordinates(UUID employeeId) {
        log.info("Delete employee details for manager with id: {}", employeeId);
        managerEmployeeRepository.removeAllByManagerId(employeeId);
    }
}
