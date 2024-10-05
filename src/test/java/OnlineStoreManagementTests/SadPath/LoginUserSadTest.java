package OnlineStoreManagementTests.SadPath;

import business.billing.BillingManager;
import business.customer.CustomerManager;
import business.item.ItemManager;
import core.OnlineShoppingCLI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.mockito.Mockito.*;

public class LoginUserSadTest {

    private CustomerManager customerManager;
    private ItemManager itemManager;
    private BillingManager billingManager;
    private Scanner scanner;

    @BeforeEach
    public void setup() {
        customerManager = mock(CustomerManager.class);
        itemManager = mock(ItemManager.class);
        billingManager = mock(BillingManager.class);
        scanner = mock(Scanner.class);
    }

    @Test
    public void testLoginWithInvalidEmail() {

        when(scanner.nextLine()).thenReturn("invalid.email@example.com");


        when(customerManager.findCustomerByEmail("invalid.email@example.com")).thenReturn(null);


        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);


        verify(customerManager).findCustomerByEmail("invalid.email@example.com");
        System.out.println("Customer not found with email invalid.email@example.com.");
    }
}
