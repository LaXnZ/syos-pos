package business.billing;

import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;
import entities.models.Transaction;
import java.util.List;

public interface BillingManager {
    Bill createBill(Customer customer);
    void addItemToBill(Bill bill, Item item, int quantity);
    void applyDiscount(Bill bill, double discountRate);
    void finalizeBill(Bill bill, double cashTendered,boolean useLoyaltyPoints);
    List<Transaction> getTransactionsByBillId(int billId);

}
