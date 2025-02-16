package com.andersenlab.staff.controller.impl;

import com.andersenlab.staff.config.security.AdminAccess;
import com.andersenlab.staff.config.security.AuthAccess;
import com.andersenlab.staff.controller.EmployeeApi;
import com.andersenlab.staff.model.assembler.EmployeeModelAssembler;
import com.andersenlab.staff.model.dto.*;
import com.andersenlab.staff.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;
    private final EmployeeModelAssembler employeeModelAssembler;

    @AuthAccess
    @GetMapping
    public PagedModel<EmployeeDto> getAllEmployees(
            @Valid GetEmployeesRequest request,
            PagedResourcesAssembler<EmployeeDto> assembler) {

        log.info("REST request to get all employees : {}", request);
        Page<EmployeeDto> employees = employeeService.getAllEmployees(request);
        return assembler.toModel(employees, employeeModelAssembler);
    }

    @AuthAccess
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeDto>> getEmployeeById(@PathVariable UUID id) {
        log.info("REST request to get employee : {}", id);

        EmployeeDto employee = employeeService.getEmployeeById(id);
        EntityModel<EmployeeDto> entityModel = EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).getEmployeeById(id)).withSelfRel()
        );

        return ResponseEntity.ok(entityModel);
    }

    @AdminAccess
    @PostMapping
    public ResponseEntity<EntityModel<EmployeeDto>> createEmployee(@RequestBody CreateEmployeeRequest request) {
        log.info("REST request to create employee : {}", request);
        EmployeeDto employee = employeeService.createEmployee(request);

        EntityModel<EmployeeDto> entityModel = EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel()
        );

        return ResponseEntity
                .created(linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).toUri())
                .body(entityModel);
    }

    @AdminAccess
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeDto>> updateEmployee(
            @PathVariable UUID id, @RequestBody UpdateEmployeeRequest request) {
        log.info("REST request to update employee with id: {} : {}", id, request);
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, request);
        EntityModel<EmployeeDto> entityModel = EntityModel.of(updatedEmployee,
                linkTo(methodOn(EmployeeController.class).getEmployeeById(updatedEmployee.getId())).withSelfRel()
        );
        return ResponseEntity.ok().body(entityModel);
    }

    @AdminAccess
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<EmployeeDto>> changeEmployeeType(
            @PathVariable UUID id, @RequestBody UpdateEmployeeType request) {
        log.info("REST request to update employee type with id: {} : {}", id, request);

        employeeService.updateEmployeeType(id, request);
        return ResponseEntity.ok().build();
    }

    @AdminAccess
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable UUID id) {
        log.info("REST request to delete employee : {}", id);
        employeeService.deactivateEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @AdminAccess
    @PostMapping("/{id}/activation")
    public ResponseEntity<EntityModel<EmployeeDto>> activateEmployee(@PathVariable UUID id) {
        log.info("REST request to activate employee : {}", id);
        EmployeeDto employeeDto = employeeService.activateEmployee(id);
        EntityModel<EmployeeDto> entityModel = EntityModel.of(employeeDto,
                linkTo(methodOn(EmployeeController.class).getEmployeeById(employeeDto.getId())).withSelfRel()
        );
        return ResponseEntity.ok(entityModel);
    }
}
