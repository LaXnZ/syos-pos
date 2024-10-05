package OnlineStoreManagementTests.SadPath;

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

public class RegisterUserSadTest {

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
    public void testFailedRegistrationWithDuplicatePhoneNumber() {
        // Simulate user input where the phone number already exists
        String input = "2\nJohn Doe\n0711234567\njohn.doe@example.com\n";  // Simulate registration input
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Mock behavior: simulate that the phone number already exists
        doThrow(new RuntimeException("Phone number already exists")).when(customerManager).addCustomer(any(Customer.class));

        // Try to handle the exception during registration
        try {
            OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);
        } catch (RuntimeException e) {
            // Confirm that the registration failed due to duplicate phone number
            System.out.println("Registration failed: " + e.getMessage());
        }

        // Verify that addCustomer was called, but the process failed
        verify(customerManager, times(1)).addCustomer(any(Customer.class));
        System.out.println("Test completed: Registration failed as expected due to duplicate phone number.");
    }

    @Test
    public void testFailedRegistrationWithInvalidEmail() {
        // Simulate user input with an invalid email address
        String input = "2\nJohn Doe\n0711234567\ninvalid-email\n";  // Simulate registration with an invalid email
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Mock behavior: simulate validation failure for an invalid email
        doThrow(new RuntimeException("Invalid email address")).when(customerManager).addCustomer(any(Customer.class));

        // Try to handle the exception during registration
        try {
            OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);
        } catch (RuntimeException e) {
            // Confirm that the registration failed due to invalid email
            System.out.println("Registration failed: " + e.getMessage());
        }

        // Verify that addCustomer was called, but the process failed
        verify(customerManager, times(1)).addCustomer(any(Customer.class));
        System.out.println("Test completed: Registration failed as expected due to invalid email.");
    }
}
