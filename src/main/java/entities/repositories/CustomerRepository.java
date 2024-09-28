package entities.repositories;

import entities.models.Customer;

public interface CustomerRepository {
    void save(Customer customer);
    Customer findById(int customerId);
}
