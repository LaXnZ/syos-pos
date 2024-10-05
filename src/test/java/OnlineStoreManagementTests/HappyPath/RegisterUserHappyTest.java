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

public class RegisterUserHappyTest {

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
    public void testSuccessfulRegistration() {
        // Simulate user input for the registration process
        String input = "2\nJohn Doe\n0711234567\njohn.doe@example.com\n3\n";  // Registration flow input + back to main menu
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Call the OnlineShoppingCLI method to handle user registration
        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);

        // Verify the customerManager.addCustomer was called with a Customer object
        verify(customerManager).addCustomer(any(Customer.class));

        // Print the confirmation message for testing purposes
        System.out.println("Registration successful for: John Doe");
    }
}
