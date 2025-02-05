package com.andersenlab.staff.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("WORKER")
public class Employee extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "hire_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hireDate;

    @Column(name="type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return Objects.equals(firstName, employee.firstName) && Objects.equals(middleName, employee.middleName)
                && Objects.equals(lastName, employee.lastName) && Objects.equals(birthDate, employee.birthDate)
                && Objects.equals(hireDate, employee.hireDate) && type == employee.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, birthDate, hireDate, type);
    }
}