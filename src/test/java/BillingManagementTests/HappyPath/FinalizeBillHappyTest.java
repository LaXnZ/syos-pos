package BillingManagementTests.HappyPath;

import business.billing.BillingManagerImpl;
import entities.models.Bill;
import entities.models.Customer;
import entities.repositories.BillRepository;
import entities.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class FinalizeBillHappyTest {

    private BillingManagerImpl billingManager;
    private BillRepository billRepository;
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        billRepository = Mockito.mock(BillRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        billingManager = new BillingManagerImpl(billRepository, customerRepository, null, null, null, null);
    }

    @Test
    public void testFinalizeBillSuccess() {
        Customer customer = new Customer("David Warner", "0711234567", "david.warner@example.com", LocalDate.now());
        customer.setLoyaltyPoints(500);
        Bill bill = new Bill(LocalDate.now(), BigDecimal.valueOf(1000), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);

        billingManager.finalizeBill(bill, 1500.0, true);

        assertEquals(BigDecimal.valueOf(0), customer.getLoyaltyPoints());
        assertEquals(BigDecimal.valueOf(500), bill.getFinalPrice());
        assertEquals(BigDecimal.valueOf(1000), bill.getCashTendered());
        assertEquals(BigDecimal.valueOf(500), bill.getChangeAmount());

        Mockito.verify(customerRepository, Mockito.times(1)).update(customer);
        Mockito.verify(billRepository, Mockito.times(1)).save(bill);
    }
}
