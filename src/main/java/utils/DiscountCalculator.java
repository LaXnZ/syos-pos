package utils;

public class DiscountCalculator {
    public double calculateDiscount(double totalPrice, double discountRate) {
        return totalPrice * (discountRate / 100);
    }
}