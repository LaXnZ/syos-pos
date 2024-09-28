package business.billing;

import models.Bill;

public class PaymentProcessor {

    public static void processPayment(String paymentType, double amount, Bill bill) {
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

    private static void processCashPayment(double amount, Bill bill) {
        double change = amount - bill.getFinalPrice();
        bill.setPaymentType("cash");
        bill.setPaymentAmount(amount);
        System.out.println("Cash payment processed. Change: " + change);
    }
}
