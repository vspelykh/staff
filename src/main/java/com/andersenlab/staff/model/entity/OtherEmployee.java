package com.andersenlab.staff.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue("OTHER")
@ToString
public class OtherEmployee extends Employee {

}