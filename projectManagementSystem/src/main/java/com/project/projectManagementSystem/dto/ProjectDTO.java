package com.project.projectManagementSystem.dto;

public class ProjectDTO {
	private Long id;
	private String projectDescription;
	private String projectEndDate;
	private Boolean isDeleted;
	
	public ProjectDTO() {
		super();
	}
	
	public ProjectDTO(Long id, String projectDescription, String projectEndDate, Boolean isDeleted) {
		super();
		this.id = id;
		this.projectDescription = projectDescription;
		this.projectEndDate = projectEndDate;
		this.isDeleted = isDeleted;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	public String getProjectEndDate() {
		return projectEndDate;
	}
	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@Override
	public String toString() {
		return "ProjectDTO [id=" + id + ", projectDescription=" + projectDescription + ", projectEndDate="
				+ projectEndDate + ", isDeleted=" + isDeleted + "]";
	}
	
	
}
