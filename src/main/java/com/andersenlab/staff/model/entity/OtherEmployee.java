package com.andersenlab.staff.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("OTHER")
@ToString
public class OtherEmployee extends Employee {

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private EmployeeDetails details;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OtherEmployee employee)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}