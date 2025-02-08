package com.andersenlab.staff.service.impl;

import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.repository.ManagerEmployeeRepository;
import com.andersenlab.staff.service.ManagerService;
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

    private final ManagerEmployeeRepository managerEmployeeRepository;

    @Override
    public List<EmployeeDto> getSubordinates(UUID managerId) {
        return List.of();
    }

    @Override
    @Transactional
    public void removeAllSubordinates(UUID employeeId) {
        log.info("Delete employee details for manager with id: {}", employeeId);
        managerEmployeeRepository.removeAllByManagerId(employeeId);
    }
}
