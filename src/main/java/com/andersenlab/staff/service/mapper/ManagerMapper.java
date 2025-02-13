package com.andersenlab.staff.service.mapper;

import com.andersenlab.staff.model.dto.ManagerDto;
import com.andersenlab.staff.model.entity.Manager;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface ManagerMapper extends EntityMapper<ManagerDto, Manager> {

    @Override
    default Manager toEntity(ManagerDto dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
