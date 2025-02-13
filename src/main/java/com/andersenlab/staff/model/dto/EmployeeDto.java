package com.andersenlab.staff.model.dto;

import com.andersenlab.staff.model.entity.EmployeeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(description = "Data transfer object for Employee")
public class EmployeeDto extends RepresentationModel<EmployeeDto> {

    @Schema(description = "Unique identifier of the employee", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "First name of the employee", example = "John")
    private String firstName;

    @Schema(description = "Middle name of the employee (optional)", example = "Michael")
    private String middleName;

    @Schema(description = "Last name of the employee", example = "Doe")
    private String lastName;

    @Schema(description = "Birth date of the employee in 'yyyy-MM-dd' format", example = "1990-01-15")
    private Date birthDate;

    @Schema(description = "Hire date of the employee in 'yyyy-MM-dd' format", example = "2023-02-01")
    private Date hireDate;

    @Schema(description = "Type of the employee", example = "MANAGER")
    private EmployeeType type;

    @Schema(description = "Additional description or notes about the employee", example = "Highly skilled in Java development")
    private String description;

    @Schema(description = "Employee's active status", example = "true")
    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDto that)) return false;
        if (!super.equals(o)) return false;
        return active == that.active && Objects.equals(id, that.id)
                && Objects.equals(firstName, that.firstName) && Objects.equals(middleName, that.middleName)
                && Objects.equals(lastName, that.lastName) && Objects.equals(birthDate, that.birthDate)
                && Objects.equals(hireDate, that.hireDate) && type == that.type
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, firstName, middleName, lastName, birthDate, hireDate, type, description, active);
    }
}
