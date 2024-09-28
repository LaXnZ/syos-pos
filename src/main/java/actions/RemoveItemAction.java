package actions;

import business.billing.BillingManager;
import entities.models.Bill;
import entities.models.Item;

public class RemoveItemAction implements Action {
    private final BillingManager billingManager;
    private final Bill bill;
    private final Item item;

    public RemoveItemAction(BillingManager billingManager, Bill bill, Item item) {
        this.billingManager = billingManager;
        this.bill = bill;
        this.item = item;
    }

    @Override
    public void execute() {
        billingManager.removeItemFromBill(bill, item);  // Now it should work since removeItemFromBill is part of the interface
    }
}
