package com.project.projectManagementSystem.service;

import java.util.Optional;

import com.project.projectManagementSystem.dto.ProjectDTO;
import com.project.projectManagementSystem.model.Project;
import com.project.projectManagementSystem.response.GlobalResponse;

public interface ProjectService {

	public GlobalResponse createProject(String token, Project project);

	public GlobalResponse getProjectById(String token, Long id);

	public GlobalResponse updateProject(String token, ProjectDTO projectDTO);

	public GlobalResponse deleteProjectById(String token, Long id);

	public GlobalResponse getAllProject(String token, Boolean isDeleted);

	public GlobalResponse getProjectList(String token, Integer page, Integer limit, String sort, String sortName, String keyword,
			Optional<String> sortBy, String sortDateType, String startDate, String endDate, Boolean isDeleted);

}
