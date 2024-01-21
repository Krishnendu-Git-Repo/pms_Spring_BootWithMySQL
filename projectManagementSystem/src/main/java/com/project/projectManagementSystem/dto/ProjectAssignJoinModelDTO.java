package com.project.projectManagementSystem.dto;

import java.math.BigInteger;

public class ProjectAssignJoinModelDTO {
	private BigInteger projectId;
	private BigInteger employeeId;
	private String assignDate;
	private Boolean isEmployeeWorking;
	private String firstName;
	private String lastName;
	private String email;
	private String department;
	private String projectName;
	private String projectDescription;
	private String projectStartDate;
	private String projectEndDate;
	
	public ProjectAssignJoinModelDTO() {
		super();
	}
	
	public ProjectAssignJoinModelDTO(BigInteger projectId, BigInteger employeeId, String assignDate, Boolean isEmployeeWorking,
			String firstName, String lastName, String email, String department, String projectName,
			String projectDescription, String projectStartDate, String projectEndDate) {
		super();
		this.projectId = projectId;
		this.employeeId = employeeId;
		this.assignDate = assignDate;
		this.isEmployeeWorking = isEmployeeWorking;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.department = department;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.projectStartDate = projectStartDate;
		this.projectEndDate = projectEndDate;
	}
	
	public BigInteger getProjectId() {
		return projectId;
	}
	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}
	public BigInteger getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigInteger employeeId) {
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public String getProjectStartDate() {
		return projectStartDate;
	}
	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}
	public String getProjectEndDate() {
		return projectEndDate;
	}
	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	
	@Override
	public String toString() {
		return "ProjectAssignJoinModelDTO [projectId=" + projectId + ", employeeId=" + employeeId + ", assignDate="
				+ assignDate + ", isEmployeeWorking=" + isEmployeeWorking + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", department=" + department + ", projectName=" + projectName
				+ ", projectDescription=" + projectDescription + ", projectStartDate=" + projectStartDate
				+ ", projectEndDate=" + projectEndDate + "]";
	}
	
	
}
