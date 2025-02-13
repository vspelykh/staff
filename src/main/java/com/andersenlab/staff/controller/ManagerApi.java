package com.andersenlab.staff.controller;

import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.ManagerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Tag(name = "Managers", description = "Management of managers and their subordinates")
public interface ManagerApi {

    @Operation(summary = "Get subordinates of a manager")
    @GetMapping("/{managerId}/subordinates")
    CollectionModel<EmployeeDto> getSubordinates(
            @Parameter(description = "Manager ID", required = true) @PathVariable UUID managerId);

    @Operation(summary = "Get information about a manager")
    @GetMapping("/{managerId}")
    ResponseEntity<EntityModel<ManagerDto>> getManager(
            @Parameter(description = "Manager ID", required = true) @PathVariable UUID managerId);

    @Operation(summary = "Assign an employee as a subordinate to a manager")
    @PostMapping("/{managerId}/subordinates/{employeeId}")
    ResponseEntity<EntityModel<ManagerDto>> assignSubordinate(
            @Parameter(description = "Manager ID", required = true) @PathVariable UUID managerId,
            @Parameter(description = "Employee ID", required = true) @PathVariable UUID employeeId);

    @Operation(summary = "Remove an employee from a manager's subordinates")
    @DeleteMapping("/{managerId}/subordinates/{employeeId}")
    ResponseEntity<Void> removeSubordinate(
            @Parameter(description = "Manager ID", required = true) @PathVariable UUID managerId,
            @Parameter(description = "Employee ID", required = true) @PathVariable UUID employeeId);
}
