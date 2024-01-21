package com.project.projectManagementSystem.service.serviceImpl;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.projectManagementSystem.auth.services.LogInUserDetails;
import com.project.projectManagementSystem.dto.LoginEmployeeData;
import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.helper.JWTUtill;
import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.repo.EmployeeRepo;
import com.project.projectManagementSystem.response.GlobalResponse;
import com.project.projectManagementSystem.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private LogInUserDetails loginUserDetails;
	@Autowired
	private JWTUtill jwtUtill;

	@Override
	public GlobalResponse registerEmployee(Employee employee) {
		try {
			GlobalResponse createEmployee = this.employeeServiceImpl.createEmployee(employee);
			return createEmployee;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<?> employeeLogin(Employee employee) {
		try {
			if (employee.getDepartment().equalsIgnoreCase("USER")) {
				System.out.println("employee.getPassword().trim() "+employee.getPassword().trim());
				try {
					this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
							employee.getEmail().trim(), employee.getPassword().trim()));
				} catch (Exception e) {
					if (e.getMessage().equals("Bad credentials")) {
						throw new CustomException("Please check your password");
					} else {
						throw new CustomException(e.getMessage());
					}
				}
			} else if (employee.getDepartment().equalsIgnoreCase("HR")) {
				try {
					this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
							employee.getEmail().trim(), employee.getPassword().trim()));
				} catch (Exception e) {
					if (e.getMessage().equals("Bad credentials")) {
						throw new CustomException("Please check your password");
					} else {
						throw new CustomException(e.getMessage());
					}
				}
			} else {
				try {
					this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
							employee.getEmail().trim(), employee.getPassword().trim()));
				} catch (Exception e) {
					if (e.getMessage().equals("Bad credentials")) {
						throw new CustomException("Please check your password");
					} else {
						throw new CustomException(e.getMessage());
					}
				}
			}
			UserDetails vendor = this.loginUserDetails.loadUserByUsername(employee.getEmail().trim());
			String token = jwtUtill.generateToken(vendor);
			Optional<Employee> employeeByEmail = this.employeeRepo.findByEmail(vendor.getUsername());
			if (employee.getDepartment().equalsIgnoreCase("ADMIN")) {
				if (employeeByEmail.isPresent()) {
					return ResponseEntity.ok(new LoginEmployeeData(token, employeeByEmail.get().getId(),
							employeeByEmail.get().getEmail().trim(), employeeByEmail.get().getPassword().trim(),
							"Login successful",
							Stream.of("ADMIN").map(SimpleGrantedAuthority::new).collect(Collectors.toList()), "ADMIN",
							200));
				} else {
					throw new CustomException("Admin Not Found");
				}
			} else if (employee.getDepartment().equalsIgnoreCase("HR")) {
				if (employeeByEmail.isPresent()) {
					return ResponseEntity.ok(new LoginEmployeeData(token, employeeByEmail.get().getId(),
							employeeByEmail.get().getEmail().trim(), employeeByEmail.get().getPassword().trim(),
							"Login successful",
							Stream.of("HR").map(SimpleGrantedAuthority::new).collect(Collectors.toList()), "HR",
							200));
				} else {
					throw new CustomException("HR Not Found");
				}
			} else if (employee.getDepartment().equalsIgnoreCase("USER")) {
				if (employeeByEmail.isPresent()) {
					return ResponseEntity.ok(new LoginEmployeeData(token, employeeByEmail.get().getId(),
							employeeByEmail.get().getEmail().trim(), employeeByEmail.get().getPassword().trim(),
							"Login successful",
							Stream.of("USER").map(SimpleGrantedAuthority::new).collect(Collectors.toList()), "USER",
							200));
				} else {
					throw new CustomException("User Not Found");
				}
			} else {
				return null;	
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
