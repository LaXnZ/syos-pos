package actions;

import business.billing.BillingManager;
import entities.models.Bill;
import entities.models.Item;

public class AddItemAction implements Action {
    private final BillingManager billingManager;
    private final Bill bill;
    private final Item item;
    private final int quantity;

    public AddItemAction(BillingManager billingManager, Bill bill, Item item, int quantity) {
        this.billingManager = billingManager;
        this.bill = bill;
        this.item = item;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        billingManager.addItemToBill(bill, item, quantity);
    }
}
