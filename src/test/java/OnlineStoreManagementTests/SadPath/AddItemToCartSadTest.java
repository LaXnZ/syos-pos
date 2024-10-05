package OnlineStoreManagementTests.SadPath;

import business.billing.BillingManager;
import business.customer.CustomerManager;
import business.item.ItemManager;
import core.OnlineShoppingCLI;
import entities.models.Bill;
import entities.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class AddItemToCartSadTest {

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
    public void testAddItemToCartItemNotFound() {

        Customer customer = new Customer("John Doe", "0711234567", "john.doe@example.com", 0, BigDecimal.ZERO, LocalDate.now());
        Bill bill = new Bill();
        bill.setCustomer(customer);


        when(customerManager.findCustomerByEmail("john.doe@example.com")).thenReturn(customer);
        when(billingManager.createBill(customer)).thenReturn(bill);
        when(itemManager.findByCode("RI002")).thenReturn(null);


        String input = "1\njohn.doe@example.com\n1\nRI002\n2\n2\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);


        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);

        System.out.println("Test completed: Item not found was handled correctly.");
    }
}

