package model;

public class Payment {
    private int paymentId;
    private int billId;  // Foreign key to Bill
    private String paymentType;
    private double paymentAmount;

    // Constructor, Getters, and Setters
    public Payment(int paymentId, int billId, String paymentType, double paymentAmount) {
        this.paymentId = paymentId;
        this.billId = billId;
        this.paymentType = paymentType;
        this.paymentAmount = paymentAmount;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
