package strategies.payment;

import java.math.BigDecimal;

public class CashPayment {
    private BigDecimal finalPrice;
    private BigDecimal cashTendered;

    public CashPayment(BigDecimal finalPrice, BigDecimal cashTendered) {
        this.finalPrice = finalPrice;
        this.cashTendered = cashTendered;
    }

    public BigDecimal getChange() {
        return cashTendered.subtract(finalPrice);
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public BigDecimal getCashTendered() {
        return cashTendered;
    }
}
