package business.billing;

import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;

public interface BillingManager {
    Bill createBill(Customer customer);
    void addItemToBill(Bill bill, Item item, int quantity);
    void applyDiscount(Bill bill, double discountRate);
    void finalizeBill(Bill bill, double cashTendered);

    // Ensure the method signature matches here
    Bill getBillById(int billId);  // <-- This is the method that must be in the interface
    void removeItemFromBill(Bill bill, Item item);
}
