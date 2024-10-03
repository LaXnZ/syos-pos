package strategies.payment;

import java.math.BigDecimal;

public class PaymentHandler {
    public BigDecimal processPayment(CashPayment cashPayment) {

        return cashPayment.getCashTendered().subtract(cashPayment.getFinalPrice());
    }
}
