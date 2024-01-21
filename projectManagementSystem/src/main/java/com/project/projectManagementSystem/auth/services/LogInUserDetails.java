package com.project.projectManagementSystem.auth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.projectManagementSystem.dto.LoginEmployeeData;
import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.repo.EmployeeRepo;

@Service
public class LogInUserDetails implements UserDetailsService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Employee> employeeByEmail = this.employeeRepo.findByEmail(username);
		if (employeeByEmail.isPresent()) {
			employeeByEmail.orElseThrow(() -> new CustomException("Please Check The User Name!"));
			return employeeByEmail.map(LoginEmployeeData :: new).get();
		} else {
			throw new CustomException("Please Check The User Name!");
		}
	}

}
