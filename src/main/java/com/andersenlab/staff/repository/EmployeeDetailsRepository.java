package com.andersenlab.staff.repository;

import com.andersenlab.staff.model.entity.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, UUID> {

    Optional<EmployeeDetails> findByEmployeeId(UUID employeeId);

    void deleteByEmployeeId(UUID employeeId);
}