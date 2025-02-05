package com.andersenlab.staff.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
}
