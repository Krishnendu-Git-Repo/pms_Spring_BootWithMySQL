package com.project.projectManagementSystem.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.projectManagementSystem.model.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
	Optional<Project> findByProjectName(String projectName);
	
	List<Project> findByIsDeleted(Boolean isDeleted);

	@Query(value = "select * from projects where project_name like %:keyword% or project_description like %:keyword% and"
			+ " is_deleted = :isDeleted", nativeQuery = true)
	Page<Project> findBykeywordAndIsDeleted(@Param("keyword") String keyword, @Param("isDeleted") Boolean isDeleted, Pageable pageable);
	
	@Query(value = "select * from projects where is_deleted = :isDeleted", nativeQuery = true)
	Page<Project> findByisDelete(@Param("isDeleted") Boolean isDeleted, Pageable pageable);
	
	@Query(value = "select * from projects where project_id IN (:projectId) and is_deleted = :isDeleted", nativeQuery = true)
	Page<Project> findByIdInAndIsDeleted(List<Long> projectId, Boolean isDeleted, Pageable pageable);

}
