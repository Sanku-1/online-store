
package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Store {

    public static void main(String[] args) {

        try {
            // Create lists for inventory and the shopping cart
            ArrayList<Product> inventory = new ArrayList<>();
            ArrayList<Product> cart = new ArrayList<>();

            // Load inventory from the data file (pipe-delimited: id|name|price)
            loadInventory("products.csv", inventory);

            // Main menu loop
            Scanner scanner = new Scanner(System.in);
            int choice = -1;
            while (choice != 3) {
                System.out.println("\nWelcome to the Online Store!");
                System.out.println("1. Show Products");
                System.out.println("2. Show Cart");
                System.out.println("3. Exit");
                System.out.print("Your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Please enter 1, 2, or 3.");
                    scanner.nextLine();                 // discard bad input
                    continue;
                }
                choice = scanner.nextInt();
                scanner.nextLine();                     // clear newline

                switch (choice) {
                    case 1 -> displayProducts(inventory, cart, scanner);
                    case 2 -> displayCart(cart, scanner);
                    case 3 -> System.out.println("Thank you for shopping with us!");
                    default -> System.out.println("Invalid choice!");
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    /**
     * Reads product data from a file and populates the inventory list.
     * File format (pipe-delimited):
     * id|name|price
     * <p>
     * Example line:
     * A17|Wireless Mouse|19.99
     */
    public static void loadInventory(String fileName, ArrayList<Product> inventory) throws IOException {
        BufferedReader bufReader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = bufReader.readLine()) != null) {
            String[] tokens = line.split("\\|");
            String productId = tokens[0];
            String productName = tokens[1];
            double productPrice = Double.parseDouble(tokens[2]);
            Product product = new Product(productId, productName, productPrice);
            inventory.add(product);
        }
        bufReader.close();
    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        for (Product product : inventory) {
            System.out.println(product.toString());
        }
        System.out.println("Please enter the id of the product you would like to add to cart:");
        String userProductIdInput = scanner.nextLine();
        for (Product product : inventory) {
            if (userProductIdInput.equalsIgnoreCase(product.getId())) {
                cart.add(product);
            }
        }
    }

    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        double cartTotalPrice = 0;
        for (Product product : cart) {
            System.out.println(product.toString());
            cartTotalPrice += product.getPrice();
        }
        System.out.printf("Total Cost: $%.2f", cartTotalPrice);

        String command = "";
        boolean isDone = false;
        while (!isDone) {
            System.out.println("\nShopping Cart Options:");
            System.out.println("C. Check Out");
            System.out.println("X. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextLine()) {
                System.out.println("Please enter C or X.");
                continue;
            }

            command = scanner.nextLine();

            switch (command) {
                case "C" -> checkOut(cart, cartTotalPrice, scanner);
                case "X" -> isDone = true;
                default -> System.out.println("Invalid choice!");

            }
        }
    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {

        // TODO: implement steps listed above
        String checkOutCommand = "";
        boolean isDone = false;
        while (!isDone) {
            System.out.println("\nPlease confirm that you would like to make this purchase:");
            System.out.println("Y. Check Out");
            System.out.println("N. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextLine()) {
                System.out.println("Please enter Y or N.");
                continue;
            }

            checkOutCommand = scanner.nextLine();

            switch (checkOutCommand) {
                case "Y":
                    System.out.println("Please enter your payment amount:");
                    double userPaymentInput = scanner.nextDouble();
                    scanner.nextLine();                 // discard bad input
                    if (userPaymentInput >= totalAmount) {
                        double change = totalAmount - userPaymentInput;
                        System.out.println("\nReceipt:");
                        for (Product product : cart) {
                            System.out.println(product.toString());
                        }
                        System.out.printf("Order Total: $%.2f", totalAmount);
                        System.out.printf("\nPayment Received: $%.2f", userPaymentInput);
                        System.out.printf("\nChange: $%.2f", -change);
                        cart.clear();
                        isDone = true;


                    } else {
                        System.out.println("You haven't provided the necessary amount to complete your purchase, please try again.");
                        break;
                    }
                case "N":
                    isDone = true;
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }
}