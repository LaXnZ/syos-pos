package entities.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Customer {
    private int customerId;
    private String name;
    private String phoneNumber;
    private String email;
    private int loyaltyPoints;
    private BigDecimal totalSpent;
    private LocalDate lastPurchaseDate;

    // Default constructor
    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Phone: " + phoneNumber +
                ", Email: " + email + ", Loyalty Points: " + loyaltyPoints +
                ", Total Spent: " + totalSpent + ", Last Purchase Date: " + lastPurchaseDate;
    }

    // Full constructor with all attributes
    public Customer(String name, String phoneNumber, String email, int loyaltyPoints, BigDecimal totalSpent,
                    LocalDate lastPurchaseDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.loyaltyPoints = loyaltyPoints;
        this.totalSpent = totalSpent;
        this.lastPurchaseDate = lastPurchaseDate;
    }

    // New constructor with default values for loyaltyPoints and totalSpent
    public Customer(String name, String phoneNumber, String email, LocalDate lastPurchaseDate) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.loyaltyPoints = 0; // Default loyalty points
        this.totalSpent = BigDecimal.ZERO; // Default total spent
        this.lastPurchaseDate = lastPurchaseDate;
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(LocalDate lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }
}
