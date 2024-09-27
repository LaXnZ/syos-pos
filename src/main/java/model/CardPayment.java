package model;

public class CardPayment extends Payment {
    private String cardNumber;
    private String cardType;

    // Constructor, Getters, and Setters
    public CardPayment(int paymentId, int billId, double paymentAmount, String cardNumber, String cardType) {
        super(paymentId, billId, "Card", paymentAmount);
        this.cardNumber = cardNumber;
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
