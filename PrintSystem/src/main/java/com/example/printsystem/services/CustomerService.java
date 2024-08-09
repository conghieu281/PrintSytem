package com.example.printsystem.services;

import com.example.printsystem.models.entity.Customer;
import com.example.printsystem.models.repository.CustomerRepository;
import com.example.printsystem.services.IService.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository _customerRepository;

    @Override
    public List<Customer> getAllCustomer() {
        return _customerRepository.findAll();
    }

    @Override
    public Customer addNewCustomer(Customer customer) {
        return _customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) throws Exception {
        return _customerRepository.save(customer);
    }

    @Override
    public String deleteCustomer(Long id) {
        _customerRepository.deleteById(id);
        return "Delete successfully";
    }

    @Override
    public Customer getCustomerById(Long id) throws Exception {
        return _customerRepository.findById(id).orElseThrow(() -> new Exception(("Customer not found")));
    }
}
