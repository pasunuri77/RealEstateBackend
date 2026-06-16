package com.realestate.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/buildings")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @PostMapping
    public ResponseEntity<Building> createBuilding(
            @RequestBody BuildingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(buildingService.createBuilding(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Building> updateBuilding(
            @PathVariable Long id,
            @RequestBody BuildingRequest request) {

        return ResponseEntity.ok(
                buildingService.updateBuilding(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Building> getBuildingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                buildingService.getBuildingById(id));
    }

    @GetMapping
    public ResponseEntity<List<Building>> getAllBuildings() {

        return ResponseEntity.ok(
                buildingService.getAllBuildings());
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Building>> getBuildingsByProject(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                buildingService.getBuildingsByProject(projectId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuilding(
            @PathVariable Long id) {

        buildingService.deleteBuilding(id);

        return ResponseEntity.noContent().build();
    }


}
