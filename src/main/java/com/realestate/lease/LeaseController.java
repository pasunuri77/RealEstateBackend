package com.realestate.lease;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    @Autowired
    private LeaseService service;

    @PostMapping
    public ResponseEntity<Lease> createRequest(
            @Valid @RequestBody LeaseRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createRequest(request));
    }

    @GetMapping
    public ResponseEntity<List<Lease>> getAllRequests() {

        return ResponseEntity.ok(
                service.getAllRequests());
    }

    @GetMapping("/{i3d}")
    public ResponseEntity<Lease>
    getRequestById(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.getRequestById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Lease>>
    getByStatus(@PathVariable String status) {

        return ResponseEntity.ok(
                service.getRequestsByStatus(status));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<Lease>
    approveRequest(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.approveRequest(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<Lease>
    rejectRequest(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.rejectRequest(id));
    }

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<Lease>
    updateStatus(@PathVariable Long id, @PathVariable String status) {

        return ResponseEntity.ok(
                service.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteRequest(@PathVariable Long id) {

        service.deleteRequest(id);

        return ResponseEntity.noContent().build();
    }

}
