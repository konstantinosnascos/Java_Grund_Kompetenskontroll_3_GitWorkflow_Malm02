package com.example.helper;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputHelper {

    private final Scanner scan;
    private static final Logger logger = LoggerFactory.getLogger(InputHelper.class);

    public InputHelper(Scanner scan) {
        this.scan = scan;
    }

    public int getInt(String prompt) {
        try {
            while (true) {
                System.out.print(prompt);
                if (scan.hasNextInt()) {
                    int value = scan.nextInt();
                    scan.nextLine();
                    return value;
                } else {
                    System.out.println("Fel input. Skriv ett heltal tack.");
                    logger.warn("Ogiltig inmatning – användaren skrev inte ett heltal.");
                    scan.next();
                }
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("Ett fel uppstod vid inläsning av tal: " + e.getMessage());
            logger.error("Fel i getInt(): ", e);
            return -1;
        }
    }

    public double getDouble(String prompt) {
        try {
            while (true) {
                System.out.print(prompt);
                try {
                    double value = scan.nextDouble();
                    scan.nextLine();
                    return value;
                } catch (InputMismatchException e) {
                    System.out.println("Fel input. Skriv ett nummer tack (använd komma för decimaltal).");
                    logger.warn("Ogiltig inmatning av double: {}", e.getMessage());
                    scan.next();
                }
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("Ett fel uppstod vid inläsning av nummer: " + e.getMessage());
            logger.error("Fel i getDouble(): ", e);
            return 0.0;
        }
    }

    public String getString(String prompt) {
        try {
            while (true) {
                System.out.print(prompt);
                String input = scan.nextLine().trim();
                if (!input.isEmpty()) {
                    return input;
                }
                System.out.println("Inmatningen får inte vara tom. Försök igen.");
                logger.warn("Användaren skrev en tom sträng som input.");
            }
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.println("Ett fel uppstod vid inläsning av text: " + e.getMessage());
            logger.error("Fel i getString(): ", e);
            return "";
        }
    }


}