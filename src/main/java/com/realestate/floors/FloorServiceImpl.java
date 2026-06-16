package com.realestate.floors;

import com.realestate.building.Building;

import com.realestate.exception.BadRequestException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repositories.BuildingRepo;
import com.realestate.repositories.FloorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorRepo floorRepository;
    @Autowired
    private BuildingRepo buildingRepository;

    @Override
    public Floor createFloor(FloorRequest request) {

        Building building = buildingRepository
                .findById(request.getBuildingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Building not found with id "
                                        + request.getBuildingId()));

        long existingFloors =
                floorRepository.countByBuildingId(
                        request.getBuildingId());

        if (existingFloors >= building.getTotalFloors()) {

            throw new BadRequestException(
                    ("cannot create more floors.Building allows only '"
                            + building.getTotalFloors() + "' floors.")
            );

        }

        if (floorRepository
                .existsByBuildingIdAndFloorNumber(
                        request.getBuildingId(),
                        request.getFloorNumber())) {

            throw new BadRequestException(
                    "Floor number "
                            + request.getFloorNumber()
                            + " already exists");
        }

        Floor floor = Floor.builder()
                .buildingId(request.getBuildingId())
                .floorNumber(request.getFloorNumber())
                .floorName(request.getFloorName())
                .description(request.getDescription())
                .units(request.getUnits())
                .build();

        return floorRepository.save(floor);
    }

    @Override
    public Floor updateFloor(Long id,
                             FloorRequest request) {

        Floor floor = floorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Floor not found with id : " + id));

        floor.setBuildingId(request.getBuildingId());
        floor.setFloorNumber(request.getFloorNumber());
        floor.setFloorName(request.getFloorName());
        floor.setDescription(request.getDescription());
        floor.setUnits(request.getUnits());

        return floorRepository.save(floor);
    }

    @Override
    public Floor getFloorById(Long id) {

        return floorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Floor not found with id : " + id));
    }

    @Override
    public List<Floor> getAllFloors() {
        return floorRepository.findAll();
    }

    @Override
    public List<Floor> getFloorsByBuilding(Long buildingId) {
        return floorRepository.findByBuildingId(buildingId);
    }

    @Override
    public void deleteFloor(Long id) {

        Floor floor = floorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Floor not found with id : " + id));

        floorRepository.delete(floor);
    }

}
