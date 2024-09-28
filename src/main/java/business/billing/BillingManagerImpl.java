package business.billing;

import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;
import entities.repositories.BillRepository;
import entities.repositories.CustomerRepository;
import entities.repositories.BillRepositoryImpl;
import entities.repositories.CustomerRepositoryImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;

public class BillingManagerImpl implements BillingManager {

    private final BillRepository billRepository;
    private final CustomerRepository customerRepository;

    public BillingManagerImpl(Connection connection) {
        this.billRepository = new BillRepositoryImpl(connection);
        this.customerRepository = new CustomerRepositoryImpl(connection);
    }

    @Override
    public Bill createBill(Customer customer) {
        Bill bill = new Bill(LocalDate.now(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, customer);
        billRepository.save(bill);
        return bill;
    }

    @Override
    public void addItemToBill(Bill bill, Item item, int quantity) {
        BigDecimal itemPrice = item.getItemPrice().multiply(BigDecimal.valueOf(quantity));
        bill.setTotalPrice(bill.getTotalPrice().add(itemPrice));
        System.out.println("Item added to the bill: " + item.getItemName() + " - Quantity: " + quantity);

        // Save bill after modification
        billRepository.save(bill);
    }

    @Override
    public void applyDiscount(Bill bill, double discountRate) {
        BigDecimal discountAmount = bill.getTotalPrice().multiply(BigDecimal.valueOf(discountRate));
        bill.setDiscountAmount(discountAmount);
        BigDecimal finalPrice = bill.getTotalPrice().subtract(discountAmount).add(bill.getTaxAmount());
        bill.setFinalPrice(finalPrice);  // Final price after discount is set here
        System.out.println("Discount applied to the bill.");

        // Save bill after modification
        billRepository.save(bill);
    }

    @Override
    public void finalizeBill(Bill bill, double cashTendered) {
        BigDecimal finalPrice = bill.getTotalPrice().subtract(bill.getDiscountAmount()).add(bill.getTaxAmount());
        bill.setFinalPrice(finalPrice);
        bill.setCashTendered(BigDecimal.valueOf(cashTendered));
        BigDecimal changeAmount = BigDecimal.valueOf(cashTendered).subtract(finalPrice);
        bill.setChangeAmount(changeAmount);
        System.out.println("Bill finalized. Change: " + changeAmount);

        // Save the updated values to the database
        billRepository.save(bill);  // Ensure bill is saved after finalization
    }

    @Override
    public Bill getBillById(int billId) {
        return billRepository.findById(billId);
    }

    @Override
    public void removeItemFromBill(Bill bill, Item item) {
        BigDecimal itemPrice = item.getItemPrice();
        bill.setTotalPrice(bill.getTotalPrice().subtract(itemPrice));
        System.out.println("Item removed from the bill: " + item.getItemName());

        // Save bill after modification
        billRepository.save(bill);
    }
}
