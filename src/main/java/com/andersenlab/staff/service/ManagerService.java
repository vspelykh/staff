package com.andersenlab.staff.service;

import com.andersenlab.staff.model.dto.EmployeeDto;

import java.util.List;
import java.util.UUID;

public interface ManagerService {
    List<EmployeeDto> getSubordinates(UUID managerId);

    void removeAllSubordinates(UUID id);
}