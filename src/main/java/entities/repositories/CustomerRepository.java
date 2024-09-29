package entities.repositories;

import entities.models.Customer;
import java.util.List;

public interface CustomerRepository {
    void save(Customer customer);
    Customer findById(int customerId);
    Customer findByName(String customerName);  // Add this method
    void update(Customer customer);  // Add update method
    void delete(int customerId);
    List<Customer> findAll();  // Add findAll method
}
