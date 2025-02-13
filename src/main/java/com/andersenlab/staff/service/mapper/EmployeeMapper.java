package com.andersenlab.staff.service.mapper;

import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.dto.UpdateEmployeeRequest;
import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.EmployeeType;
import com.andersenlab.staff.model.entity.OtherEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDto, Employee> {

    @Mapping(target = "description", expression = "java(mapEmployeeDetails(employee))")
    EmployeeDto toDto(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    void updateEntityFromRequest(UpdateEmployeeRequest request, @MappingTarget Employee employee);

    default String mapEmployeeDetails(Employee employee) {
        if (employee.getType() == EmployeeType.OTHER) {
            OtherEmployee otherEmployee = (OtherEmployee) employee;
            return otherEmployee.getDetails().getDescription();
        }
        return null;
    }
}
