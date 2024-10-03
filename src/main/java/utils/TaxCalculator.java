package utils;

public class TaxCalculator {
    private static final double TAX_RATE = 0.02;

    public double calculateTax(double totalPrice) {
        return totalPrice * TAX_RATE;
    }
}
