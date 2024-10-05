package BillingManagementTests.SadPath;

import business.billing.BillingManagerImpl;
import entities.models.Bill;
import entities.models.Customer;
import entities.repositories.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FinalizeBillSadTest {

    private BillingManagerImpl billingManager;
    private BillRepository billRepository;

    @BeforeEach
    public void setUp() {
        billRepository = Mockito.mock(BillRepository.class);
        billingManager = new BillingManagerImpl(billRepository, null, null, null, null, null);
    }

    @Test
    public void testFinalizeBillWithInsufficientCash() {

        Customer customer = new Customer("Insufficient Cash", "0712345678", "insufficient.cash@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1500), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billingManager.finalizeBill(bill, 1000, false);
        });

        assertEquals("Insufficient cash provided", exception.getMessage());
        Mockito.verify(billRepository, Mockito.never()).save(bill);
    }

    @Test
    public void testFinalizeBillWithNegativeCash() {

        Customer customer = new Customer("Negative Cash", "0712345678", "negative.cash@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1000), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billingManager.finalizeBill(bill, -500, false);
        });

        assertEquals("Invalid cash amount", exception.getMessage());
        Mockito.verify(billRepository, Mockito.never()).save(bill);
    }

    @Test
    public void testFinalizeBillWithExactCash() {

        Customer customer = new Customer("Exact Cash", "0712345678", "exact.cash@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1000), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);


        billingManager.finalizeBill(bill, 1000, false);


        assertEquals(BigDecimal.ZERO, bill.getChangeAmount());
        assertEquals(BigDecimal.valueOf(1000), bill.getFinalPrice());
        assertEquals(BigDecimal.valueOf(1000), bill.getCashTendered());
        Mockito.verify(billRepository, Mockito.times(1)).save(bill);
    }
}
