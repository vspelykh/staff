package com.andersenlab.staff.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request for updating an employee's information")
public class UpdateEmployeeRequest {

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

    @Schema(description = "Hire date of the employee in 'yyyy-MM-dd' format", example = "2023-02-01")
    @PastOrPresent(message = "Hire date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    @Schema(description = "Additional description or notes about the employee", example = "Skilled in leadership and project management.")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
}
