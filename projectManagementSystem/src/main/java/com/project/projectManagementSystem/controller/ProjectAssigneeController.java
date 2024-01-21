package com.project.projectManagementSystem.controller;

import java.util.Optional;

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

import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.model.ProjectAssignee;
import com.project.projectManagementSystem.response.GlobalResponse;
import com.project.projectManagementSystem.service.ProjectAssigneeService;

@RestController
@RequestMapping("/projectAssign")
public class ProjectAssigneeController {

	@Autowired
	private ProjectAssigneeService projectAssigneeService;

	public static final Logger LOGGER = LoggerFactory.getLogger(ProjectAssigneeController.class);

	/* This API is responsible for assigning a Project to an employee */
	/* Only ADMIN can access this API, HR and User can't access */
	@PostMapping("/addAssignProject")
	public GlobalResponse assignProject(@RequestHeader("Authorization") String token,
			@RequestBody @Valid ProjectAssignee projectAssignee) {
		try {
			return this.projectAssigneeService.assignProject(token, projectAssignee);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	 /* This API is responsible for updating a Project means only update the employee
	 * is still working or not in this project */
	/* Only ADMIN can access this API, HR and User can't access */
	@PutMapping("/updateAssignProject")
	public GlobalResponse updateAssignProject(@RequestHeader("Authorization") String token,
			@RequestParam("projectId") Long projectId, @RequestParam("employeeId") Long employeeId) {
		try {
			return this.projectAssigneeService.updateAssignProject(token, projectId, employeeId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for getting all assign Project for all employees */
	/* Only ADMIN and HR can access this API, User can't access */
	@GetMapping("/getAllAssignProjects")
	public GlobalResponse getAllAssignProject(@RequestHeader("Authorization") String token,
			@RequestParam(value = "isDeleted", defaultValue = "false") Boolean isDeleted) {
		try {
			return this.projectAssigneeService.getAllAssignProject(token, isDeleted);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for getting all assign Project for a particular employee by employeeId */
	/* ADMIN, User and HR all can access this API */
	@GetMapping("/getAllAssignProjectsByEmployeeId")
	public GlobalResponse getAssigneProjectByEmployeeId(@RequestHeader("Authorization") String token,
			@RequestParam("employeeId") Long employeeId,
			@RequestParam(value = "isDeleted", defaultValue = "false") Boolean isDeleted) {
		try {
			return this.projectAssigneeService.getAssigneProjectByEmployeeId(token, employeeId, isDeleted);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for getting all assign Project for a particular employee by projectId */
	/* ADMIN, User and HR all can access this API */
	@GetMapping("/getAllAssignProjectsByProjectId")
	public GlobalResponse getAssigneProjectByProjectId(@RequestHeader("Authorization") String token,
			@RequestParam("projectId") Long projectId,
			@RequestParam(value = "isDeleted", defaultValue = "false") Boolean isDeleted) {
		try {
			return this.projectAssigneeService.getAssigneProjectByProjectId(token, projectId, isDeleted);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for deleting a Project from an employee */
	/* Only ADMIN can access this API, HR and User can't access */
	@PutMapping("/deleteAssignProjectByProjectIdAndEmployeeId")
	public GlobalResponse deleteAssignProjectByProjectIdAndEmployeeId(@RequestHeader("Authorization") String token,
			@RequestParam("projectId") Long projectId, @RequestParam("employeeId") Long employeeId) {
		try {
			return this.projectAssigneeService.deleteAssignProjectByProjectIdAndEmployeeId(token, projectId,
					employeeId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for getting all assign Project for all employees */
	/* ADMIN, User and HR all can access this API */
	@GetMapping("/allAssignProjects/list")
	public GlobalResponse getAllAssignProjectList(@RequestHeader("Authorization") String token,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit,
			@RequestParam(value = "sort", defaultValue = "DESC") String sort,
			@RequestParam(value = "sortName", defaultValue = "project_assign_id") String sortName,
			@RequestParam(value = "keyword", defaultValue = "") String keyword, @RequestParam Optional<String> sortBy,
			@RequestParam(value = "sortDateType", defaultValue = "") String sortDateType,
			@RequestParam(value = "startDate", defaultValue = "") String startDate,
			@RequestParam(value = "endDate", defaultValue = "") String endDate,
			@RequestParam(value = "isDeleted", defaultValue = "false") Boolean isDeleted,
			@RequestParam(value = "isEmployeeWorking", defaultValue = "true") Boolean isEmployeeWorking,
			@RequestParam(value = "employeeId", defaultValue = "") Long employeeId,
			@RequestParam(value = "projectId", defaultValue = "") Long projectId) {
		try {
			return this.projectAssigneeService.getAllAssignProjectList(token, page, limit, sort, sortName, keyword,
					sortBy, sortDateType, startDate, endDate, isDeleted, isEmployeeWorking, employeeId, projectId);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
