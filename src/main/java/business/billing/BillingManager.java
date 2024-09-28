package business.billing;

import entities.models.Bill;
import entities.models.Item;
import entities.models.Customer;

public interface BillingManager {
    Bill createBill(Customer customer);
    void addItemToBill(Bill bill, Item item, int quantity);
    void applyDiscount(Bill bill, double discountRate);
    void finalizeBill(Bill bill, double cashTendered);
}
