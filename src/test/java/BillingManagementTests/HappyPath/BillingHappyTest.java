package BillingManagementTests.HappyPath;

import business.billing.BillingManagerImpl;
import entities.models.*;
import entities.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class BillingHappyTest {

    private BillingManagerImpl billingManager;
    private BillRepository billRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;
    private ItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        billRepository = Mockito.mock(BillRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        transactionRepository = Mockito.mock(TransactionRepository.class);
        itemRepository = Mockito.mock(ItemRepository.class);

        billingManager = new BillingManagerImpl(billRepository, customerRepository, transactionRepository, itemRepository, null, null);
    }

    @Test
    public void testCreateBillSuccess() {
        Customer customer = new Customer("John Doe", "0712345678", "john.doe@example.com", LocalDate.now());
        Bill createdBill = billingManager.createBill(customer);

        assertEquals(customer, createdBill.getCustomer());
        assertEquals(BigDecimal.ZERO, createdBill.getTotalPrice());
        assertEquals(LocalDate.now(), createdBill.getBillDate());
        Mockito.verify(billRepository, Mockito.times(1)).save(any(Bill.class));
    }

    @Test
    public void testAddItemToBill() {
        // data setup
        Customer customer = new Customer("John Doe", "0712345678", "john.doe@example.com", LocalDate.now());
        Bill bill = new Bill(LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);
        Item item = new Item("TM005", "Taco Night Kit", BigDecimal.valueOf(200));

        // mock behavior
        Mockito.when(itemRepository.findByCode("TM005")).thenReturn(item);

        // action
        billingManager.addItemToBill(bill, item, 2);

        // assertion
        assertEquals(BigDecimal.valueOf(400), bill.getTotalPrice()); // 200 * 2
        Mockito.verify(billRepository, Mockito.times(1)).save(bill);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(any(Transaction.class));
    }
}
