package com.example.printsystem.controllers;

import com.example.printsystem.models.entity.Design;
import com.example.printsystem.models.entity.PrintJobs;
import com.example.printsystem.services.DesignService;
import com.example.printsystem.services.NotificationService;
import com.example.printsystem.services.PrintJobService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/designs")
public class DesignController {
    @Autowired
    private DesignService _designService;

    @Autowired
    private NotificationService _notificationService;

    @Autowired
    private PrintJobService _printJobService;


    @PreAuthorize("hasAuthority('ROLE_DESIGNER')")
    @PostMapping("/create")
    public ResponseEntity<?> addDesign(@RequestParam("projectId") Long projectId,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam("designerId") Long designerId) {
        try {
            Design createdDesign = _designService.addDesign(projectId, designerId, file);
            return new ResponseEntity<>(createdDesign, HttpStatus.CREATED);
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

    }

    @PreAuthorize("hasAuthority('ROLE_DESIGNER')")
    @PutMapping("/{id}")
    public ResponseEntity<Design> updateDesign(@PathVariable Long id, @RequestBody Design design) {
        design.setId(id);
        Design updatedDesign = _designService.updateDesign(design);
        return new ResponseEntity<>(updatedDesign, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_DESIGNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDesign(@PathVariable Long id) {
        _designService.deleteDesign(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDesignById(@PathVariable Long id) {
        try {
            Design design = _designService.getDesignById(id);
            return new ResponseEntity<>(design, HttpStatus.OK);
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getDesignsByProjectId(@PathVariable Long projectId) {
        List<Design> designs = _designService.getDesignsByProjectId(projectId);
        return new ResponseEntity<>(designs, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_LEADER')")
    @PostMapping("/{projectId}/designs/{designId}/approve")
    public ResponseEntity<?> approveDesign(@PathVariable Long projectId, @PathVariable Long designId, @RequestParam Long leaderId) {
        try {
            Design design = _designService.approveDesign(projectId, designId, leaderId);
            //_notificationService.createNotify(design.getUserDesign().getId(), "Your design has been approved.", "");
            _printJobService.createPrintJob(new PrintJobs(),design);
            return new ResponseEntity<>(design, HttpStatus.OK);
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_LEADER')")
    @PostMapping("/{projectId}/designs/{designId}/reject")
    public ResponseEntity<?> rejectDesign(@PathVariable Long projectId, @PathVariable Long designId, @RequestParam Long leaderId) {
        try {
            Design design = _designService.rejectDesign(projectId, designId, leaderId);
            //_notificationService.createNotify(design.getUserDesign().getId(), "Your design has been rejected.", "");
            return new ResponseEntity<>(design, HttpStatus.OK);
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
