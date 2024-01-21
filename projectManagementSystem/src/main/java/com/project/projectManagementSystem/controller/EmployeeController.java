package com.project.projectManagementSystem.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.projectManagementSystem.dto.EmployeeDTO;
import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.response.GlobalResponse;
import com.project.projectManagementSystem.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	public static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	/* This API is internally called from Auth Controller when an employee register or add */
	@PostMapping("/addEmployee")
	public GlobalResponse createEmployee(@RequestBody @Valid Employee employee) {
		try {
			return this.employeeService.createEmployee(employee);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/* This API is responsible for getting all details of an employee by id */
	/* ADMIN, HR and User all can access this API */
	@GetMapping("/getEmployeeById")
	public GlobalResponse getEmployeeById(@RequestHeader("Authorization") String token, @RequestParam("id") Long id) {
		try {
			return this.employeeService.getEmployeeById(token, id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for updating an employee by id */
	/* ADMIN and User can access this API, HR can't access */
	@PutMapping("/updateEmployee")
	public GlobalResponse updateEmployee(@RequestHeader("Authorization") String token, @RequestBody @Valid EmployeeDTO employeeDTO) {
		try {
			return this.employeeService.updateEmployee(token, employeeDTO);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for deleting an employee by id */
	/* ADMIN and User can access this API, HR can't access */
	@PutMapping("/deleteEmployeeById")
	public GlobalResponse deleteEmployeeById(@RequestHeader("Authorization") String token, @RequestParam("id") Long id) {
		try {
			return this.employeeService.deleteEmployeeById(token, id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for getting all the employee details */
	/* ADMIN and HR can access this API, User can't access */
	@GetMapping("/getAllEmployee")
	public GlobalResponse findAllEmployee(@RequestHeader("Authorization") String token,
			@RequestParam(value = "isDeleted", defaultValue = "false") Boolean isDeleted) {
		try {
			return this.employeeService.findAllEmployee(token, isDeleted);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

}
