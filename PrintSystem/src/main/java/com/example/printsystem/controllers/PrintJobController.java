package com.example.printsystem.controllers;

import com.example.printsystem.models.Enum.EPrintJob;
import com.example.printsystem.models.Enum.EProjectStatus;
import com.example.printsystem.models.dto.PrintJobDTO;
import com.example.printsystem.models.entity.PrintJobs;
import com.example.printsystem.models.entity.Project;
import com.example.printsystem.services.EmailService;
import com.example.printsystem.services.PrintJobService;
import com.example.printsystem.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/printjobs")
public class PrintJobController {
    @Autowired
    private PrintJobService _printJobsService;

    @Autowired
    private ProjectService _projectService;

    @Autowired
    private EmailService _emailService;

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @PostMapping("/prepare")
    public ResponseEntity<?> prepareForPrint(@RequestBody PrintJobDTO request) {
        try {
            _printJobsService.prepareForPrint(request);
            return ResponseEntity.ok("Print job prepared successfully");
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @PutMapping("/confirmReceived/{id}")
    public ResponseEntity<?> confirmReceived(@PathVariable Long id) {
        try {
            PrintJobs printJobs = _printJobsService.getPrintJobById(id);
            printJobs.setPrintJobStatus(EPrintJob.PRINT_JOB_RECEIVED);
            _printJobsService.updatePrintJob(printJobs);
            return ResponseEntity.ok(printJobs);
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

    }

    @PreAuthorize("hasRole('ROLE_LEADER')")
    @PutMapping("/confirmCompleted/{id}")
    public ResponseEntity<?> confirmPrintJobCompleted(@PathVariable Long id) {
        try {
            PrintJobs printJobs = _printJobsService.getPrintJobById(id);
            printJobs.setPrintJobStatus(EPrintJob.PRINT_JOB_DONE);
            Project project = printJobs.getDesignPrintJob().getProjectDesign();
            project.setProjectStatus(EProjectStatus.PROJECT_STATUS_DONE);
            _projectService.updateProject(project);
            _printJobsService.updatePrintJob(printJobs);
            _emailService.sendDoneNotifyEmail(project.getCustomerProject().getEmail());
            return ResponseEntity.ok(project);
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

    }

    @GetMapping("/design/{designId}")
    public ResponseEntity<?> getPrintJobsByDesign(@PathVariable Long designId){
        List<PrintJobs> printJobs = _printJobsService.getPrintJobsByDesignId(designId);
        return ResponseEntity.ok(printJobs);
    }
}
