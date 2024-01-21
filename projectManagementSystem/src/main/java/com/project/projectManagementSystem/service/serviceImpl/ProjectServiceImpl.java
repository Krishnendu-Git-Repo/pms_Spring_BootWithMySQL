package com.project.projectManagementSystem.service.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.projectManagementSystem.dto.ProjectDTO;
import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.model.Project;
import com.project.projectManagementSystem.repo.ProjectRepo;
import com.project.projectManagementSystem.response.GlobalResponse;
import com.project.projectManagementSystem.service.ProjectService;
import com.project.projectManagementSystem.utility.CommonUtility;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private CommonUtility commonUtility;

	public static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Override
	public GlobalResponse createProject(String token, Project project) {
		try {
			Optional<Project> findByProjectName = this.projectRepo.findByProjectName(project.getProjectName());
			if (findByProjectName.isPresent()) {
				return new GlobalResponse("400", "This Project Already Present in Database", null);
			} else {
				Project addProject = commonUtility.addProject(project);
				Project savedProject = this.projectRepo.save(addProject);
				Long projectId = savedProject.getId();
				return new GlobalResponse("200", "Project Added Successfully", projectId);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse getProjectById(String token, Long id) {
		try {
			Optional<Project> projectById = this.projectRepo.findById(id);
			if (projectById.isPresent()) {
				Project project = projectById.get();
				return new GlobalResponse("200", "Success", project);
			} else {
				return new GlobalResponse("400", "Project Does Not Exist", null);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse updateProject(String token, ProjectDTO projectDTO) {
		try {
			Optional<Project> projectById = this.projectRepo.findById(projectDTO.getId());
			if (projectById.isPresent()) {
				Project project = projectById.get();
				Project updateProject = new Project();
				updateProject.setId(project.getId());
				updateProject.setProjectName(project.getProjectName());
				updateProject.setProjectDescription(
						projectDTO.getProjectDescription() == null ? project.getProjectDescription()
								: projectDTO.getProjectDescription());
				updateProject.setProjectStartDate(project.getProjectStartDate());
				updateProject.setProjectEndDate(projectDTO.getProjectEndDate() == null ? project.getProjectEndDate()
						: projectDTO.getProjectEndDate());
				updateProject.setIsDeleted(
						projectDTO.getIsDeleted() == null ? project.getIsDeleted() : projectDTO.getIsDeleted());
				updateProject.setCreatedOn(project.getCreatedOn());
				Project saveProject = this.projectRepo.save(updateProject);
				return new GlobalResponse("200", "Project Updated Successfully ", saveProject);
			} else {
				return new GlobalResponse("400", "Project Does Not Exist", null);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse deleteProjectById(String token, Long id) {
		try {
			Optional<Project> projectById = this.projectRepo.findById(id);
			Boolean isDelete = Boolean.TRUE;
			if (projectById.isPresent()) {
				if (projectById.get().getIsDeleted().equals(Boolean.FALSE)) {
					Project project = projectById.get();
					project.setIsDeleted(isDelete);
					Project savedProject = this.projectRepo.save(project);
					return new GlobalResponse("200", "Project Deleted Successfully ", savedProject.getIsDeleted());
				} else {
					return new GlobalResponse("400", "Project Has Been Alreaday Deleted", null);
				}
			} else {
				return new GlobalResponse("400", "Project Does Not Exist", null);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse getAllProject(String token, Boolean isDeleted) {
		try {
			List<Project> projectIsDeleted = this.projectRepo.findByIsDeleted(isDeleted);
			if (projectIsDeleted.size() > 0) {
				return new GlobalResponse("200", "Success", projectIsDeleted);
			} else {
				return new GlobalResponse("400", "Projects Not Found !", null);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse getProjectList(String token, Integer page, Integer limit, String sort, String sortName, String keyword,
			Optional<String> sortBy, String sortDateType, String startDate, String endDate, Boolean isDeleted) {
		try {
			Pageable pageable = null;
			Page<Project> paginationData = null;

			if (!sortDateType.equals("")) {
				if (sortDateType.equalsIgnoreCase("new")) {
					pageable = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
				} else if (sortDateType.equalsIgnoreCase("old")) {
					pageable = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
				} else {
					pageable = null;
				}
			} else {
				if (sort.equalsIgnoreCase("ASC")) {
					pageable = PageRequest.of(page, limit, Sort.Direction.ASC, sortBy.orElse(sortName));
				} else {
					pageable = PageRequest.of(page, limit, Sort.Direction.DESC, sortBy.orElse(sortName));
				}
			}

			if (!keyword.equals("")) {
				paginationData = this.projectRepo.findBykeywordAndIsDeleted(keyword, isDeleted, pageable);
			} else {
				paginationData = this.projectRepo.findByisDelete(isDeleted, pageable);
			}

			List<Long> projectId = paginationData.getContent().stream().filter(e -> {
				if (!startDate.equals("") && !endDate.equals("")) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					try {
						Date startingDate = sdf.parse(startDate);
						Date endingDate = sdf.parse(endDate);
						Date createDate = sdf.parse(e.getCreatedOn());

						return ((createDate.after(startingDate) || createDate.equals(startingDate))
								&& (createDate.before(endingDate) || createDate.equals(endingDate))) ? true : false;
					} catch (Exception e1) {
						throw new CustomException(e1.getMessage());
					}
				} else {
					return true;
				}
			}).map(e -> e.getId()).collect(Collectors.toList());
			paginationData = this.projectRepo.findByIdInAndIsDeleted(projectId, isDeleted, pageable);

			Map<String, Object> response = new HashMap<String, Object>();
			response.put("data", paginationData.getContent());
			response.put("currentPage", paginationData.getNumber());
			response.put("totalElements", paginationData.getTotalElements());
			response.put("totalPage", paginationData.getTotalPages());
			response.put("perPage", paginationData.getSize());
			response.put("perPageElement", paginationData.getNumberOfElements());
			return new GlobalResponse("200", "Success", response);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
