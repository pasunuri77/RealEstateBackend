package com.realestate.shopunit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopunits")
public class ShopController {

    @Autowired
    private ShopUnitService service;

    @PostMapping
    public ResponseEntity<ShopUnit> createUnit(
            @RequestBody ShopUnitRequest request) {

        return ResponseEntity.ok(
                service.createUnit(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopUnit> updateUnit(
            @PathVariable Long id,
            @RequestBody ShopUnitRequest request) {

        return ResponseEntity.ok(
                service.updateUnit(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopUnit> getUnit(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getUnitById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShopUnit>>
    getAllUnits() {

        return ResponseEntity.ok(
                service.getAllUnits());
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ShopUnit>>
    getUnitsByProject(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(
                service.getUnitsByProject(
                        projectId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUnit(
            @PathVariable Long id) {

        service.deleteUnit(id);

        return ResponseEntity.ok(
                "Unit deleted successfully");
    }
}
