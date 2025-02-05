package com.andersenlab.staff.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee_details")
@ToString
public class EmployeeDetails extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(columnDefinition = "TEXT")
    private String description;
}