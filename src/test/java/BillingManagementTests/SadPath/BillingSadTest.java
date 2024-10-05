package BillingManagementTests.SadPath;

import business.billing.BillingManagerImpl;
import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;
import entities.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BillingSadTest {

    private BillingManagerImpl billingManager;
    private BillRepository billRepository;

    @BeforeEach
    public void setUp() {
        billRepository = Mockito.mock(BillRepository.class);
        billingManager = new BillingManagerImpl(billRepository, null, null, null, null, null);
    }

    @Test
    public void testAddInvalidItemToBill() {
        Customer customer = new Customer("Invalid", "0000000000", "invalid@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);
        Item invalidItem = null;

        billingManager.addItemToBill(bill, invalidItem, 1);

        assertEquals(BigDecimal.ZERO, bill.getTotalPrice());
        verify(billRepository, times(0)).save(bill);
    }
}
