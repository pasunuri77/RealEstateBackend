package com.realestate.project;

import java.util.List;

public interface ProjectService {

    Project createProject(ProjectRequest request);

    Project updateProject(Long id,
                          ProjectRequest request);

    Project getProjectById(Long id);

    List<Project> getAllProjects();

    List<Project> getProjectsByStatus(String status);

    void deleteProject(Long id);
}
