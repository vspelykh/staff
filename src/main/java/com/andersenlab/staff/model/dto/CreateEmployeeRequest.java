package com.andersenlab.staff.model.dto;

import com.andersenlab.staff.model.entity.EmployeeType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeRequest {

    @NotBlank(message = "First name must not be empty")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Size(max = 50, message = "Middle name must not exceed 50 characters")
    private String middleName;

    @NotBlank(message = "Last name must not be empty")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @PastOrPresent(message = "Hire date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate = new Date(System.currentTimeMillis());

    @NotNull(message = "Employee type is required")
    private EmployeeType type;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}