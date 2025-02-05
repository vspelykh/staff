package com.andersenlab.staff.controller;

import com.andersenlab.staff.model.assembler.EmployeeModelAssembler;
import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.GetEmployeesRequest;
import com.andersenlab.staff.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeModelAssembler employeeModelAssembler;

    @GetMapping
    public PagedModel<EmployeeDto> getAllEmployees(
            @Valid GetEmployeesRequest request,
            PagedResourcesAssembler<EmployeeDto> assembler) {

        log.info("REST request to get all employees : {}", request);
        Page<EmployeeDto> employees = employeeService.getAllEmployees(request);
        return assembler.toModel(employees, employeeModelAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<EmployeeDto> getEmployeeById(@PathVariable UUID id) {
        log.info("REST request to get employee : {}", id);
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).getEmployeeById(id)).withSelfRel()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable UUID id) {
        log.info("REST request to delete employee : {}", id);
        employeeService.deactivateEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
