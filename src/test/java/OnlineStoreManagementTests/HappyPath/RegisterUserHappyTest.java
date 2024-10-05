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

        String input = "2\nJohn Doe\n0711234567\njohn.doe@example.com\n3\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);


        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);


        verify(customerManager).addCustomer(any(Customer.class));


        System.out.println("Registration successful for: John Doe");
    }
}
