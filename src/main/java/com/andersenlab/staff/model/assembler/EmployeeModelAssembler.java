package com.andersenlab.staff.model.assembler;

import com.andersenlab.staff.controller.EmployeeController;
import com.andersenlab.staff.model.dto.EmployeeDto;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class EmployeeModelAssembler extends RepresentationModelAssemblerSupport<EmployeeDto, EmployeeDto> {

    public EmployeeModelAssembler() {
        super(EmployeeController.class, EmployeeDto.class);
    }

    @Override
    public @NonNull EmployeeDto toModel(@NonNull EmployeeDto employee) {
        EntityModel<EmployeeDto> model = EntityModel.of(employee);

        model.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel());

        return Objects.requireNonNull(model.getContent());
    }
}
