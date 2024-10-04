package CustomerManagementTests.SadPath;

import business.customer.CustomerManagerImpl;
import entities.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNull;

public class FindCustomerSadTest {

    @Test
    public void testFindNonExistentCustomer() {
        CustomerRepository mockRepo = Mockito.mock(CustomerRepository.class);
        CustomerManagerImpl customerManager = new CustomerManagerImpl(mockRepo);

        // Simulate returning null when customer does not exist
        Mockito.when(mockRepo.findById(99)).thenReturn(null);

        assertNull(customerManager.findCustomerById(99));

        // Verify findById was called once
        Mockito.verify(mockRepo, Mockito.times(1)).findById(99);
    }
}
