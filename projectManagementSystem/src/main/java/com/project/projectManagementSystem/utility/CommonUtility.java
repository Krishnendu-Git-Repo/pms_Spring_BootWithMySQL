package com.project.projectManagementSystem.utility;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.projectManagementSystem.dto.ProjectAssignJoinModelDTO;
import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.model.Project;
import com.project.projectManagementSystem.model.ProjectAssignee;

@Component
public class CommonUtility {

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public Employee addEmployee(Employee employee) {
		try {
			Employee returnEmployee = new Employee();
			returnEmployee.setFirstName(employee.getFirstName());
			returnEmployee.setLastName(employee.getLastName());
			returnEmployee.setEmail(employee.getEmail());
			returnEmployee.setDepartment(employee.getDepartment());
			returnEmployee.setDateOfBirth(employee.getDateOfBirth());
			returnEmployee.setIsDeleted(Boolean.FALSE);
			returnEmployee.setPassword(employee.getPassword());
			
			return returnEmployee;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Project addProject(Project project) {
		try {
			String format = simpleDateFormat.format(new Date());
			Project returnProject = new Project();
			returnProject.setProjectName(project.getProjectName());
			returnProject.setProjectDescription(project.getProjectDescription());
			returnProject.setProjectStartDate(format);
			returnProject.setProjectEndDate(project.getProjectEndDate() == null ? "" : project.getProjectEndDate());
			returnProject.setIsDeleted(Boolean.FALSE);
			returnProject.setCreatedOn(format);
			return returnProject;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProjectAssignee addAssignProject(@Valid ProjectAssignee projectAssignee) {
		try {
			String format = simpleDateFormat.format(new Date());
			ProjectAssignee returnProjectAssignee = new ProjectAssignee();
			returnProjectAssignee.setEmployeeId(projectAssignee.getEmployeeId());
			returnProjectAssignee.setProjectId(projectAssignee.getProjectId());
			returnProjectAssignee.setIsEmployeeWorking(Boolean.TRUE);
			returnProjectAssignee.setIsDeleted(Boolean.FALSE);
			returnProjectAssignee.setAssignDate(format);
			return returnProjectAssignee;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public ProjectAssignJoinModelDTO mapToDTO(Object[] result) {
		try {
			ProjectAssignJoinModelDTO dto = new ProjectAssignJoinModelDTO();
			dto.setProjectId((BigInteger) result[0]);
			dto.setEmployeeId((BigInteger) result[1]);
			dto.setAssignDate((String) result[2]);
			dto.setIsEmployeeWorking((Boolean) result[3]);
			dto.setFirstName((String) result[4]);
			dto.setLastName((String) result[5]);
			dto.setEmail((String) result[6]);
			dto.setDepartment((String) result[7]);
			dto.setProjectName((String) result[8]);
			dto.setProjectDescription((String) result[9]);
			dto.setProjectStartDate((String) result[10]);
			dto.setProjectEndDate((String) result[11]);
			return dto;
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Page<ProjectAssignJoinModelDTO> getPaginationData(Page<Object[]> data, Pageable pageable) {
		try {
			List<ProjectAssignJoinModelDTO> collectData = data.getContent().stream().filter(Objects::nonNull)
					.map(this :: mapToDTO).collect(Collectors.toList());
			return this.getPaginationDataOfList(collectData, pageable);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public Page<ProjectAssignJoinModelDTO> getPaginationDataOfList(List<ProjectAssignJoinModelDTO> data,
			Pageable pageable) {
		try {
			int startOfPage = pageable.getPageNumber() * pageable.getPageSize();
			int endOfPage = Math.min(startOfPage + pageable.getPageSize(), data.size());
			List<ProjectAssignJoinModelDTO> subList = startOfPage >= endOfPage ? new ArrayList<>()
					: data.subList(startOfPage, endOfPage);
			return new PageImpl<ProjectAssignJoinModelDTO>(subList, pageable, data.size());
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
