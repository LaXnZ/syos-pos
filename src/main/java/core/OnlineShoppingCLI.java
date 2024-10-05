package core;

import business.billing.BillingManager;
import business.customer.CustomerManager;
import business.item.ItemManager;
import entities.models.Bill;
import entities.models.Customer;
import entities.models.Item;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class OnlineShoppingCLI {

    public static void handleOnlineShopping(CustomerManager customerManager, ItemManager itemManager,
                                            BillingManager billingManager, Scanner scanner) {
        System.out.println("\n\n==== Online Shopping ====");
        System.out.println("1. Log in");
        System.out.println("2. Register");
        System.out.println("3. Back to Main Menu");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                loginUser(customerManager, itemManager, billingManager, scanner);
                break;
            case 2:
                registerUser(customerManager, itemManager, billingManager, scanner);
                break;
            case 3:
                System.out.println("Returning to the main menu.");
                break;
            default:
                System.out.println("Invalid option! Please try again.");
        }
    }

    private static void loginUser(CustomerManager customerManager, ItemManager itemManager,
                                  BillingManager billingManager, Scanner scanner) {
        System.out.print("\nEnter your email: ");
        String email = scanner.nextLine();

        Customer customer = customerManager.findCustomerByEmail(email);
        if (customer == null) {
            System.out.println("Customer not found. Please register first.");
            handleOnlineShopping(customerManager, itemManager, billingManager, scanner);
        } else {
            System.out.println("\n\nWelcome back, " + customer.getName());
            onlineShoppingProcess(customer, itemManager, billingManager, scanner);
        }
    }

    private static void registerUser(CustomerManager customerManager, ItemManager itemManager,
                                     BillingManager billingManager, Scanner scanner) {
        System.out.println("\nEnter your details to register:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Customer newCustomer = new Customer(name, phoneNumber, email, 0, BigDecimal.ZERO, LocalDate.now());
        customerManager.addCustomer(newCustomer);

        System.out.println("\nRegistration successful! You can now start shopping.");
        onlineShoppingProcess(newCustomer, itemManager, billingManager, scanner);
    }

    private static void onlineShoppingProcess(Customer customer, ItemManager itemManager,
                                              BillingManager billingManager, Scanner scanner) {
        // create the bill for the customer
        Bill bill = billingManager.createBill(customer);

        boolean shopping = true;
        while (shopping) {
            System.out.println("\n\n==== Online Shopping ====");
            System.out.println("1. Add item to cart");
            System.out.println("2. Checkout");
            System.out.println("3. Back to main menu");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter item code: ");
                    String itemCode = scanner.nextLine();
                    Item item = itemManager.findByCode(itemCode);
                    if (item == null) {
                        System.out.println("Item not found. Please try again.");
                    } else {
                        System.out.print("Enter quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();
                        billingManager.addItemToBill(bill, item, quantity);
                        System.out.println("\nItem added to your cart.");
                    }
                    break;
                case 2:
                    checkoutProcess(customer, bill, billingManager, scanner);
                    System.out.println("\nCheckout completed. Thank you for shopping!");
                    shopping = false;
                    break;
                case 3:
                    shopping = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void checkoutProcess(Customer customer, Bill bill, BillingManager billingManager, Scanner scanner) {
        System.out.println("\nTotal Price: " + bill.getTotalPrice());

        // apply loyalty points as discount if available
        if (customer.getLoyaltyPoints() > 0) {
            System.out.println("\nLoyalty Points: " + customer.getLoyaltyPoints());
            System.out.print("Would you like to use loyalty points? (y/n): ");
            String useLoyalty = scanner.nextLine();

            if (useLoyalty.equalsIgnoreCase("y")) {

                BigDecimal loyaltyDiscount = BigDecimal.valueOf(customer.getLoyaltyPoints());
                bill.setDiscountAmount(bill.getDiscountAmount().add(loyaltyDiscount));

                // ensure discount does not exceed total price
                if (bill.getDiscountAmount().compareTo(bill.getTotalPrice()) > 0) {
                    bill.setDiscountAmount(bill.getTotalPrice());
                }

                BigDecimal finalPrice = bill.getTotalPrice().subtract(bill.getDiscountAmount());
                bill.setFinalPrice(finalPrice);
                System.out.println("Discount applied to the bill.");

                // update customer loyalty points
                customer.setLoyaltyPoints(0);
                System.out.println("Loyalty points applied as discount.");
            } else {
                bill.setFinalPrice(bill.getTotalPrice());
            }
        } else {
            bill.setFinalPrice(bill.getTotalPrice());
        }


        System.out.println("\nSelect payment method:");
        System.out.println("1. Card Payment");
        System.out.println("2. Cash on Delivery");
        System.out.print("Choose an option: ");
        int paymentOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (paymentOption == 1) {

            System.out.println("\nPurchase successful!");
            billingManager.finalizeBill(bill, bill.getFinalPrice().doubleValue(), true);  // Finalize the bill with online flag
        } else if (paymentOption == 2) {

            System.out.print("\nEnter delivery address: ");
            String address = scanner.nextLine();

            System.out.println("\nCash on delivery selected. Your items will be delivered to: " + address);
            billingManager.finalizeBill(bill, 0, true);  // Finalize the bill with online flag, payment is collected on delivery
        } else {
            System.out.println("Invalid payment option. Please try again.");
            checkoutProcess(customer, bill, billingManager, scanner);
            return;
        }


        displayFinalBill(bill);
    }

    private static void displayFinalBill(Bill bill) {
        System.out.println("\n\n==== Final Bill ====");
        System.out.println("Bill Serial Number: " + bill.getBillId());
        System.out.println("Bill Date: " + bill.getBillDate());
        System.out.println("Customer Name: " + bill.getCustomer().getName());
        System.out.println("Phone: " + bill.getCustomer().getPhoneNumber());
        System.out.println("Email: " + bill.getCustomer().getEmail());

        System.out.println("\nItems Purchased:");



        System.out.println("\n\nTotal Price: " + bill.getTotalPrice());
        System.out.println("Discount: " + bill.getDiscountAmount());
        System.out.println("Final Price (after discount and tax): " + bill.getFinalPrice());
    }
}
