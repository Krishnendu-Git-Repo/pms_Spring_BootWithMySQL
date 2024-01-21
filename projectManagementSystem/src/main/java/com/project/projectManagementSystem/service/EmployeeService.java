package com.project.projectManagementSystem.service;

import com.project.projectManagementSystem.dto.EmployeeDTO;
import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.response.GlobalResponse;

public interface EmployeeService {

	public GlobalResponse createEmployee(Employee employee);

	public GlobalResponse getEmployeeById(String token, Long id);

	public GlobalResponse updateEmployee(String token, EmployeeDTO employeeDTO);

	public GlobalResponse deleteEmployeeById(String token, Long id);

	public GlobalResponse findAllEmployee(String token, Boolean isDeleted);

}
