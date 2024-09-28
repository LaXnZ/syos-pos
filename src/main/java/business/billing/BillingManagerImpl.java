package business.billing;

import entities.models.Bill;
import entities.models.Item;
import entities.models.Customer;
import entities.repositories.BillRepository;
import utils.DiscountCalculator;
import utils.TaxCalculator;

public class BillingManagerImpl implements BillingManager {
    private final BillRepository billRepository;
    private final DiscountCalculator discountCalculator;
    private final TaxCalculator taxCalculator;

    public BillingManagerImpl(BillRepository billRepository, DiscountCalculator discountCalculator, TaxCalculator taxCalculator) {
        this.billRepository = billRepository;
        this.discountCalculator = discountCalculator;
        this.taxCalculator = taxCalculator;
    }

    @Override
    public Bill createBill(Customer customer) {
        return new Bill(customer);
    }

    @Override
    public void addItemToBill(Bill bill, Item item, int quantity) {
        double totalPrice = item.getItemPrice() * quantity;
        bill.addTransaction(item, quantity, totalPrice);
    }

    @Override
    public void applyDiscount(Bill bill, double discountRate) {
        double discount = discountCalculator.calculateDiscount(bill.getTotalPrice(), discountRate);
        bill.setDiscountAmount(discount);
    }

    @Override
    public void finalizeBill(Bill bill, double cashTendered) {
        double taxAmount = taxCalculator.calculateTax(bill.getTotalPrice());
        bill.setTaxAmount(taxAmount);
        bill.calculateFinalPrice();
        bill.setCashTendered(cashTendered);
        bill.setChangeAmount(cashTendered - bill.getFinalPrice());

        // Save bill to the repository
        billRepository.save(bill);
    }
}
