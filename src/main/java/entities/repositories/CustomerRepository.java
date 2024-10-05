package entities.repositories;

import entities.models.Customer;
import java.util.List;

public interface CustomerRepository {
    void save(Customer customer);
    Customer findById(int customerId);
    Customer findByPhoneNumber(String phoneNumber);
    void update(Customer customer);
    void delete(int customerId);
    List<Customer> findAll();
    Customer findByEmail(String email);
}
