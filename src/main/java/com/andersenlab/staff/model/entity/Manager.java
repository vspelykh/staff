package com.andersenlab.staff.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("MANAGER")
@ToString
public class Manager extends Employee {
    @OneToMany
    @JoinTable(name = "manager_employee",
            joinColumns = @JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @ToString.Exclude
    private List<Employee> subordinates = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager manager)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(getId(), manager.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
