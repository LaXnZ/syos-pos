package business.billing;

import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;

public interface BillingManager {
    Bill createBill(Customer customer);
    void addItemToBill(Bill bill, Item item, int quantity);
    void applyDiscount(Bill bill, double discountRate);
    void finalizeBill(Bill bill, double cashTendered);
    Bill getBillById(int billId);
    void removeItemFromBill(Bill bill, Item item);
}
