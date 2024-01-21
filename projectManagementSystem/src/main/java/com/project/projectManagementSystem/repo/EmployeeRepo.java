package com.project.projectManagementSystem.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.projectManagementSystem.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
	List<Employee> findByIsDeleted(Boolean isDeleted);
	Optional<Employee> findByEmail(String email);

}
