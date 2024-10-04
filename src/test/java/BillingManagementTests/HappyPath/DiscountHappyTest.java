package BillingManagementTests.HappyPath;

import business.billing.BillingManagerImpl;
import entities.models.Bill;
import entities.models.Customer;
import entities.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class DiscountHappyTest {

    private BillingManagerImpl billingManager;
    private BillRepository billRepository;

    @BeforeEach
    public void setUp() {
        billRepository = Mockito.mock(BillRepository.class);
        billingManager = new BillingManagerImpl(billRepository, null, null, null, null, null);
    }

    @Test
    public void testApplyDiscount() {
        Customer customer = new Customer("Jane Doe", "0712345679", "jane.doe@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1000), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);

        billingManager.applyDiscount(bill, 10.0); // 10% discount

        assertEquals(BigDecimal.valueOf(100.0), bill.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(900.0), bill.getFinalPrice());
        Mockito.verify(billRepository, Mockito.times(1)).save(bill);
    }
}
