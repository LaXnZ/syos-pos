package OnlineStoreManagementTests.HappyPath;

import business.customer.CustomerManager;
import business.item.ItemManager;
import business.billing.BillingManager;
import entities.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.OnlineShoppingCLI;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class LoginUserHappyTest {

    private CustomerManager customerManager;
    private ItemManager itemManager;
    private BillingManager billingManager;

    @BeforeEach
    public void setup() {
        customerManager = mock(CustomerManager.class);
        itemManager = mock(ItemManager.class);
        billingManager = mock(BillingManager.class);
    }

    @Test
    public void testSuccessfulLogin() {
        // set up a valid customer
        Customer customer = new Customer("John Doe", "0711234567", "john.doe@example.com", 0, BigDecimal.ZERO, LocalDate.now());


        String input = "1\njohn.doe@example.com\n2\n";  // Simulating login and then choosing "Checkout"
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // mock behavior for customer management
        when(customerManager.findCustomerByEmail("john.doe@example.com")).thenReturn(customer);


        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);

        verify(customerManager).findCustomerByEmail("john.doe@example.com");


        System.out.println("Successfully logged in with: " + customer.getName());
    }
}
