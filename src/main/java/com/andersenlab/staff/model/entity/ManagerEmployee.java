package com.andersenlab.staff.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("MANAGER")
@ToString
@Table(name = "manager_employee")
public class ManagerEmployee {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "manager_id", nullable = false)
    @ToString.Exclude
    private Employee manager;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    @ToString.Exclude
    private Employee employee;

    @NotNull
    @Column(name = "assigned_date", nullable = false)
    private Instant assignedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagerEmployee employee1)) return false;
        return Objects.equals(manager, employee1.manager)
                && Objects.equals(employee, employee1.employee)
                && Objects.equals(assignedDate, employee1.assignedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manager, employee, assignedDate);
    }
}