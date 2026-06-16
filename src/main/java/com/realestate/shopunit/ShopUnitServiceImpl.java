package com.realestate.shopunit;

import com.realestate.building.Building;
import com.realestate.exception.BadRequestException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.floors.Floor;
import com.realestate.project.Project;
import com.realestate.repositories.BuildingRepo;
import com.realestate.repositories.FloorRepo;
import com.realestate.repositories.ProjectRepo;
import com.realestate.repositories.ShopUnitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopUnitServiceImpl implements ShopUnitService {

    @Autowired
    private ShopUnitRepo shopUnitRepo;

    @Autowired
    private FloorRepo floorRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private BuildingRepo buildingRepo;


    @Override
    public ShopUnit createUnit(ShopUnitRequest request) {
        // Project Validation
        Project project = projectRepo.findById(
                        request.getProjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id "
                                        + request.getProjectId()));

        // Building Validation
        Building building = buildingRepo.findById(
                        request.getBuildingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Building not found with id "
                                        + request.getBuildingId()));

        if (!building.getProjectId()
                .equals(project.getId())) {

            throw new BadRequestException(
                    "Building does not belong to Project");
        }

        Floor floor = floorRepo.findById(request.getFloorId()).orElseThrow(
                () -> new ResourceNotFoundException("Floor not found" + request.getFloorId())
        );
        if (!floor.getBuildingId()
                .equals(building.getId())) {

            throw new BadRequestException(
                    "Floor does not belong to Building");
        }

        // Duplicate Unit Check
        if (shopUnitRepo
                .existsByFloorIdAndUnitNumber(
                        request.getFloorId(),
                        request.getUnitNumber())) {

            throw new BadRequestException(
                    "Unit number already exists on this floor");
        }

        // Maximum Units Check
        long existingUnits =
                shopUnitRepo.countByFloorId(
                        request.getFloorId());

        if (existingUnits >= floor.getUnits()) {

            throw new BadRequestException(
                    "Cannot create more units. Floor allows only "
                            + floor.getUnits()
                            + " units");
        }


        ShopUnit unit = ShopUnit.builder()
                .projectId(request.getProjectId())
                .buildingId(request.getBuildingId())
                .floorId(request.getFloorId())
                .unitNumber(request.getUnitNumber())
                .unitType(request.getUnitType())
                .areaSqft(request.getAreaSqft())
                .availabilityType(request.getAvailabilityType())
                .monthlyRent(request.getMonthlyRent())
                .yearlyRent(request.getYearlyRent())
                .salePrice(request.getSalePrice())
                .bookingAmount(request.getBookingAmount())
                .maintenanceCharges(request.getMaintenanceCharges())
                .status(request.getStatus())
                .build();

        return shopUnitRepo.save(unit);
    }

    @Override
    public ShopUnit updateUnit(
            Long id,
            ShopUnitRequest request) {

        ShopUnit unit = shopUnitRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Unit not found"));

        unit.setProjectId(request.getProjectId());
        unit.setBuildingId(request.getBuildingId());
        unit.setFloorId(request.getFloorId());
        unit.setUnitNumber(request.getUnitNumber());
        unit.setUnitType(request.getUnitType());
        unit.setAreaSqft(request.getAreaSqft());
        unit.setAvailabilityType(
                request.getAvailabilityType());
        unit.setMonthlyRent(
                request.getMonthlyRent());
        unit.setYearlyRent(
                request.getYearlyRent());
        unit.setSalePrice(
                request.getSalePrice());
        unit.setBookingAmount(
                request.getBookingAmount());
        unit.setMaintenanceCharges(
                request.getMaintenanceCharges());
        unit.setStatus(
                request.getStatus());

        return shopUnitRepo.save(unit);
    }

    @Override
    public ShopUnit getUnitById(Long id) {

        return shopUnitRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Unit not found"));
    }

    @Override
    public List<ShopUnit> getAllUnits() {

        List<ShopUnit> shopUnit = shopUnitRepo.findAll();

        if (shopUnit == null) {
            throw new ResourceNotFoundException("No Unit found");
        }
        return shopUnit;
    }

    @Override
    public List<ShopUnit> getUnitsByProject(
            Long projectId) {

        return shopUnitRepo.findByProjectId(
                projectId);
    }

    @Override
    public void deleteUnit(Long id) {

        ShopUnit unit = shopUnitRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Unit not found"));

        shopUnitRepo.delete(unit);
    }

}

