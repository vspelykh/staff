package com.andersenlab.staff.repository;

import com.andersenlab.staff.model.entity.ManagerEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManagerEmployeeRepository extends JpaRepository<ManagerEmployee, UUID> {

    void removeAllByManagerId(UUID managerId);
}