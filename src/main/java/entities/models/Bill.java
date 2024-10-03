package entities.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Bill {
    private int billId;
    private LocalDate billDate;
    private BigDecimal totalPrice;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal finalPrice;
    private BigDecimal cashTendered;
    private BigDecimal changeAmount;
    private Customer customer;
    private String paymentType;          // Add paymentType field
    private BigDecimal paymentAmount;    // Add paymentAmount field
    private double discountRate;         // Add discountRate field

    // Constructors
    public Bill() {
    }

    public Bill(LocalDate billDate, BigDecimal totalPrice, BigDecimal discountAmount, BigDecimal taxAmount, BigDecimal finalPrice, BigDecimal cashTendered, BigDecimal changeAmount, Customer customer) {
        this.billDate = billDate;
        this.totalPrice = totalPrice;
        this.discountAmount = discountAmount;
        this.taxAmount = taxAmount;
        this.finalPrice = finalPrice;
        this.cashTendered = cashTendered;
        this.changeAmount = changeAmount;
        this.customer = customer;
    }


    // Getters and Setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public BigDecimal getCashTendered() {
        return cashTendered;
    }

    public void setCashTendered(BigDecimal cashTendered) {
        this.cashTendered = cashTendered;
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public double getDiscountRate() {
        return discountRate;  // Getter for discountRate
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;  // Setter for discountRate
    }
}
