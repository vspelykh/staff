package com.andersenlab.staff.model.dto;

import com.andersenlab.staff.model.entity.EmployeeType;
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
public class EmployeeDto extends RepresentationModel<EmployeeDto> {

    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDate;
    private Date hireDate;
    private EmployeeType type;
    private String description;
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
