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
import static org.mockito.Mockito.times;

public class DiscountSadTest {

    private BillingManagerImpl billingManager;
    private BillRepository billRepository;

    @BeforeEach
    public void setUp() {
        billRepository = Mockito.mock(BillRepository.class);
        billingManager = new BillingManagerImpl(billRepository, null, null, null, null, null);
    }

    @Test
    public void testApplyInvalidDiscount() {
        // Arrange
        Customer customer = new Customer("Invalid Discount", "0723456789", "invalid.discount@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1000), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);

        // Act
        billingManager.applyDiscount(bill, -10); // Invalid negative discount

        // Assert
        assertEquals(BigDecimal.ZERO, bill.getDiscountAmount()); // No discount should be applied
        assertEquals(BigDecimal.valueOf(1000), bill.getTotalPrice()); // Total price should remain the same
        assertEquals(BigDecimal.valueOf(1000), bill.getFinalPrice()); // Final price should be unchanged
        Mockito.verify(billRepository, times(1)).save(bill); // Ensure that the bill is still saved once
    }

    @Test
    public void testApplyExcessiveDiscount() {
        // Arrange
        Customer customer = new Customer("Excessive Discount", "0723456789", "excessive.discount@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1000), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);

        // Act
        billingManager.applyDiscount(bill, 1.5); // Excessive 150% discount

        // Assert
        assertEquals(BigDecimal.valueOf(1000), bill.getTotalPrice()); // Total price stays the same
        assertEquals(BigDecimal.ZERO, bill.getDiscountAmount()); // Discount should not exceed the total price
        assertEquals(BigDecimal.valueOf(1000), bill.getFinalPrice()); // Final price should be unchanged
        Mockito.verify(billRepository, times(1)).save(bill); // Ensure that the bill is saved once
    }
}
