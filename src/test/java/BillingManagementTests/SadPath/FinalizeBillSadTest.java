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
        // Arrange
        Customer customer = new Customer("Insufficient Cash", "0712345678", "insufficient.cash@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1500), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billingManager.finalizeBill(bill, 1000, false); // Cash tendered is less than the final price, no loyalty points used
        });

        assertEquals("Insufficient cash provided", exception.getMessage());
        Mockito.verify(billRepository, Mockito.never()).save(bill); // Ensure bill is not saved
    }

    @Test
    public void testFinalizeBillWithNegativeCash() {
        // Arrange
        Customer customer = new Customer("Negative Cash", "0712345678", "negative.cash@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1000), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            billingManager.finalizeBill(bill, -500, false); // Negative cash tendered, no loyalty points used
        });

        assertEquals("Invalid cash amount", exception.getMessage());
        Mockito.verify(billRepository, Mockito.never()).save(bill);
    }

    @Test
    public void testFinalizeBillWithExactCash() {
        // Arrange
        Customer customer = new Customer("Exact Cash", "0712345678", "exact.cash@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1000), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);

        // Act
        billingManager.finalizeBill(bill, 1000, false); // Cash tendered matches final price, no loyalty points used

        // Assert
        assertEquals(BigDecimal.ZERO, bill.getChangeAmount());
        assertEquals(BigDecimal.valueOf(1000), bill.getFinalPrice());
        assertEquals(BigDecimal.valueOf(1000), bill.getCashTendered());
        Mockito.verify(billRepository, Mockito.times(1)).save(bill); // Ensure the bill is saved
    }
}
