package CustomerManagementTests.SadPath;

import business.customer.CustomerManagerImpl;
import entities.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveCustomerSadTest {

    @Test
    public void testRemoveNonExistentCustomer() {
        CustomerRepository mockRepo = Mockito.mock(CustomerRepository.class);
        CustomerManagerImpl customerManager = new CustomerManagerImpl(mockRepo);


        Mockito.doThrow(new IllegalArgumentException("Customer not found"))
                .when(mockRepo).delete(99);

        assertThrows(IllegalArgumentException.class, () -> customerManager.removeCustomer(99));


        Mockito.verify(mockRepo, Mockito.times(1)).delete(99);
    }
}
