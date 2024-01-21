package com.project.projectManagementSystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "project_assignees")
public class ProjectAssignee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_assign_id")
	private Long id;
	@NotNull
	@Column(name = "project_id")
	private Long projectId;
	@NotNull
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "assign_date")
	private String assignDate;
	@Column(name = "is_employee_working")
	private Boolean isEmployeeWorking;
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	public ProjectAssignee() {
		super();
	}

	public ProjectAssignee(Long id, @NotNull Long projectId, @NotNull Long employeeId, String assignDate,
			Boolean isEmployeeWorking, Boolean isDeleted) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.employeeId = employeeId;
		this.assignDate = assignDate;
		this.isEmployeeWorking = isEmployeeWorking;
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}

	public Boolean getIsEmployeeWorking() {
		return isEmployeeWorking;
	}

	public void setIsEmployeeWorking(Boolean isEmployeeWorking) {
		this.isEmployeeWorking = isEmployeeWorking;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "ProjectAssignee [id=" + id + ", projectId=" + projectId + ", employeeId=" + employeeId + ", assignDate="
				+ assignDate + ", isEmployeeWorking=" + isEmployeeWorking + ", isDeleted=" + isDeleted + "]";
	}

}
