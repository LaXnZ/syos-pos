package OnlineStoreManagementTests.HappyPath;

import business.billing.BillingManager;
import business.customer.CustomerManager;
import business.item.ItemManager;
import core.OnlineShoppingCLI;
import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class CheckoutHappyTest {

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
    public void testSuccessfulCheckout() {
        // set up customer and item details
        Customer customer = new Customer("John Doe", "0711234567", "john.doe@example.com", 100, BigDecimal.ZERO, LocalDate.now());
        Bill bill = new Bill();
        bill.setCustomer(customer);

        Item item = new Item("RI002", "Eggs", new BigDecimal("50"));

        // mock behavior for customer and item management
        when(customerManager.findCustomerByEmail("john.doe@example.com")).thenReturn(customer);
        when(billingManager.createBill(customer)).thenReturn(bill);
        when(itemManager.findByCode("RI002")).thenReturn(item);

        // set total price
        BigDecimal totalPrice = item.getItemPrice().multiply(BigDecimal.valueOf(2));  // Assume 2 quantity
        bill.setTotalPrice(totalPrice);
        bill.setFinalPrice(totalPrice);

        //  simulate user input
            String input = "1\njohn.doe@example.com\n1\nRI002\n2\n2\nn\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        // call the method to be tested
        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);


        verify(billingManager, times(1)).finalizeBill(bill, totalPrice.doubleValue(), true);
        System.out.println("Test completed: Successful checkout.");
    }
}
