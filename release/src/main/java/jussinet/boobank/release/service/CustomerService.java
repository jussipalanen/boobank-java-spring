package jussinet.boobank.release.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jussinet.boobank.release.entity.Customer;

public class CustomerService {
    EntityManager entityManager;

    public CustomerService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public long saveCustomer(Customer customer) {
        entityManager.persist(customer);
        return customer.getId();
    }
}
