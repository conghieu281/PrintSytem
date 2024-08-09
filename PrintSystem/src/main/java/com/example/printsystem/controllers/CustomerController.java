package com.example.printsystem.controllers;

import com.example.printsystem.models.entity.Customer;
import com.example.printsystem.models.entity.Project;
import com.example.printsystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService _customerService;

    @GetMapping("/")
    public List<Customer> getAllCustomer() {
        return _customerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        try {
            Customer customer = _customerService.getCustomerById(id);
            return ResponseEntity.ok().body(customer);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            return ResponseEntity.ok(_customerService.addNewCustomer(customer));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Project> update(@PathVariable Long id, @RequestBody Project project) {
//        project.setId(id);
//        Project updatedProject = _projectService.updateProject(project);
//        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        _customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
