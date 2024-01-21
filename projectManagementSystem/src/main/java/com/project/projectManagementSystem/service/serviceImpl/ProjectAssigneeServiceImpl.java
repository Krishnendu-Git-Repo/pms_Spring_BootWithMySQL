package com.project.projectManagementSystem.service.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.projectManagementSystem.dto.ProjectAssignJoinModelDTO;
import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.mailConfig.EmailSender;
import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.model.Project;
import com.project.projectManagementSystem.model.ProjectAssignee;
import com.project.projectManagementSystem.repo.EmployeeRepo;
import com.project.projectManagementSystem.repo.ProjectAssigneeRepo;
import com.project.projectManagementSystem.repo.ProjectRepo;
import com.project.projectManagementSystem.response.GlobalResponse;
import com.project.projectManagementSystem.service.ProjectAssigneeService;
import com.project.projectManagementSystem.utility.CommonUtility;

@Service
public class ProjectAssigneeServiceImpl implements ProjectAssigneeService {

	@Autowired
	private ProjectAssigneeRepo projectAssigneeRepo;
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private CommonUtility commonUtility;
	@Autowired
	private EmailSender emailSender;

	public static final Logger LOGGER = LoggerFactory.getLogger(ProjectAssigneeServiceImpl.class);

	@Override
	public GlobalResponse assignProject(String token, @Valid ProjectAssignee projectAssignee) {
		try {
			Optional<ProjectAssignee> findByProjectIdAndEmployeeId = this.projectAssigneeRepo
					.findByProjectIdAndEmployeeId(projectAssignee.getProjectId(), projectAssignee.getEmployeeId());
			if (findByProjectIdAndEmployeeId.isPresent()) {
				return new GlobalResponse("400", "This Project Is Already Assigned To The Employee", null);
			} else {
				Optional<Employee> employeeById = this.employeeRepo.findById(projectAssignee.getEmployeeId());
				Boolean isDeletedEmp = employeeById.get().getIsDeleted();
				Optional<Project> projectById = this.projectRepo.findById(projectAssignee.getProjectId());
				Boolean isDeletedProject = projectById.get().getIsDeleted();
				if (isDeletedEmp.equals(Boolean.TRUE) && isDeletedProject.equals(Boolean.TRUE)) {
					return new GlobalResponse("400", "This Project And Employee Does Not Exist", null);
				} else if (isDeletedEmp.equals(Boolean.TRUE)) {
					return new GlobalResponse("400", "This Employee Does Not Exist", null);
				} else if (isDeletedProject.equals(Boolean.TRUE)) {
					return new GlobalResponse("400", "This Project Does Not Exist", null);
				} else {
					ProjectAssignee addAssignProject = this.commonUtility.addAssignProject(projectAssignee);
					ProjectAssignee savedAssignedProject = this.projectAssigneeRepo.save(addAssignProject);
					String employeeEmailId = employeeById.get().getEmail();
					String subject = "New Project is Assigned to You";
					String body = "You have been assigned to a new project and the project name is: "
							+ projectById.get().getProjectName();
					emailSender.sendMail(employeeEmailId, subject, body);
					return new GlobalResponse("200", "Project Is Assigned To The Employee", savedAssignedProject);
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse updateAssignProject(String token, Long projectId, Long employeeId) {
		try {
			Optional<ProjectAssignee> findByProjectIdAndEmployeeId = this.projectAssigneeRepo
					.findByProjectIdAndEmployeeId(projectId, employeeId);
			if (!findByProjectIdAndEmployeeId.isPresent()) {
				return new GlobalResponse("400", "This Project And Employee Does Not Exist", null);
			} else {
				if (findByProjectIdAndEmployeeId.get().getIsEmployeeWorking().equals(Boolean.FALSE)) {
					return new GlobalResponse("400", "This Employee Is Not Working In This Project", null);
				} else {
					ProjectAssignee getProjectAssignee = findByProjectIdAndEmployeeId.get();
					ProjectAssignee updateProjectAssignee = new ProjectAssignee();
					updateProjectAssignee.setId(getProjectAssignee.getId());
					updateProjectAssignee.setEmployeeId(getProjectAssignee.getEmployeeId());
					updateProjectAssignee.setProjectId(getProjectAssignee.getProjectId());
					updateProjectAssignee.setAssignDate(getProjectAssignee.getAssignDate());
					updateProjectAssignee.setIsDeleted(getProjectAssignee.getIsDeleted());
					updateProjectAssignee.setIsEmployeeWorking(Boolean.FALSE);
					ProjectAssignee updatedAssignProject = this.projectAssigneeRepo.save(updateProjectAssignee);
					return new GlobalResponse("200", "Assign Project is Updated Successfully", updatedAssignProject);
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse getAllAssignProject(String token, Boolean isDeleted) {
		try {
			// List<ProjectAssignJoinModelDTO> customJoinQuery =
			// this.projectAssigneeRepo.getJoinData(isDeleted);
			List<Object[]> findAllData = this.projectAssigneeRepo.findAllData(isDeleted);
			List<ProjectAssignJoinModelDTO> collectData = findAllData.stream().map(this.commonUtility::mapToDTO)
					.collect(Collectors.toList());
			if (collectData.size() > 0) {
				return new GlobalResponse("200", "Success", collectData);
			} else {
				return new GlobalResponse("400", "Data Not Found", new ArrayList<>());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse getAssigneProjectByEmployeeId(String token, Long employeeId, Boolean isDeleted) {
		try {
			List<Object[]> findAssigneProjectByEmployeeId = this.projectAssigneeRepo
					.findAssigneProjectByEmployeeId(employeeId, isDeleted);
			List<ProjectAssignJoinModelDTO> result = findAssigneProjectByEmployeeId.stream()
					.map(this.commonUtility::mapToDTO).collect(Collectors.toList());
			if (result.size() > 0) {
				return new GlobalResponse("200", "Success", result);
			} else {
				return new GlobalResponse("400", "Data Not Found", new ArrayList<>());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse getAssigneProjectByProjectId(String token, Long projectId, Boolean isDeleted) {
		try {
			List<Object[]> findAssigneProjectByProjectId = this.projectAssigneeRepo
					.findAssigneProjectByProjectId(projectId, isDeleted);
			List<ProjectAssignJoinModelDTO> result = findAssigneProjectByProjectId.stream()
					.map(this.commonUtility::mapToDTO).collect(Collectors.toList());
			if (result.size() > 0) {
				return new GlobalResponse("200", "Success", result);
			} else {
				return new GlobalResponse("400", "Data Not Found", new ArrayList<>());
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	@Override
	public GlobalResponse deleteAssignProjectByProjectIdAndEmployeeId(String token, Long projectId, Long employeeId) {
		try {
			Optional<ProjectAssignee> findByProjectIdAndEmployeeId = this.projectAssigneeRepo
					.findByProjectIdAndEmployeeId(projectId, employeeId);
			if (!findByProjectIdAndEmployeeId.isPresent()) {
				return new GlobalResponse("400", "Data Not Found", null);
			} else {
				ProjectAssignee projectAssignee = findByProjectIdAndEmployeeId.get();
				if (projectAssignee.getIsDeleted().equals(Boolean.TRUE)) {
					return new GlobalResponse("400", "The Assign Project is Already Deleted", null);
				} else {
					projectAssignee.setIsDeleted(Boolean.TRUE);
					ProjectAssignee updatedData = this.projectAssigneeRepo.save(projectAssignee);
					return new GlobalResponse("200", "Assign Project is Deleted Successfully",
							updatedData.getIsDeleted());
				}
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	@Override
	public GlobalResponse getAllAssignProjectList(String token, Integer page, Integer limit, String sort, String sortName,
			String keyword, Optional<String> sortBy, String sortDateType, String startDate, String endDate,
			Boolean isDeleted, Boolean isEmployeeWorking, Long employeeId, Long projectId) {
		try {
			Pageable pageable = null;
			Page<ProjectAssignJoinModelDTO> paginationData = null;
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
				if (employeeId != null && projectId == null) {
					Page<Object[]> dataByKeywordAndEmployeeIdAndIsDeletedAndIsEmployeeWorking = this.projectAssigneeRepo
							.findByKeywordAndEmployeeIdAndIsDeletedAndIsEmployeeWorking(keyword, employeeId, isDeleted,
									isEmployeeWorking, pageable);
					paginationData = this.commonUtility
							.getPaginationData(dataByKeywordAndEmployeeIdAndIsDeletedAndIsEmployeeWorking, pageable);
				} else if (employeeId == null && projectId != null) {
					Page<Object[]> dataByKeywordAndProjectIdAndIsDeletedAndIsEmployeeWorking = this.projectAssigneeRepo
							.findByKeywordAndProjectIdAndIsDeletedAndIsEmployeeWorking(keyword, projectId, isDeleted,
									isEmployeeWorking, pageable);
					paginationData = this.commonUtility
							.getPaginationData(dataByKeywordAndProjectIdAndIsDeletedAndIsEmployeeWorking, pageable);
				} else if (employeeId == null && projectId == null) {
//					List<ProjectAssignJoinModelDTO> collectJoinQueryDTOData = new ArrayList<ProjectAssignJoinModelDTO>();
//					if (dataByKeywordAndIsDeleted.getTotalElements() < pageable.getOffset()) {					
//				}
					Page<Object[]> dataByKeywordAndIsDeleted = this.projectAssigneeRepo
							.findByKeywordAndIsDeletedAndIsEmployeeWorking(keyword, isDeleted, isEmployeeWorking,
									pageable);
					paginationData = this.commonUtility.getPaginationData(dataByKeywordAndIsDeleted, pageable);
				} else {
					paginationData = new PageImpl<>(Collections.emptyList());
				}
			} else {
				if (employeeId != null && projectId == null) {
					Page<Object[]> dataByEmployeeIdAndIsDeletedAndIsEmployeeWorking = this.projectAssigneeRepo
							.findByEmployeeIdAndIsDeletedAndIsEmployeeWorking(employeeId, isDeleted, isEmployeeWorking,
									pageable);
					paginationData = this.commonUtility
							.getPaginationData(dataByEmployeeIdAndIsDeletedAndIsEmployeeWorking, pageable);
				} else if (employeeId == null && projectId != null) {
					Page<Object[]> dataByProjectIdAndIsDeletedAndIsEmployeeWorking = this.projectAssigneeRepo
							.findByProjectIdAndIsDeletedAndIsEmployeeWorking(projectId, isDeleted, isEmployeeWorking, pageable);
					paginationData = this.commonUtility
							.getPaginationData(dataByProjectIdAndIsDeletedAndIsEmployeeWorking, pageable);
				} else if (employeeId == null && projectId == null) {
					Page<Object[]> isDeletedAndIsEmployeeWorking = this.projectAssigneeRepo
							.findByisDeletedAndIsEmployeeWorking(isDeleted, isEmployeeWorking, pageable);
					paginationData = this.commonUtility.getPaginationData(isDeletedAndIsEmployeeWorking, pageable);
				} else {
					paginationData = new PageImpl<>(Collections.emptyList());
				}
			}
			List<ProjectAssignJoinModelDTO> collectStartDateEndDateData = paginationData.getContent().stream()
					.filter(Objects :: nonNull).filter(e -> {
						if (!startDate.equals("") && !endDate.equals("")) {
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							try {
								Date startingDate = sdf.parse(startDate);
								Date endingDate = sdf.parse(endDate);
								Date createDate = sdf.parse(e.getAssignDate());

								return ((createDate.after(startingDate) || createDate.equals(startingDate))
										&& (createDate.before(endingDate) || createDate.equals(endingDate))) ? true
												: false;
							} catch (Exception e1) {
								throw new CustomException(e1.getMessage());
							}
						} else {
							return true;
						}
					}).collect(Collectors.toList());
			paginationData = this.commonUtility.getPaginationDataOfList(collectStartDateEndDateData, pageable);

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
