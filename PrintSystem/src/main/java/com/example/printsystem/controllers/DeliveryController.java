package com.example.printsystem.controllers;

import com.example.printsystem.models.entity.Delivery;
import com.example.printsystem.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService _deliveryService;

    @GetMapping("/")
    public ResponseEntity<?> getAllDelivery(){
        return ResponseEntity.ok(_deliveryService.getAllDelivery());
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getAllDeliveryByProjectId(@PathVariable Long projectId){
        return ResponseEntity.ok(_deliveryService.getAllDeliveryByProjectId(projectId));
    }

    @PostMapping("/assign/{deliveryId}/{shipperId}")
    public ResponseEntity<?> assignDeliveriesToShippers(@PathVariable Long deliveryId, @PathVariable Long shipperId) {
        try {
            Delivery delivery = _deliveryService.assignDeliveriesToShippers(deliveryId, shipperId);
            return ResponseEntity.ok(delivery);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirm/{deliveryId}")
    public ResponseEntity<?> confirmDelivery(@PathVariable Long deliveryId) {
        try {
            _deliveryService.confirmDelivery(deliveryId, true);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create/{projectId}")
    public ResponseEntity<?> createDelivery(@PathVariable Long projectId) {
        try {
            Delivery createdDelivery = _deliveryService.createDelivery(projectId);
            return ResponseEntity.ok(createdDelivery);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
