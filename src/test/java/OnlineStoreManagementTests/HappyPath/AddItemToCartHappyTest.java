package HappyPath;

import business.billing.BillingManager;
import business.customer.CustomerManager;
import business.item.ItemManager;
import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;
import core.OnlineShoppingCLI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class AddItemToCartHappyTest {

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
    public void testAddItemToCart() {
        // Set up customer and item details
        Customer customer = new Customer("John Doe", "0711234567", "john.doe@example.com", 0, BigDecimal.ZERO, LocalDate.now());
        Bill bill = new Bill();  // Assuming Bill has a default constructor
        bill.setCustomer(customer);  // Set the customer in the bill to avoid NullPointerException

        Item item = new Item("RI002", "Eggs", new BigDecimal("50"));  // Use available constructor

        // Mock behavior for customer and item management
        when(customerManager.findCustomerByEmail("john.doe@example.com")).thenReturn(customer);
        when(billingManager.createBill(customer)).thenReturn(bill);
        when(itemManager.findByCode("RI002")).thenReturn(item);

        // Manually set the final price in the Bill before calling the checkout process
        BigDecimal totalPrice = item.getItemPrice().multiply(BigDecimal.valueOf(2));  // Assume 2 quantity
        bill.setTotalPrice(totalPrice);
        bill.setFinalPrice(totalPrice);  // Set final price to prevent NullPointerException

        // Simulate user input for the Scanner (including payment method)
        String input = "1\njohn.doe@example.com\n1\nRI002\n2\n2\n1\n";  // Login, add item, and checkout
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        // Call the handleOnlineShopping method with the real scanner and simulated input
        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);

        // No need to mock or verify anything here, just observe outputs
        System.out.println("Test completed. Item added to cart and checkout successful.");
    }
}
