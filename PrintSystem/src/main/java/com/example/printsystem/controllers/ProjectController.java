package com.example.printsystem.controllers;

import com.example.printsystem.models.entity.Project;
import com.example.printsystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService _projectService;

    @GetMapping("/")
    public List<Project> getAllProjects() {
        return _projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        try {
            Project project = _projectService.getProjectById(id).get();
            return ResponseEntity.ok().body(project);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            return ResponseEntity.ok(_projectService.createProject(project));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        project.setId(id);
        Project updatedProject = _projectService.updateProject(project);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        _projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
