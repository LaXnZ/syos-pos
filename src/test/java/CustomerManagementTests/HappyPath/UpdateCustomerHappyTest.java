package CustomerManagementTests.HappyPath;

import business.customer.CustomerManagerImpl;
import entities.models.Customer;
import entities.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

public class UpdateCustomerHappyTest {

    @Test
    public void testUpdateCustomerSuccessfully() {
        CustomerRepository mockRepo = Mockito.mock(CustomerRepository.class);
        CustomerManagerImpl customerManager = new CustomerManagerImpl(mockRepo);

        Customer existingCustomer = new Customer("Jane Doe", "0123456789", "jane@example.com", LocalDate.now());
        existingCustomer.setCustomerId(1);

        customerManager.updateCustomer(existingCustomer);

        // Verify if update() was called once
        Mockito.verify(mockRepo, Mockito.times(1)).update(existingCustomer);
    }
}
