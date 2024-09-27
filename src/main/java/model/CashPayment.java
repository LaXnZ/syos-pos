package model;

public class CashPayment extends Payment {
    private double cashReceived;
    private double cashChange;

    // Constructor, Getters, and Setters
    public CashPayment(int paymentId, int billId, double paymentAmount, double cashReceived, double cashChange) {
        super(paymentId, billId, "Cash", paymentAmount);
        this.cashReceived = cashReceived;
        this.cashChange = cashChange;
    }

    public double getCashReceived() {
        return cashReceived;
    }

    public void setCashReceived(double cashReceived) {
        this.cashReceived = cashReceived;
    }

    public double getCashChange() {
        return cashChange;
    }

    public void setCashChange(double cashChange) {
        this.cashChange = cashChange;
    }
}
