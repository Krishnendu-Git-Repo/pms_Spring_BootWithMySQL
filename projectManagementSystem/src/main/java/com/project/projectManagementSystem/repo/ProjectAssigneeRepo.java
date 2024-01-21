package com.project.projectManagementSystem.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.projectManagementSystem.model.ProjectAssignee;

@Repository
public interface ProjectAssigneeRepo extends JpaRepository<ProjectAssignee, Long> {

	Optional<ProjectAssignee> findByProjectIdAndEmployeeId(Long projectId, Long employeeId);

//	@Query("SELECT new com.project.projectManagementSystem.dto.ProjectAssignJoinModelDTO(" +
//		    "pa.projectId, pa.employeeId, pa.assignDate, pa.isEmployeeWorking, " +
//		    "e.firstName, e.lastName, e.email, e.department, " +
//		    "p.projectName, p.projectDescription, p.projectStartDate, p.projectEndDate) " +
//		    "FROM ProjectAssignee pa " +
//		    "INNER JOIN Employee e ON pa.employeeId = e.id " +
//		    "INNER JOIN Project p ON pa.projectId = p.id where pa.isDeleted =:isDeleted")
//	    List<ProjectAssignJoinModelDTO> getJoinData(@Param(value = "isDeleted") Boolean isDeleted);

	@Query(value = "SELECT pa.project_id, pa.employee_id, pa.assign_date, pa.is_employee_working, "
			+ "e.first_name, e.last_name, e.email, e.department, "
			+ "p.project_name, p.project_description, p.project_start_date, p.project_end_date "
			+ "FROM project_assignees pa " + "INNER JOIN employees e ON pa.employee_id = e.employee_id "
			+ "INNER JOIN projects p ON pa.project_id = p.project_id where pa.is_deleted = :isDeleted", nativeQuery = true)
	List<Object[]> findAllData(@Param(value = "isDeleted") Boolean isDeleted);

	@Query(value = "SELECT pa.project_id, pa.employee_id, pa.assign_date, pa.is_employee_working, "
			+ "e.first_name, e.last_name, e.email, e.department, "
			+ "p.project_name, p.project_description, p.project_start_date, p.project_end_date "
			+ "FROM project_assignees pa " + "INNER JOIN employees e ON pa.employee_id = e.employee_id "
			+ "INNER JOIN projects p ON pa.project_id = p.project_id where pa.is_deleted = :isDeleted and pa.employee_id = :employeeId", nativeQuery = true)
	List<Object[]> findAssigneProjectByEmployeeId(@Param(value = "employeeId") Long employeeId,
			@Param(value = "isDeleted") Boolean isDeleted);

	@Query(value = "SELECT pa.project_id, pa.employee_id, pa.assign_date, pa.is_employee_working, "
			+ "e.first_name, e.last_name, e.email, e.department, "
			+ "p.project_name, p.project_description, p.project_start_date, p.project_end_date "
			+ "FROM project_assignees pa " + "INNER JOIN employees e ON pa.employee_id = e.employee_id "
			+ "INNER JOIN projects p ON pa.project_id = p.project_id where pa.is_deleted = :isDeleted and pa.project_id = :projectId", nativeQuery = true)
	List<Object[]> findAssigneProjectByProjectId(@Param(value = "projectId") Long projectId,
			@Param(value = "isDeleted") Boolean isDeleted);

	/// queries for pagination ///
	@Query(value = "SELECT pa.project_id, pa.employee_id, pa.assign_date, pa.is_employee_working, "
			+ "e.first_name, e.last_name, e.email, e.department, "
			+ "p.project_name, p.project_description, p.project_start_date, p.project_end_date "
			+ "FROM project_assignees pa " + "INNER JOIN employees e ON pa.employee_id = e.employee_id "
			+ "INNER JOIN projects p ON pa.project_id = p.project_id where pa.is_deleted = :isDeleted and pa.is_employee_working = :isEmployeeWorking", nativeQuery = true)
	Page<Object[]> findByisDeletedAndIsEmployeeWorking(@Param(value = "isDeleted") Boolean isDeleted,
			@Param(value = "isEmployeeWorking") Boolean isEmployeeWorking, Pageable pageable);

	@Query(value = "SELECT pa.project_id, pa.employee_id, pa.assign_date, pa.is_employee_working, "
			+ "e.first_name, e.last_name, e.email, e.department, "
			+ "p.project_name, p.project_description, p.project_start_date, p.project_end_date "
			+ "FROM project_assignees pa " + "INNER JOIN employees e ON pa.employee_id = e.employee_id "
			+ "INNER JOIN projects p ON pa.project_id = p.project_id where (pa.assign_date like %:keyword% or e.first_name like "
			+ "%:keyword% or e.last_name like %:keyword% or e.department like %:keyword% or p.project_name like %:keyword% or "
			+ "p.project_description like %:keyword%) and pa.is_deleted = :isDeleted and pa.is_employee_working = :isEmployeeWorking", nativeQuery = true)
	Page<Object[]> findByKeywordAndIsDeletedAndIsEmployeeWorking(@Param(value = "keyword") String keyword,
			@Param(value = "isDeleted") Boolean isDeleted,
			@Param(value = "isEmployeeWorking") Boolean isEmployeeWorking, Pageable pageable);

	@Query(value = "SELECT pa.project_id, pa.employee_id, pa.assign_date, pa.is_employee_working, "
			+ "e.first_name, e.last_name, e.email, e.department, "
			+ "p.project_name, p.project_description, p.project_start_date, p.project_end_date "
			+ "FROM project_assignees pa " + "INNER JOIN employees e ON pa.employee_id = e.employee_id "
			+ "INNER JOIN projects p ON pa.project_id = p.project_id where pa.employee_id = :employeeId and pa.is_deleted = :isDeleted "
			+ "and pa.is_employee_working = :isEmployeeWorking", nativeQuery = true)
	Page<Object[]> findByEmployeeIdAndIsDeletedAndIsEmployeeWorking(@Param(value = "employeeId") Long employeeId,
			@Param(value = "isDeleted") Boolean isDeleted,
			@Param(value = "isEmployeeWorking") Boolean isEmployeeWorking, Pageable pageable);

	@Query(value = "SELECT pa.project_id, pa.employee_id, pa.assign_date, pa.is_employee_working, "
			+ "e.first_name, e.last_name, e.email, e.department, "
			+ "p.project_name, p.project_description, p.project_start_date, p.project_end_date "
			+ "FROM project_assignees pa " + "INNER JOIN employees e ON pa.employee_id = e.employee_id "
			+ "INNER JOIN projects p ON pa.project_id = p.project_id where (pa.assign_date like %:keyword% or e.first_name like "
			+ "%:keyword% or e.last_name like %:keyword% or e.department like %:keyword% or p.project_name like %:keyword% or "
			+ "p.project_description like %:keyword%) and pa.employee_id = :employeeId and pa.is_deleted = :isDeleted and pa.is_employee_working = :isEmployeeWorking", nativeQuery = true)
	Page<Object[]> findByKeywordAndEmployeeIdAndIsDeletedAndIsEmployeeWorking(@Param(value = "keyword") String keyword,
			@Param(value = "employeeId") Long employeeId, @Param(value = "isDeleted") Boolean isDeleted,
			@Param(value = "isEmployeeWorking") Boolean isEmployeeWorking, Pageable pageable);

	@Query(value = "SELECT pa.project_id, pa.employee_id, pa.assign_date, pa.is_employee_working, "
			+ "e.first_name, e.last_name, e.email, e.department, "
			+ "p.project_name, p.project_description, p.project_start_date, p.project_end_date "
			+ "FROM project_assignees pa " + "INNER JOIN employees e ON pa.employee_id = e.employee_id "
			+ "INNER JOIN projects p ON pa.project_id = p.project_id where pa.project_id = :projectId and pa.is_deleted = :isDeleted "
			+ "and pa.is_employee_working = :isEmployeeWorking", nativeQuery = true)
	Page<Object[]> findByProjectIdAndIsDeletedAndIsEmployeeWorking(@Param(value = "projectId") Long projectId,
			@Param(value = "isDeleted") Boolean isDeleted,
			@Param(value = "isEmployeeWorking") Boolean isEmployeeWorking, Pageable pageable);

	@Query(value = "SELECT pa.project_id, pa.employee_id, pa.assign_date, pa.is_employee_working, "
			+ "e.first_name, e.last_name, e.email, e.department, "
			+ "p.project_name, p.project_description, p.project_start_date, p.project_end_date "
			+ "FROM project_assignees pa " + "INNER JOIN employees e ON pa.employee_id = e.employee_id "
			+ "INNER JOIN projects p ON pa.project_id = p.project_id where (pa.assign_date like %:keyword% or e.first_name like "
			+ "%:keyword% or e.last_name like %:keyword% or e.department like %:keyword% or p.project_name like %:keyword% or "
			+ "p.project_description like %:keyword%) and pa.project_id = :projectId and pa.is_deleted = :isDeleted and pa.is_employee_working = :isEmployeeWorking", nativeQuery = true)
	Page<Object[]> findByKeywordAndProjectIdAndIsDeletedAndIsEmployeeWorking(String keyword, Long projectId, Boolean isDeleted,
			Boolean isEmployeeWorking, Pageable pageable);
}
