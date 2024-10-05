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
        // Set up mock customer
        Customer customer = new Customer("John Doe", "0711234567", "john.doe@example.com", 0, BigDecimal.ZERO, LocalDate.now());

        // Simulate user input for the login process and following menu interactions
        // The input is:
        // 1 -> Select login option
        // john.doe@example.com -> Enter email
        // 2 -> Select "Checkout" from the menu
        String input = "1\njohn.doe@example.com\n2\n";  // Simulating login and then choosing "Checkout"
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Mock the customerManager behavior to return a valid customer
        when(customerManager.findCustomerByEmail("john.doe@example.com")).thenReturn(customer);

        // Call the method to handle user login
        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);

        // Verify that the customer was successfully logged in
        verify(customerManager).findCustomerByEmail("john.doe@example.com");

        // Print success message for testing purposes
        System.out.println("Successfully logged in with: " + customer.getName());
    }
}
