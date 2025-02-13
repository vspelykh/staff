package com.andersenlab.staff.model.dto;

import com.andersenlab.staff.model.entity.EmployeeType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeType {

    @NotNull
    private EmployeeType type;
}
