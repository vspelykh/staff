package com.andersenlab.staff.service.mapper;

import com.andersenlab.staff.model.dto.EmployeeDto;
import com.andersenlab.staff.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDto, Employee> {

    @Mapping(source = "employeeDetails.description", target = "description")
    EmployeeDto toDto(Employee employee);
}
