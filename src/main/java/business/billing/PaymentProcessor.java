package business.billing;

import entities.models.Bill;

import java.math.BigDecimal;

public class PaymentProcessor {

    public static void processPayment(String paymentType, BigDecimal amount, Bill bill) {
        switch (paymentType.toLowerCase()) {
            case "cash":
                processCashPayment(amount, bill);
                break;
            case "card":
                // Future card payment handling can be added here
                System.out.println("Processing card payment...");
                break;
            default:
                System.out.println("Unsupported payment type: " + paymentType);
        }
    }

    private static void processCashPayment(BigDecimal amount, Bill bill) {
        BigDecimal change = amount.subtract(bill.getFinalPrice());
        bill.setPaymentType("cash");
        bill.setPaymentAmount(amount);
        System.out.println("Cash payment processed. Change: " + change);
    }
}
