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

public class LoyaltyPointsSadTest {

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
    public void testApplyLoyaltyPointsFailureDueToInsufficientPoints() {
        // Set up customer with insufficient loyalty points and item details
        Customer customer = new Customer("John Doe", "0711234567", "john.doe@example.com", 10, BigDecimal.ZERO, LocalDate.now()); // Only 10 points
        Bill bill = new Bill();
        bill.setCustomer(customer);

        Item item = new Item("RI002", "Eggs", new BigDecimal("5000"));  // High price to simulate failure

        // Mock behavior for customer and item management
        when(customerManager.findCustomerByEmail("john.doe@example.com")).thenReturn(customer);
        when(billingManager.createBill(customer)).thenReturn(bill);
        when(itemManager.findByCode("RI002")).thenReturn(item);

        // Set total price and discount amount
        BigDecimal totalPrice = item.getItemPrice().multiply(BigDecimal.valueOf(2));  // Assume 2 quantity
        bill.setTotalPrice(totalPrice);

        // Initialize discountAmount in Bill to avoid null pointer exception
        bill.setDiscountAmount(BigDecimal.ZERO);  // No discount applied yet

        // Simulate user input where loyalty points are insufficient
        String input = "1\njohn.doe@example.com\n1\nRI002\n2\n2\ny\n1\n";  // Login, add item, try to use loyalty points, select card payment
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        // Simulate failure in loyalty points application (e.g., by throwing an exception or error)
        doThrow(new RuntimeException("Insufficient loyalty points")).when(billingManager).finalizeBill(any(Bill.class), anyDouble(), anyBoolean());

        // Try to handle the exception during checkout
        try {
            OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);
        } catch (RuntimeException e) {
            // Confirm that the checkout failed due to insufficient loyalty points
            System.out.println("Checkout failed: " + e.getMessage());
        }

        // Verify that finalizeBill was called, but the process failed
        verify(billingManager, times(1)).finalizeBill(any(Bill.class), anyDouble(), anyBoolean());
        System.out.println("Test completed: Loyalty points application failed as expected.");
    }
}
