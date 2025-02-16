package com.andersenlab.staff.controller.impl;

import com.andersenlab.staff.config.security.AdminAccess;
import com.andersenlab.staff.config.security.AuthAccess;
import com.andersenlab.staff.controller.ManagerApi;
import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.ManagerDto;
import com.andersenlab.staff.service.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/api/v1/managers")
@RequiredArgsConstructor
public class ManagerController implements ManagerApi {

    private final ManagerService managerService;

    @AuthAccess
    @GetMapping("/{managerId}/subordinates")
    public CollectionModel<EmployeeDto> getSubordinates(@PathVariable UUID managerId) {
        log.info("REST request to get subordinates for manager : {}", managerId);
        List<EmployeeDto> subordinates = managerService.getSubordinates(managerId);

        subordinates.forEach(employee -> employee.add(linkTo(methodOn(EmployeeController.class)
                .getEmployeeById(employee.getId())).withSelfRel()));

        return CollectionModel.of(subordinates,
                linkTo(methodOn(ManagerController.class).getSubordinates(managerId)).withSelfRel()
        );
    }

    @AuthAccess
    @GetMapping("/{managerId}")
    public ResponseEntity<EntityModel<ManagerDto>> getManager(@PathVariable UUID managerId) {
        log.info("REST request to get manager : {}", managerId);
        ManagerDto manager = managerService.getManagerById(managerId);
        EntityModel<ManagerDto> entityModel = EntityModel.of(manager,
                linkTo(methodOn(ManagerController.class).getManager(managerId)).withSelfRel()
        );

        return ResponseEntity.ok(entityModel);
    }

    @AdminAccess
    @PostMapping(("/{managerId}/subordinates/{employeeId}"))
    public ResponseEntity<EntityModel<ManagerDto>> assignSubordinate(
            @PathVariable UUID managerId,
            @PathVariable UUID employeeId) {
        log.info("REST request to assign subordinate for manager : {}, employee id: {}", managerId, employeeId);
        ManagerDto manager = managerService.assignSubordinate(managerId, employeeId);

        EntityModel<ManagerDto> entityModel = EntityModel.of(manager,
                linkTo(methodOn(ManagerController.class).getManager(managerId)).withSelfRel()
        );

        return ResponseEntity.ok(entityModel);
    }

    @AdminAccess
    @DeleteMapping("/{managerId}/subordinates/{employeeId}")
    public ResponseEntity<Void> removeSubordinate(@PathVariable UUID managerId, @PathVariable UUID employeeId) {
        log.info("REST request to remove subordinate for manager, employeeId : {}", employeeId);
        managerService.removeSubordinate(managerId, employeeId);
        return ResponseEntity.noContent().build();
    }
}
