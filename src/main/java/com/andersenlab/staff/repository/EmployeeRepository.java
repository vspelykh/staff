package com.andersenlab.staff.repository;

import com.andersenlab.staff.model.entity.Employee;
import com.andersenlab.staff.model.entity.EmployeeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Page<Employee> findAll(Specification<Employee> spec, Pageable pageable);

    List<Employee> findAllByIdIn(List<UUID> ids);

    Optional<Employee> findByIdAndActiveIsTrue(UUID employeeId);

    @Modifying
    @Query("UPDATE Employee e SET e.type = :newType WHERE e.id = :id")
    void updateEmployeeType(@Param("id") UUID id, @Param("newType") EmployeeType newType);
}
