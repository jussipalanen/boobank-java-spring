package jussinet.boobank.release.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jussinet.boobank.release.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findCustomerById(Integer id);
    public Customer findCustomerByEmail(String email);
}
