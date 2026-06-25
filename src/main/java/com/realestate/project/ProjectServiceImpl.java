package com.realestate.project;

import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repositories.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepo repository;

    @Autowired
    private com.realestate.repositories.BuildingRepo buildingRepo;

    @Autowired
    private com.realestate.repositories.FloorRepo floorRepo;

    @Autowired
    private com.realestate.repositories.ShopUnitRepo shopUnitRepo;


    @Override
    public Project createProject(
            ProjectRequest request) {

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .projectType(request.getProjectType())
                .status(request.getStatus())
                .startDate(request.getStartDate())
                .expectedCompletionDate(
                        request.getExpectedCompletionDate())
                .completedDate(
                        request.getCompletedDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .build();

        return repository.save(project);
    }

    @Override
    public Project updateProject(
            Long id,
            ProjectRequest request) {

        Project project = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found"+id));

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setLocation(request.getLocation());
        project.setProjectType(request.getProjectType());
        project.setStatus(request.getStatus());
        project.setStartDate(request.getStartDate());
        project.setExpectedCompletionDate(
                request.getExpectedCompletionDate());
        project.setCompletedDate(
                request.getCompletedDate());
        project.setUpdatedAt(LocalDateTime.now());
        project.setLongitude(request.getLongitude());
        project.setLatitude(request.getLatitude());

        return repository.save(project);
    }

    @Override
    public Project getProjectById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found"));
    }

    @Override
    public List<Project> getAllProjects() {

        return repository.findAll();
    }

    @Override
    public List<Project> getProjectsByStatus(
            String status) {

        return repository.findByStatus(
                ProjectStatus.valueOf(
                        status.toUpperCase()));
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteProject(Long id) {

        Project project = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found"));

        // 1. Find all buildings related to the project
        java.util.List<com.realestate.building.Building> buildings = buildingRepo.findByProjectId(id);

        // 2. Delete floors related to those buildings
        for (com.realestate.building.Building building : buildings) {
            floorRepo.deleteByBuildingId(building.getId());
        }

        // 3. Delete all buildings related to this project
        buildingRepo.deleteByProjectId(id);

        // 4. Delete all shop units related to this project
        shopUnitRepo.deleteByProjectId(id);

        // 5. Finally, delete the project
        repository.delete(project);
    }

}

