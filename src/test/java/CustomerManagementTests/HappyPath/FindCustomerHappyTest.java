package CustomerManagementTests.HappyPath;

import business.customer.CustomerManagerImpl;
import entities.models.Customer;
import entities.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindCustomerHappyTest {

    @Test
    public void testFindCustomerByIdSuccessfully() {
        CustomerRepository mockRepo = Mockito.mock(CustomerRepository.class);
        CustomerManagerImpl customerManager = new CustomerManagerImpl(mockRepo);

        Customer mockCustomer = new Customer();
        mockCustomer.setCustomerId(1);
        mockCustomer.setName("Jane Doe");

        Mockito.when(mockRepo.findById(1)).thenReturn(mockCustomer);

        Customer foundCustomer = customerManager.findCustomerById(1);

        assertEquals(mockCustomer, foundCustomer);
        Mockito.verify(mockRepo, Mockito.times(1)).findById(1);
    }
}
