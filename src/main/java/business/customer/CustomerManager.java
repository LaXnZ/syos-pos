package business.customer;

import entities.models.Customer;
import java.util.List;

public interface CustomerManager {
    void addCustomer(Customer customer);
    Customer findCustomerById(int customerId);
    Customer findCustomerByPhoneNumber(String phoneNumber);  // Updated this method to find by phone number
    void updateCustomer(Customer customer);
    void removeCustomer(int customerId);
    List<Customer> findAllCustomers();
}
