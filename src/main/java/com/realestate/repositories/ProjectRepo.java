package com.realestate.repositories;

import com.realestate.project.Project;
import com.realestate.project.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project,Long> {

    List<Project> findByStatus(ProjectStatus status);

}
