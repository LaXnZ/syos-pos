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

        Customer customer = new Customer("John Doe", "0711234567", "john.doe@example.com", 10, BigDecimal.ZERO, LocalDate.now()); // Only 10 points
        Bill bill = new Bill();
        bill.setCustomer(customer);

        Item item = new Item("RI002", "Eggs", new BigDecimal("5000"));  // High price to simulate failure


        when(customerManager.findCustomerByEmail("john.doe@example.com")).thenReturn(customer);
        when(billingManager.createBill(customer)).thenReturn(bill);
        when(itemManager.findByCode("RI002")).thenReturn(item);


        BigDecimal totalPrice = item.getItemPrice().multiply(BigDecimal.valueOf(2));
        bill.setTotalPrice(totalPrice);


        bill.setDiscountAmount(BigDecimal.ZERO);

        String input = "1\njohn.doe@example.com\n1\nRI002\n2\n2\ny\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);


        doThrow(new RuntimeException("Insufficient loyalty points")).when(billingManager).finalizeBill(any(Bill.class), anyDouble(), anyBoolean());


        try {
            OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);
        } catch (RuntimeException e) {

            System.out.println("Checkout failed: " + e.getMessage());
        }


        verify(billingManager, times(1)).finalizeBill(any(Bill.class), anyDouble(), anyBoolean());
        System.out.println("Test completed: Loyalty points application failed as expected.");
    }
}
