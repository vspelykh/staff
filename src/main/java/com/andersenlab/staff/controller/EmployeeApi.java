package com.andersenlab.staff.controller;

import com.andersenlab.staff.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Employees", description = "Employee management operations")
public interface EmployeeApi {

    @Operation(summary = "Get list of employees")
    @GetMapping
    PagedModel<EmployeeDto> getAllEmployees(
            @Parameter(description = "Request for employee filtering") @Valid GetEmployeesRequest request,
            PagedResourcesAssembler<EmployeeDto> assembler);

    @Operation(summary = "Get employee by ID")
    @GetMapping("/{id}")
    ResponseEntity<EntityModel<EmployeeDto>> getEmployeeById(
            @Parameter(description = "Employee ID") @PathVariable UUID id);

    @Operation(summary = "Create a new employee")
    @PostMapping
    ResponseEntity<EntityModel<EmployeeDto>> createEmployee(
            @Parameter(description = "Data for creating a new employee") @RequestBody CreateEmployeeRequest request);

    @Operation(summary = "Update employee details")
    @PutMapping("/{id}")
    ResponseEntity<EntityModel<EmployeeDto>> updateEmployee(
            @Parameter(description = "Employee ID") @PathVariable UUID id,
            @Parameter(description = "Data for updating the employee") @RequestBody UpdateEmployeeRequest request);

    @Operation(summary = "Change employee type")
    @PatchMapping("/{id}")
    ResponseEntity<EntityModel<EmployeeDto>> changeEmployeeType(
            @Parameter(description = "Employee ID") @PathVariable UUID id,
            @Parameter(description = "New employee type") @RequestBody UpdateEmployeeType request);

    @Operation(summary = "Delete employee")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEmployeeById(
            @Parameter(description = "Employee ID") @PathVariable UUID id);

    @Operation(summary = "Activate employee")
    @PostMapping("/{id}/activation")
    ResponseEntity<EntityModel<EmployeeDto>> activateEmployee(
            @Parameter(description = "Employee ID") @PathVariable UUID id);
}
