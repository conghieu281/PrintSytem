package com.example.printsystem.controllers;

import com.example.printsystem.models.entity.ResourceProperty;
import com.example.printsystem.models.entity.ResourcePropertyDetail;
import com.example.printsystem.models.entity.Resources;
import com.example.printsystem.services.ResourcePropertyDetailService;
import com.example.printsystem.services.ResourcePropertyService;
import com.example.printsystem.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    @Autowired
    private ResourceService _resourcesService;

    @Autowired
    private ResourcePropertyService _resourcePropertyService;

    @Autowired
    private ResourcePropertyDetailService _resourcePropertyDetailService;

    @GetMapping("/")
    public ResponseEntity<?> getAllResource(){
        List<Resources> resources = _resourcesService.getAllResource();
        return ResponseEntity.ok(resources);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createResource(@RequestBody Resources resource) {
        Resources createdResource = _resourcesService.createResource(resource);
        return ResponseEntity.ok(createdResource);
    }

    @PostMapping("/resourceProperties/create")
    public ResponseEntity<?> createResourceProperty(@RequestBody ResourceProperty resourceProperty) throws Exception {
        ResourceProperty createdProperty = _resourcePropertyService.createResourceProperty(resourceProperty);
        return ResponseEntity.ok(createdProperty);
    }

    @GetMapping("/resourceProperties/detail/")
    public ResponseEntity<?> getAllResourceDetail(@RequestParam Long resourceId) throws Exception {
        List<ResourcePropertyDetail> details = _resourcePropertyDetailService.getAllDetailByResourceId(resourceId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/resourceProperties/detail-list/")
    public ResponseEntity<?> getAllResourceDetail(){
        List<ResourcePropertyDetail> details = _resourcePropertyDetailService.getAllDetail();
        return ResponseEntity.ok(details);
    }

    @PostMapping("/resourceProperties/detail/create")
    public ResponseEntity<?> createResourcePropertyDetail(@RequestBody ResourcePropertyDetail detail) throws Exception {
        ResourcePropertyDetail createdDetail = _resourcePropertyDetailService.createResourcePropertyDetail(detail);
        return ResponseEntity.ok(createdDetail);
    }
}
