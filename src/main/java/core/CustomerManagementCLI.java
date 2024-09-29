package core;

import business.customer.CustomerManager;
import entities.models.Customer;

import java.time.LocalDate;
import java.util.Scanner;

public class CustomerManagementCLI {

    public static void handleCustomerManagement(CustomerManager customerManager, Scanner scanner) {
        boolean running = true;

        // Menu-driven approach for Customer Management
        while (running) {
            System.out.println("\n==== Customer Management ====");
            System.out.println("1. Add Customer");
            System.out.println("2. Find Customer by ID");
            System.out.println("3. Update Customer");
            System.out.println("4. Remove Customer");
            System.out.println("5. View All Customers");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    // Add Customer
                    System.out.println("Enter customer name:");
                    String customerName = scanner.nextLine();

                    System.out.println("Enter phone number:");
                    String phoneNumber = scanner.nextLine();

                    System.out.println("Enter email:");
                    String email = scanner.nextLine();

                    // Create new customer and save to the database
                    Customer newCustomer = new Customer(customerName, phoneNumber, email, LocalDate.now());
                    customerManager.addCustomer(newCustomer);
                    System.out.println("Customer added successfully!");
                    break;

                case 2:
                    // Find Customer by ID
                    System.out.println("Enter customer ID:");
                    int customerId = scanner.nextInt();
                    Customer foundCustomer = customerManager.findCustomerById(customerId);

                    if (foundCustomer != null) {
                        System.out.println("Customer found: " + foundCustomer);
                    } else {
                        System.out.println("Customer not found with ID: " + customerId);
                    }
                    break;

                case 3:
                    // Update Customer
                    System.out.println("Enter customer ID to update:");
                    int updateCustomerId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    Customer customerToUpdate = customerManager.findCustomerById(updateCustomerId);
                    if (customerToUpdate != null) {
                        System.out.println("Enter new customer name:");
                        String updatedName = scanner.nextLine();

                        System.out.println("Enter new phone number:");
                        String updatedPhone = scanner.nextLine();

                        System.out.println("Enter new email:");
                        String updatedEmail = scanner.nextLine();

                        // Update customer details
                        customerToUpdate.setName(updatedName);
                        customerToUpdate.setPhoneNumber(updatedPhone);
                        customerToUpdate.setEmail(updatedEmail);
                        customerManager.updateCustomer(customerToUpdate);
                        System.out.println("Customer updated successfully!");
                    } else {
                        System.out.println("Customer not found with ID: " + updateCustomerId);
                    }
                    break;

                case 4:
                    // Remove Customer
                    System.out.println("Enter customer ID to remove:");
                    int removeCustomerId = scanner.nextInt();
                    customerManager.removeCustomer(removeCustomerId);
                    System.out.println("Customer removed successfully!");
                    break;

                case 5:
                    // View All Customers
                    System.out.println("\n==== All Customers ====");
                    customerManager.findAllCustomers().forEach(System.out::println);  // Uses overridden toString() in Customer class
                    break;

                case 6:
                    // Back to Main Menu
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }
}
