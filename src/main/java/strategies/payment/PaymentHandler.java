package strategies.payment;

import java.math.BigDecimal;

public class PaymentHandler {
    public BigDecimal processPayment(CashPayment cashPayment) {
        // Use the correct method names from CashPayment
        return cashPayment.getCashTendered().subtract(cashPayment.getFinalPrice());
    }
}
