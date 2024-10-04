package CustomerManagementTests.HappyPath;

import business.customer.CustomerManagerImpl;
import entities.models.Customer;
import entities.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

public class AddCustomerHappyTest {

    @Test
    public void testAddCustomerSuccessfully() {
        CustomerRepository mockRepo = Mockito.mock(CustomerRepository.class);
        CustomerManagerImpl customerManager = new CustomerManagerImpl(mockRepo);

        Customer customer = new Customer("John Doe", "0123456789", "john@example.com", LocalDate.now());

        customerManager.addCustomer(customer);

        // Verify if save() was called once
        Mockito.verify(mockRepo, Mockito.times(1)).save(customer);
    }
}
