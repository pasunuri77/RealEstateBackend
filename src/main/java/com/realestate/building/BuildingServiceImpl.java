package com.realestate.building;

import com.realestate.exception.ResourceNotFoundException;
import com.realestate.project.Project;
import com.realestate.repositories.BuildingRepo;
import com.realestate.repositories.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepo repository;

    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public Building createBuilding(BuildingRequest request) {

        Project project = projectRepo.findById(request.getProjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id : "
                                        + request.getProjectId()));



        Building building = Building.builder()
                .projectId(request.getProjectId())
                .name(request.getName())
//                .latitude(request.getLatitude())
//                .longitude(request.getLongitude())
                .totalFloors(request.getTotalFloors())
                .description(request.getDescription())
//                .address(request.getAddress())
                .build();

        return repository.save(building);
    }

    @Override
    public Building updateBuilding(Long id,
                                   BuildingRequest request) {

        Building building = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Building not found with id : " + id));

        building.setProjectId(request.getProjectId());
        building.setName(request.getName());
        building.setTotalFloors(request.getTotalFloors());
        building.setDescription(request.getDescription());
//        building.setAddress(request.getAddress());
//        building.setLatitude(request.getLatitude());
//        building.setLongitude(request.getLongitude());

        return repository.save(building);
    }

    @Override
    public Building getBuildingById(Long id) {


        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Building not found with id : " + id));
    }

    @Override
    public List<Building> getAllBuildings() {
        return repository.findAll();
    }

    @Override
    public List<Building> getBuildingsByProject(Long projectId) {

        return repository.findByProjectId(projectId);
    }

    @Override
    public void deleteBuilding(Long id) {

        Building building = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Building not found with id : " + id));

        repository.delete(building);
    }


}
