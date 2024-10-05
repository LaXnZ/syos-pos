package OnlineStoreManagementTests.SadPath;

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

public class CheckoutSadTest {

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
    public void testCheckoutFailsDueToInsufficientFunds() {
        // Set up customer and item details
        Customer customer = new Customer("John Doe", "0711234567", "john.doe@example.com", 100, BigDecimal.ZERO, LocalDate.now());
        Bill bill = new Bill();
        bill.setCustomer(customer);

        Item item = new Item("RI002", "Eggs", new BigDecimal("5000"));  // High price to simulate insufficient funds scenario

        // Mock behavior for customer and item management
        when(customerManager.findCustomerByEmail("john.doe@example.com")).thenReturn(customer);
        when(billingManager.createBill(customer)).thenReturn(bill);
        when(itemManager.findByCode("RI002")).thenReturn(item);

        // Set total price to simulate insufficient funds
        BigDecimal totalPrice = item.getItemPrice().multiply(BigDecimal.valueOf(2));  // Assume 2 quantity
        bill.setTotalPrice(totalPrice);
        bill.setFinalPrice(totalPrice);

        // Simulate user input for checkout, including loyalty points question and payment method selection
        String input = "1\njohn.doe@example.com\n1\nRI002\n2\n2\nn\n1\n";  // Login, add item, decline to use loyalty points, select card payment
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        // Simulate a checkout failure by throwing an exception in finalizeBill
        doThrow(new RuntimeException("Insufficient funds")).when(billingManager).finalizeBill(any(Bill.class), anyDouble(), anyBoolean());

        // Try to handle the exception during checkout
        try {
            OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);
        } catch (RuntimeException e) {
            // Confirm that the checkout failed due to insufficient funds
            System.out.println("Checkout failed: " + e.getMessage());
        }

        // Verify that finalizeBill was called but resulted in an exception
        verify(billingManager, times(1)).finalizeBill(any(Bill.class), anyDouble(), anyBoolean());
        System.out.println("Test completed: Checkout failed as expected.");
    }
}
