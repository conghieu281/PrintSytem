package com.example.printsystem.services.IService;

import com.example.printsystem.models.entity.Customer;

import java.util.List;

public interface ICustomerService {
    public List<Customer> getAllCustomer();
    Customer addNewCustomer( Customer customer);
    Customer updateCustomer (Customer customer) throws Exception;
    String deleteCustomer (Long id);
    Customer getCustomerById(Long id) throws Exception;
}
