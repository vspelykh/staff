package com.andersenlab.staff.model.dto;

import com.andersenlab.staff.model.entity.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerDto {

    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDate;
    private Date hireDate;
    private EmployeeType type;
    private boolean active;
    private List<EmployeeDto> subordinates;
}
