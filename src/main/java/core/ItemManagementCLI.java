package core;

import business.item.ItemManager;
import entities.models.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ItemManagementCLI {

    public static void handleItemManagement(ItemManager itemManager, Scanner scanner) {
        boolean running = true;


        while (running) {
            System.out.println("\n==== Item Management ====");
            System.out.println("1. Add Item");
            System.out.println("2. Find Item by Code");
            System.out.println("3. Update Item");
            System.out.println("4. Remove Item");
            System.out.println("5. View All Items");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // add a new item
                    System.out.println("Enter item code:");
                    String itemCode = scanner.nextLine();

                    System.out.println("Enter item name:");
                    String itemName = scanner.nextLine();

                    System.out.println("Enter item price:");
                    BigDecimal itemPrice = scanner.nextBigDecimal();
                    scanner.nextLine();  // Consume newline

                    // create the item object and add it to the repository
                    Item newItem = new Item(itemCode, itemName, itemPrice);
                    itemManager.addItem(newItem);
                    System.out.println("Item added successfully!");
                    break;

                case 2:
                    // find item by code
                    System.out.println("Enter item code:");
                    String searchItemCode = scanner.nextLine();
                    Item foundItem = itemManager.findByCode(searchItemCode);

                    if (foundItem != null) {
                        System.out.println("Item found: " + foundItem); // Will now use toString()
                    } else {
                        System.out.println("Item not found with code: " + searchItemCode);
                    }
                    break;

                case 3:
                    // update item
                    System.out.println("Enter item code to update:");
                    String updateItemCode = scanner.nextLine();

                    Item itemToUpdate = itemManager.findByCode(updateItemCode);
                    if (itemToUpdate != null) {
                        System.out.println("Enter new item name:");
                        String updatedName = scanner.nextLine();

                        System.out.println("Enter new item price:");
                        BigDecimal updatedPrice = scanner.nextBigDecimal();
                        scanner.nextLine();  // Consume newline

                        itemToUpdate.setItemName(updatedName);
                        itemToUpdate.setItemPrice(updatedPrice);
                        itemManager.updateItem(itemToUpdate);
                    } else {
                        System.out.println("Item not found with code: " + updateItemCode);
                    }
                    break;

                case 4:
                    // remove item
                    System.out.println("Enter item code to remove:");
                    String removeItemCode = scanner.nextLine();
                    itemManager.removeItem(removeItemCode);
                    System.out.println("Item removed successfully!");
                    break;

                case 5:
                    // view all items
                    List<Item> allItems = itemManager.getAllItems();
                    if (allItems.isEmpty()) {
                        System.out.println("No items found.");
                    } else {
                        System.out.println("\n==== All Items ====");
                        for (Item item : allItems) {
                            System.out.println("Code: " + item.getItemCode() + ", Name: " + item.getItemName() + ", Price: " + item.getItemPrice());
                        }
                    }
                    break;

                case 6:
                    // main menu
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }
}
