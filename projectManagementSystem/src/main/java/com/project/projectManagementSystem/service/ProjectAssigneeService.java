package com.project.projectManagementSystem.service;

import java.util.Optional;

import javax.validation.Valid;

import com.project.projectManagementSystem.model.ProjectAssignee;
import com.project.projectManagementSystem.response.GlobalResponse;

public interface ProjectAssigneeService {

	public GlobalResponse assignProject(String token, @Valid ProjectAssignee projectAssignee);

	public GlobalResponse updateAssignProject(String token, Long projectId, Long employeeId);

	public GlobalResponse getAllAssignProject(String token, Boolean isDeleted);

	public GlobalResponse getAssigneProjectByEmployeeId(String token, Long employeeId, Boolean isDeleted);

	public GlobalResponse getAssigneProjectByProjectId(String token, Long projectId, Boolean isDeleted);

	public GlobalResponse deleteAssignProjectByProjectIdAndEmployeeId(String token, Long projectId, Long employeeId);

	public GlobalResponse getAllAssignProjectList(String token, Integer page, Integer limit, String sort, String sortName,
			String keyword, Optional<String> sortBy, String sortDateType, String startDate, String endDate,
			Boolean isDeleted, Boolean isEmployeeWorking, Long employeeId, Long projectId);

}
