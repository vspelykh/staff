package com.andersenlab.staff.repository;

import com.andersenlab.staff.model.entity.ManagerEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ManagerEmployeeRepository extends JpaRepository<ManagerEmployee, UUID> {

    List<ManagerEmployee> findByManagerIdOrderByEmployeeLastName(UUID managerId);

    void removeAllByManagerId(UUID managerId);

    int removeByManagerIdAndEmployeeId(UUID managerId, UUID employeeId);
}