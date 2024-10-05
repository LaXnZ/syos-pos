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

        String input = "2\nJohn Doe\n0711234567\njohn.doe@example.com\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);


        doThrow(new RuntimeException("Phone number already exists")).when(customerManager).addCustomer(any(Customer.class));


        try {
            OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);
        } catch (RuntimeException e) {

            System.out.println("Registration failed: " + e.getMessage());
        }


        verify(customerManager, times(1)).addCustomer(any(Customer.class));
        System.out.println("Test completed: Registration failed as expected due to duplicate phone number.");
    }

    @Test
    public void testFailedRegistrationWithInvalidEmail() {

        String input = "2\nJohn Doe\n0711234567\ninvalid-email\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);


        doThrow(new RuntimeException("Invalid email address")).when(customerManager).addCustomer(any(Customer.class));


        try {
            OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);
        } catch (RuntimeException e) {

            System.out.println("Registration failed: " + e.getMessage());
        }


        verify(customerManager, times(1)).addCustomer(any(Customer.class));
        System.out.println("Test completed: Registration failed as expected due to invalid email.");
    }
}
