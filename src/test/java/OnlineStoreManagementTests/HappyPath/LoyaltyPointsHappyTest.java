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

public class LoyaltyPointsHappyTest {

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
    public void testApplyLoyaltyPointsSuccessfully() {

        Customer customer = new Customer("John Doe", "0711234567", "john.doe@example.com", 100, BigDecimal.ZERO, LocalDate.now());
        Bill bill = new Bill();
        bill.setCustomer(customer);

        Item item = new Item("RI002", "Eggs", new BigDecimal("50"));


        when(customerManager.findCustomerByEmail("john.doe@example.com")).thenReturn(customer);
        when(billingManager.createBill(customer)).thenReturn(bill);
        when(itemManager.findByCode("RI002")).thenReturn(item);


        BigDecimal totalPrice = item.getItemPrice().multiply(BigDecimal.valueOf(2));  // Assume 2 quantity
        bill.setTotalPrice(totalPrice);


        BigDecimal loyaltyPointsDiscount = BigDecimal.valueOf(customer.getLoyaltyPoints());
        BigDecimal finalPrice = totalPrice.subtract(loyaltyPointsDiscount);
        bill.setDiscountAmount(loyaltyPointsDiscount);
        bill.setFinalPrice(finalPrice);


        String input = "1\njohn.doe@example.com\n1\nRI002\n2\n2\ny\n1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);


        OnlineShoppingCLI.handleOnlineShopping(customerManager, itemManager, billingManager, scanner);


        verify(billingManager, times(1)).finalizeBill(bill, finalPrice.doubleValue(), true);
        System.out.println("Test completed: Loyalty points successfully applied.");
    }
}
