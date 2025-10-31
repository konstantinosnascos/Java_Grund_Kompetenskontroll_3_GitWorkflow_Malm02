package com.example.menu;

import com.example.helper.InputHelper;
import com.example.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerMenu {

    private final InputHelper input;
    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerMenu.class);

    public CustomerMenu(InputHelper input, CustomerService customerService) {
        this.input = input;
        this.customerService = customerService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = input.getInt("Välj ett alternativ: ");
            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> showCustomer();
                case 3 -> running = false;
                default -> System.out.println("Felaktigt val, försök igen!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Kundhantering ---");
        System.out.println("1. Lägg till kund");
        System.out.println("2. Visa kund");
        System.out.println("3. Gå tillbaka");
    }

    private void addCustomer() {
        System.out.println("\n--- Lägg till kund ---");
        String id = input.getString("Kund-ID: ");
        String name = input.getString("Namn: ");
        String phone = input.getString("Telefon: ");
        String email = input.getString("E-post: ");

        customerService.addCustomer(id, name, phone, email);
        System.out.println("Kund tillagd: " + name);
        logger.info("Ny kund tillagd: {} (ID: {})", name, id);
    }

    private void showCustomer() {
        String id = input.getString("Ange kund-ID: ");
        var customer = customerService.getCustomerById(id);
        if (customer != null) {
            System.out.println(customer);
            logger.info("Visade kund med ID: {}", id);
        } else {
            System.out.println("Kund hittades inte.");
            logger.warn("Försökte visa kund med ID: {} – hittades inte.", id);
        }
    }
}