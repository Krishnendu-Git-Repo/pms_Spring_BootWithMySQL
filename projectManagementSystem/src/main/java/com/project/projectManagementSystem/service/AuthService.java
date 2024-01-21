package com.project.projectManagementSystem.service;

import org.springframework.http.ResponseEntity;

import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.response.GlobalResponse;

public interface AuthService {

	GlobalResponse registerEmployee(Employee employee);

	ResponseEntity<?> employeeLogin(Employee employee);

}
