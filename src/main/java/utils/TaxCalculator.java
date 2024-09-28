package utils;

public class TaxCalculator {
    private static final double TAX_RATE = 0.10; // 10% tax

    public double calculateTax(double totalPrice) {
        return totalPrice * TAX_RATE;
    }
}