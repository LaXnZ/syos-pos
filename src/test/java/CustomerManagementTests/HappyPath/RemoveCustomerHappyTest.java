package CustomerManagementTests.HappyPath;

import business.customer.CustomerManagerImpl;
import entities.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RemoveCustomerHappyTest {

    @Test
    public void testRemoveCustomerSuccessfully() {
        CustomerRepository mockRepo = Mockito.mock(CustomerRepository.class);
        CustomerManagerImpl customerManager = new CustomerManagerImpl(mockRepo);

        customerManager.removeCustomer(1);


        Mockito.verify(mockRepo, Mockito.times(1)).delete(1);
    }
}
