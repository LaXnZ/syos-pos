package business.customer;

import entities.models.Customer;
import java.util.List;

public interface CustomerManager {
    void addCustomer(Customer customer);
    Customer findCustomerById(int customerId);
    Customer findCustomerByName(String customerName);  // Add this method
    void updateCustomer(Customer customer);
    void removeCustomer(int customerId);
    List<Customer> findAllCustomers();  // Ensure this method is here
}
