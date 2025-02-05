package com.andersenlab.staff.repository;

import com.andersenlab.staff.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @EntityGraph(attributePaths = "employeeDetails")
    Page<Employee> findAll(Specification<Employee> spec, Pageable pageable);

    Optional<Employee> findByIdAndActiveIsTrue(UUID employeeId);
}
