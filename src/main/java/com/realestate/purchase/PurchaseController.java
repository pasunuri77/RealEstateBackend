package com.realestate.purchase;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchaserequests")
public class PurchaseController {

    @Autowired
    private PurchaseService service;


    @PostMapping
    public ResponseEntity<Purchase> createRequest(
            @Valid @RequestBody PurchaseRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createRequest(request));
    }

    @GetMapping
    public ResponseEntity<List<Purchase>>
    getAllRequests() {

        return ResponseEntity.ok(
                service.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase>
    getRequestById(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.getRequestById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteRequest(@PathVariable Long id) {

        service.deleteRequest(id);

        return ResponseEntity.noContent().build();
    }
}
