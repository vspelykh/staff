package com.andersenlab.staff.controller;

import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @GetMapping("/{managerId}/subordinates")
    public List<EmployeeDto> getSubordinates(@PathVariable UUID managerId) {
        return managerService.getSubordinates(managerId);
    }
}
