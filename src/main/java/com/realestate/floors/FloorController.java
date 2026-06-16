package com.realestate.floors;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/floors")
public class FloorController {

    @Autowired
    private FloorService floorService;

    @PostMapping
    public ResponseEntity<Floor> createFloor(
             @RequestBody FloorRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(floorService.createFloor(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Floor> updateFloor(
            @PathVariable Long id,
             @RequestBody FloorRequest request) {

        return ResponseEntity.ok(
                floorService.updateFloor(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Floor> getFloorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                floorService.getFloorById(id));
    }

    @GetMapping
    public ResponseEntity<List<Floor>> getAllFloors() {

        return ResponseEntity.ok(
                floorService.getAllFloors());
    }

    @GetMapping("/building/{buildingId}")
    public ResponseEntity<List<Floor>> getFloorsByBuilding(
            @PathVariable Long buildingId) {

        return ResponseEntity.ok(
                floorService.getFloorsByBuilding(buildingId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFloor(
            @PathVariable Long id) {

        floorService.deleteFloor(id);

        return ResponseEntity.ok("Floor deleted successfully");
    }


}
