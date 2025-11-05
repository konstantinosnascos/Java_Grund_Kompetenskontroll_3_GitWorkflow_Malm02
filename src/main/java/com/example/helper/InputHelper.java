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


    /**
     * Läser in en sträng från användaren som är valfri.
     * Om användaren bara trycker Enter returneras en tom sträng.
     * Används t.ex. vid redigering där användaren kan välja att behålla nuvarande värde.
     */
    public String getOptionalString(String prompt) {
        System.out.print(prompt);
        return scan.nextLine().trim();
    }

    /**
     * Läser in ett heltal från användaren som är valfritt.
     * Om användaren bara trycker Enter returneras -1 som signal att inget nytt värde angavs.
     * Används t.ex. vid redigering där användaren kan hoppa över fält.
     */
    public int getOptionalInt(String prompt) {
        System.out.print(prompt);
        String input = scan.nextLine().trim();
        if (input.isBlank()) return -1;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Felaktigt tal. Inget ändrat.");
            logger.warn("Ogiltig siffra i getOptionalInt(): {}", input);
            return -1;
        }
    }

    /**
     * Läser in ett valfritt datum och tid från användaren i formatet yyyy-MM-dd HH:mm.
     * Om användaren trycker Enter returneras null, vilket betyder att det gamla värdet ska behållas.
     */
    public LocalDateTime getOptionalDateTime(String prompt) {
        System.out.print(prompt);
        String input = scan.nextLine().trim();

        if (input.isBlank()) {
            return null; // Användaren vill behålla nuvarande datum
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            return LocalDateTime.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("❌ Fel format. Använd: yyyy-MM-dd HH:mm (t.ex. 2025-11-06 09:30)");
            logger.warn("Ogiltigt datumformat: {}", input);
            return null;
        }
    }



}