package com.realestate.project;

import com.realestate.repositories.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService service;

    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestBody ProjectRequest request) {

        return ResponseEntity.ok(
                service.createProject(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectRequest request) {

        return ResponseEntity.ok(
                service.updateProject(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getProjectById(id));
    }

    @GetMapping
    public ResponseEntity<List<Project>>
    getAllProjects() {

        return ResponseEntity.ok(
                service.getAllProjects());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>>
    getProjectsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                service.getProjectsByStatus(status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(
            @PathVariable Long id) {

        service.deleteProject(id);

        return ResponseEntity.ok(
                "Project deleted successfully");
    }


}
