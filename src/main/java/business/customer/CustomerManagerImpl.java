package business.customer;

import entities.models.Customer;
import entities.repositories.CustomerRepository;
import java.util.List;

public class CustomerManagerImpl implements CustomerManager {

    private final CustomerRepository customerRepository;

    public CustomerManagerImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerById(int customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.update(customer);
    }

    @Override
    public void removeCustomer(int customerId) {
        customerRepository.delete(customerId);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

}
