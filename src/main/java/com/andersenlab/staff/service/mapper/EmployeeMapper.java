package com.andersenlab.staff.service.mapper;

import com.andersenlab.staff.model.dto.CreateEmployeeRequest;
import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.UpdateEmployeeRequest;
import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.EmployeeDetails;
import com.andersenlab.staff.model.entity.EmployeeType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDto, Employee> {

    @Mapping(source = "employeeDetails.description", target = "description")
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "active", constant = "true")
    @Mapping(target = "employeeDetails", expression = "java(mapEmployeeDetails(request))")
    Employee toEntity(CreateEmployeeRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "employeeDetails", expression = "java(updateEmployeeDetails(employee.getEmployeeDetails(), request))")
    void updateEntityFromRequest(UpdateEmployeeRequest request, @MappingTarget Employee employee);

    default EmployeeDetails mapEmployeeDetails(CreateEmployeeRequest request) {
        if (request.getType() == EmployeeType.OTHER) {
            EmployeeDetails details = new EmployeeDetails();
            details.setDescription(request.getDescription());
            return details;
        }
        return null;
    }

    default EmployeeDetails updateEmployeeDetails(EmployeeDetails existingDetails, UpdateEmployeeRequest request) {
        if (existingDetails != null && request.getDescription() != null) {
            existingDetails.setDescription(request.getDescription());
        }
        return existingDetails;
    }
}
