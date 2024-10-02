package business.billing;

import entities.repositories.BillRepository;
import entities.repositories.CustomerRepository;
import entities.repositories.TransactionRepository;
import entities.repositories.ItemRepository;
import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;
import entities.models.Transaction;
import utils.DiscountCalculator;
import utils.TaxCalculator;
import strategies.payment.CashPayment;
import strategies.payment.PaymentHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BillingManagerImpl implements BillingManager {

    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final ItemRepository itemRepository;
    private final DiscountCalculator discountCalculator;
    private final TaxCalculator taxCalculator;
    private final PaymentHandler paymentHandler;

    public BillingManagerImpl(BillRepository billRepository,
                              CustomerRepository customerRepository,
                              TransactionRepository transactionRepository,
                              ItemRepository itemRepository) {
        this.billRepository = billRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.itemRepository = itemRepository;
        this.discountCalculator = new DiscountCalculator();
        this.taxCalculator = new TaxCalculator();
        this.paymentHandler = new PaymentHandler();
    }

    @Override
    public Bill createBill(Customer customer) {
        Bill bill = new Bill(LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);
        billRepository.save(bill);
        return bill;
    }

    @Override
    public void addItemToBill(Bill bill, Item item, int quantity) {
        if (item != null && item.getItemId() > 0) {
            BigDecimal itemPrice = item.getItemPrice().multiply(BigDecimal.valueOf(quantity));
            bill.setTotalPrice(bill.getTotalPrice().add(itemPrice));
            System.out.println("Item added to the bill: " + item.getItemName() + " - Quantity: " + quantity);

            Transaction transaction = new Transaction(bill, item, quantity, itemPrice, LocalDate.now(), "purchase");
            transactionRepository.save(transaction);

            billRepository.save(bill);
        } else {
            System.out.println("Error: Item is invalid or has not been added to the database.");
        }
    }

    @Override
    public void applyDiscount(Bill bill, double discountRate) {
        BigDecimal discountAmount = bill.getTotalPrice().multiply(BigDecimal.valueOf(discountRate));
        bill.setDiscountAmount(discountAmount);
        BigDecimal finalPrice = bill.getTotalPrice().subtract(discountAmount).add(bill.getTaxAmount());
        bill.setFinalPrice(finalPrice);
        System.out.println("Discount applied to the bill.");
        billRepository.save(bill);
    }

    @Override
    public void finalizeBill(Bill bill, double cashTendered) {
        // Calculate the tax amount, converting the BigDecimal to double for the tax calculator
        BigDecimal taxAmount = BigDecimal.valueOf(taxCalculator.calculateTax(bill.getTotalPrice().doubleValue()));
        bill.setTaxAmount(taxAmount);

        // Calculate final price after applying discount and adding tax
        BigDecimal finalPrice = bill.getTotalPrice()
                .subtract(bill.getDiscountAmount())  // Subtract discount
                .add(taxAmount);  // Add tax
        bill.setFinalPrice(finalPrice);

        // Convert the cashTendered to BigDecimal
        BigDecimal cashTenderedBigDecimal = BigDecimal.valueOf(cashTendered);
        bill.setCashTendered(cashTenderedBigDecimal);

        // Create a CashPayment object (assuming CashPayment uses BigDecimal instead of double)
        CashPayment cashPayment = new CashPayment(finalPrice, cashTenderedBigDecimal);

        // Process payment and calculate the change
        BigDecimal changeAmount = paymentHandler.processPayment(cashPayment);  // Assuming processPayment returns a BigDecimal
        bill.setChangeAmount(changeAmount);

        // Display the finalized bill with change
        System.out.println("Bill finalized. Change: " + changeAmount);

        // Save the finalized bill to the database
        billRepository.save(bill);
    }


    @Override
    public Bill getBillById(int billId) {
        return billRepository.findById(billId);
    }

    @Override
    public List<Transaction> getTransactionsByBillId(int billId) {
        return transactionRepository.findByBillId(billId);
    }

    @Override
    public void removeItemFromBill(Bill bill, Item item) {
        if (item != null && item.getItemId() > 0) {
            BigDecimal itemPrice = item.getItemPrice();
            bill.setTotalPrice(bill.getTotalPrice().subtract(itemPrice));
            System.out.println("Item removed from the bill: " + item.getItemName());
            billRepository.save(bill);
        } else {
            System.out.println("Error: Item is invalid or has not been added to the database.");
        }
    }
}
