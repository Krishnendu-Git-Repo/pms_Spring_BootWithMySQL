package com.project.projectManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.response.GlobalResponse;
import com.project.projectManagementSystem.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/addEmployee")
	public GlobalResponse registerEmployee(@RequestBody Employee employee) {
		try {
			return this.authService.registerEmployee(employee);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> employeeLogin(@RequestBody Employee employee) {
		try {
			return this.authService.employeeLogin(employee);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
