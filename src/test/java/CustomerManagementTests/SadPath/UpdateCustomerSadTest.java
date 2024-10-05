package CustomerManagementTests.SadPath;

import business.customer.CustomerManagerImpl;
import entities.models.Customer;
import entities.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

public class UpdateCustomerSadTest {

    @Test
    public void testUpdateNonExistentCustomer() {
        CustomerRepository mockRepo = Mockito.mock(CustomerRepository.class);
        CustomerManagerImpl customerManager = new CustomerManagerImpl(mockRepo);

        Customer nonExistentCustomer = new Customer("John Doe", "0123456789", "john@example.com", LocalDate.now());
        nonExistentCustomer.setCustomerId(99);


        Mockito.doThrow(new IllegalArgumentException("Customer does not exist"))
                .when(mockRepo).update(Mockito.any(Customer.class));

        try {
            customerManager.updateCustomer(nonExistentCustomer);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }


        Mockito.verify(mockRepo, Mockito.times(1)).update(nonExistentCustomer);
    }
}
