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

import com.project.projectManagementSystem.dto.ProjectDTO;
import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.model.Project;
import com.project.projectManagementSystem.response.GlobalResponse;
import com.project.projectManagementSystem.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	/* This API is responsible for creating a Project */
	/* Only ADMIN can access this API, HR and User can't access */
	@PostMapping("/addProject")
	public GlobalResponse createProject(@RequestHeader("Authorization") String token, 
			@RequestBody @Valid Project project) {
		try {
			return this.projectService.createProject(token, project);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for getting a Project by id */
	/* ADMIN, HR and USER all can access this API */
	@GetMapping("/getProjectById")
	public GlobalResponse getProjectById(@RequestHeader("Authorization") String token, @RequestParam("id") Long id) {
		try {
			return this.projectService.getProjectById(token, id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for updating a Project by id */
	/* Only ADMIN can access this API, HR and User can't access */
	@PutMapping("/updateProject")
	public GlobalResponse updateProject(@RequestHeader("Authorization") String token,
			@RequestBody @Valid ProjectDTO projectDTO) {
		try {
			return this.projectService.updateProject(token, projectDTO);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for deleting a Project by id */
	/* Only ADMIN can access this API, HR and User can't access */
	@PutMapping("/deleteProjectById")
	public GlobalResponse deleteProjectById(@RequestHeader("Authorization") String token, @RequestParam("id") Long id) {
		try {
			return this.projectService.deleteProjectById(token, id);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for getting all Projects */
	/* Only ADMIN and HR can access this API, User can't access */
	@GetMapping("/getAllProject")
	public GlobalResponse getAllProject(@RequestHeader("Authorization") String token,
			@RequestParam(value = "isDeleted", defaultValue = "false") Boolean isDeleted) {
		try {
			return this.projectService.getAllProject(token, isDeleted);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/* This API is responsible for getting all Projects with pagination, sorting and searching */
	/* Only ADMIN and HR can access this API, User can't access */
	@GetMapping("/projectList")
	public GlobalResponse getProjectList(@RequestHeader("Authorization") String token,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "10") Integer limit,
			@RequestParam(value = "sort", defaultValue = "DESC") String sort,
			@RequestParam(value = "sortName", defaultValue = "project_id") String sortName,
			@RequestParam(value = "keyword", defaultValue = "") String keyword, @RequestParam Optional<String> sortBy,
			@RequestParam(value = "sortDateType", defaultValue = "") String sortDateType,
			@RequestParam(value = "startDate", defaultValue = "") String startDate,
			@RequestParam(value = "endDate", defaultValue = "") String endDate,
			@RequestParam(value = "isDeleted", defaultValue = "false") Boolean isDeleted) {
		try {
			return this.projectService.getProjectList(token, page, limit, sort, sortName, keyword, sortBy, sortDateType,
					startDate, endDate, isDeleted);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
