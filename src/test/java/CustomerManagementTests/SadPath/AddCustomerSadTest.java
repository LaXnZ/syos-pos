package CustomerManagementTests.SadPath;

import business.customer.CustomerManagerImpl;
import entities.models.Customer;
import entities.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.Mockito.doThrow;

public class AddCustomerSadTest {

    @Test
    public void testAddCustomerFailure() {
        CustomerRepository mockRepo = Mockito.mock(CustomerRepository.class);
        CustomerManagerImpl customerManager = new CustomerManagerImpl(mockRepo);

        Customer customer = new Customer("John Doe", "invalidPhone", "john@example.com", LocalDate.now());

        // Simulate exception during save
        doThrow(new IllegalArgumentException("Phone number must be 10 digits"))
                .when(mockRepo).save(Mockito.any(Customer.class));

        try {
            customerManager.addCustomer(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        // Verify that save() was called despite the exception
        Mockito.verify(mockRepo, Mockito.times(1)).save(customer);
    }
}
