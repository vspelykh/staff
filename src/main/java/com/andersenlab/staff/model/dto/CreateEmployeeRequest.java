package com.andersenlab.staff.model.dto;

import com.andersenlab.staff.model.entity.EmployeeType;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request to create a new employee")
public class CreateEmployeeRequest {

    @Schema(description = "First name of the employee", example = "John")
    @NotBlank(message = "First name must not be empty")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Schema(description = "Middle name of the employee (optional)", example = "Michael")
    @Size(max = 50, message = "Middle name must not exceed 50 characters")
    private String middleName;

    @Schema(description = "Last name of the employee", example = "Doe")
    @NotBlank(message = "Last name must not be empty")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Schema(description = "Birth date of the employee in 'yyyy-MM-dd' format", example = "1990-01-15")
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Schema(description = "Hire date of the employee in 'yyyy-MM-dd' format (default is current date)", example = "2023-02-01")
    @PastOrPresent(message = "Hire date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate = new Date(System.currentTimeMillis());

    @Schema(description = "Type of the employee", example = "MANAGER")
    @NotNull(message = "Employee type is required")
    private EmployeeType type;

    @Schema(description = "Description or notes about the employee", example = "Highly skilled in Java development")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
