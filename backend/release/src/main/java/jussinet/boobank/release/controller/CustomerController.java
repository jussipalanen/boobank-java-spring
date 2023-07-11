package jussinet.boobank.release.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jussinet.boobank.release.entity.Customer;
import jussinet.boobank.release.repository.CustomerRepository;

@RestController
public class CustomerController {


    @Autowired
    private CustomerRepository repository;

    CustomerController(CustomerRepository customerRepository) {
        this.repository = customerRepository;
    }

    /**
     * Get all of the customers
     * 
     * @return
     */
    @GetMapping(value = "/api/customers")
    List<Customer> all() {
        return repository.findAll();
    }

    /**
     * Get single customer by id
     * 
     * @param id
     * @return
     */
    @GetMapping(value = "/api/customer/{id}")
    public Optional<Customer> get(@PathVariable int id) {
        return repository.findById(id);
    }


    @PostMapping
    public Customer post(Customer customer)
    {
        repository.save(customer);
        return customer;
    }
}
