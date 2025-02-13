package com.andersenlab.staff.repository;

import com.andersenlab.staff.model.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {

    Optional<Manager> findByIdAndActiveIsTrue(UUID id);
}
