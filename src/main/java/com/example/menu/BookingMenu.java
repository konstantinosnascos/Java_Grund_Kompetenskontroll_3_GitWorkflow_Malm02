package com.example.menu;
import com.example.helper.EmailValidator;
import com.example.helper.RegistrationValidator;
import com.example.helper.InputHelper;
import com.example.model.Booking;
import com.example.model.Customer;
import com.example.model.Vehicle;
import com.example.model.ServiceType;
import com.example.repository.BookingRepository;
import com.example.service.BookingService;
import com.example.exception.BookingConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

public class BookingMenu {

    private final InputHelper input;
    private final RegistrationValidator validator = new RegistrationValidator();
    private final EmailValidator emailValidator = new EmailValidator();
    private final BookingService bookingService;
    private static final Logger logger = LoggerFactory.getLogger(BookingMenu.class);
    LocalDateTime startTime = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0);
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("EEEE d MMM yyyy 'kl.' HH:mm");


    public BookingMenu(InputHelper input, BookingService bookingService) {
        this.input = input;
        this.bookingService = bookingService;
    }

    public void run() {
        boolean running = true;
        while (running) {
            try {
                printMenu();
                int choice = input.getInt("Välj ett alternativ: ");
                switch (choice) {
                    case 1 -> showCreateBooking();
                    case 2 -> showAllBookings();
                    case 3 -> cancelBooking();
                    case 4 -> editBooking();
                    case 5 -> running = false;
                    default -> System.out.println("Felaktigt val, försök igen!");
                }
            } catch (Exception e) {
                System.out.println("Ett oväntat fel uppstod i bokningsmenyn: " + e.getMessage());
                logger.error("Fel i BookingMenu.run()", e);
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Bokningsmeny ---");
        System.out.println("1. Skapa ny bokning");
        System.out.println("2. Visa alla bokningar");
        System.out.println("3. Avboka");
        System.out.println("4. Redigera bokning");
        System.out.println("5. Gå tillbaka till huvudmenyn");
    }

    private void showCreateBooking()
    {


        System.out.println("\n--- Skapa ny bokning ---");
        String name = input.getString("Kundens namn & efternamn:  ");
        String email = input.getString("Kundens e-postadress: ");
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);

        System.out.println("\nAnge fordonsuppgifter:");
        String regNum;

        do{
            regNum = input.getString("Fordonets registreringsnummer (t.ex. ABC12A): ");
                if (!validator.isValid(regNum)){
                System.out.println("Felaktig format. Försök igen! (format: AAA-99(A/9))");

            }
        } while (!validator.isValid(regNum));

        String model = input.getString("Bilmodel: ");
        int year = input.getInt("Årsmodell: ");

        Vehicle vehicle = new Vehicle(regNum, model, year);
        System.out.println("\n Fordon tillagt: " + vehicle);

        System.out.println("\nVälj typ av service:");
        System.out.println("1. Service");
        System.out.println("2. Reparation");
        System.out.println("3. Besiktning");
        int typeChoice = input.getInt("Ditt val (1-3): ");

        ServiceType serviceType = switch (typeChoice)
        {
            case 1 -> ServiceType.SERVICE;
            case 2 -> ServiceType.REPARATION;
            case 3 -> ServiceType.BESIKTNING;
            default -> ServiceType.SERVICE;
        };

        System.out.println("Du valde" + serviceType);

        showAvailableBookings();
        String bookTime = input.getString("Bokningstid: ");

        // Hämta vald tid från repository via koden användaren skrev
        LocalDateTime chosenTime = bookingService.getAvailableTimes().get(bookTime);

        // Om koden inte finns
        if (chosenTime == null ) {
            System.out.println("Ogiltig kod. Ingen bokning skapad.");
            return;
        }

        // Om användaren vill avbryta sin bokning
        String confirm = input.getString("Vill du skapa denna bokning [(y)es/(n)o]:").trim().toLowerCase();
        if (confirm.equals("n") || confirm.equals("no"))
        {
            logger.info("Användaren valde att avbryta sin bokning");
            return;
        }

        try {
// Skapa bokningen och hämta tillbaka den
            Booking newBooking = bookingService.createBooking(customer, vehicle, chosenTime, serviceType);

            System.out.println("\n✅ Bokning skapad!");
            System.out.println("--------------------------------------");
            System.out.printf("Boknings-ID:  %d%n", newBooking.getId());
            System.out.printf("Kund:         %s (%s)%n",
                    newBooking.getCustomer().getName(),
                    newBooking.getCustomer().getEmail());
            System.out.printf("Fordon:       %s (%s, %d)%n",
                    newBooking.getVehicle().getModel(),
                    newBooking.getVehicle().getRegNum(),
                    newBooking.getVehicle().getYear());
            System.out.printf("Datum:        %s%n", chosenTime.format(FORMATTER));
            System.out.printf("Typ:          %s%n", newBooking.getServiceType());
            System.out.printf("Pris:         %.2f kr%n", newBooking.getPrice());
            System.out.printf("Status:       %s%n", newBooking.isCompleted() ? "Klar" : "Bokad");
            System.out.println("--------------------------------------");



            logger.info("Ny bokning skapad för kund: {} vid tid: {}", name, chosenTime.format(FORMATTER));
;

        } catch (BookingConflictException e) {
            System.out.println("Kan inte boka: " + e.getMessage());
            logger.warn("Dubbelbokning försökte skapas vid tidkod {}", bookTime);
        } catch (Exception e) {
            System.out.println("Ett oväntat fel uppstod: " + e.getMessage());
            logger.error("Fel vid bokning med kund {} och fordon {}", name, regNum, e);
        }
    }

    private void showAllBookings() {
        System.out.println("\n--- Alla bokningar ---");
        var bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("Inga bokningar finns.");
            logger.info("Inga bokningar hittades.");
            return;
        }
        bookings.forEach(System.out::println);
    }

    private void showAvailableBookings()
    {
        System.out.println("\n---> Tillgängliga tider <---");

        var times = bookingService.getAvailableTimes();
        
        times.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry-> System.out.printf("%s → %s%n ",
                        entry.getKey(),
                        entry.getValue().format(FORMATTER)));


    }

    private void cancelBooking() {
        System.out.println("\n--- Avboka ---");
        String bookingId = input.getString("Ange boknings-ID för att avboka: ");
        boolean removed = bookingService.cancelBooking(bookingId);

        if (removed) {
            System.out.println("Bokning avbokad.");
            logger.info("Bokning med ID {} avbokad.", bookingId);
        } else {
            System.out.println("Bokning hittades inte.");
            logger.warn("Försökte avboka ID {} men hittades inte.", bookingId);
        }
    }

    private void editBooking() {
        System.out.println("\n--- Redigera bokning ---");
        int bookingId = input.getInt("Ange boknings-ID att redigera: ");
        Booking existing = bookingService.getBookingById(bookingId);

        if (existing == null) {
            System.out.println("❌ Ingen bokning med ID " + bookingId + " hittades.");
            logger.warn("Försök att redigera bokning med ogiltigt ID: {}", bookingId);
            return;
        }

        System.out.println("Nuvarande bokning:");
        System.out.println(existing);

        // Kunduppgifter – namn och e-post
        String newName = input.getOptionalString("Nytt namn (" + existing.getCustomer().getName() + "): ");
        String newEmail = input.getOptionalString("Ny e-post (" + existing.getCustomer().getEmail() + "): ");
        if (!newEmail.isBlank() && !emailValidator.isValid(newEmail)) {
            System.out.println("Ogiltig e-postadress. Ändring ignoreras.");
            newEmail = existing.getCustomer().getEmail();
        }

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName(newName.isBlank() ? existing.getCustomer().getName() : newName);
        updatedCustomer.setEmail(newEmail.isBlank() ? existing.getCustomer().getEmail() : newEmail);

        // Fordonsuppgifter
        String newModel = input.getOptionalString("Ny bilmodell (" + existing.getVehicle().getModel() + "): ");
        String newReg = input.getOptionalString("Nytt registreringsnummer (" + existing.getVehicle().getRegNum() + "): ");
        if (!newReg.isBlank() && !validator.isValid(newReg)) {
            System.out.println("Ogiltigt registreringsnummer. Ändring ignoreras.");
            newReg = existing.getVehicle().getRegNum();
        }

        int newYear = input.getOptionalInt("Ny årsmodell (" + existing.getVehicle().getYear() + "): ");

        Vehicle updatedVehicle = new Vehicle(
                newReg.isBlank() ? existing.getVehicle().getRegNum() : newReg,
                newModel.isBlank() ? existing.getVehicle().getModel() : newModel,
                newYear == -1 ? existing.getVehicle().getYear() : newYear
        );

        // Datum och tid
        LocalDateTime currentDateTime = existing.getDate().atTime(9, 0);
        LocalDateTime newDateTime = input.getOptionalDateTime(
                "Nytt datum och tid (yyyy-MM-dd HH:mm) [" + currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "]: "
        );
        LocalDateTime finalDateTime = (newDateTime == null) ? currentDateTime : newDateTime;

        // Tjänstetyp
        System.out.println("Välj ny tjänstetyp:");
        System.out.println("1. Service");
        System.out.println("2. Reparation");
        System.out.println("3. Besiktning");
        int typeChoice = input.getOptionalInt("Ditt val (1-3): ");
        ServiceType newType = switch (typeChoice) {
            case 1 -> ServiceType.SERVICE;
            case 2 -> ServiceType.REPARATION;
            case 3 -> ServiceType.BESIKTNING;
            default -> existing.getServiceType();
        };

        // Status
        System.out.println("Ändra status:");
        System.out.println("1. Inte klar");
        System.out.println("2. Klar");
        int statusChoice = input.getOptionalInt("Ditt val (1-2): ");
        boolean newStatus = switch (statusChoice) {
            case 1 -> false;
            case 2 -> true;
            default -> existing.isCompleted(); // Behåll nuvarande status
        };

        // Skapa uppdaterad bokning
        Booking updated = new Booking(
                bookingId,
                updatedCustomer,
                updatedVehicle,
                finalDateTime.toLocalDate(),
                newType,
                existing.getPrice(),
                newStatus
        );

        // Bekräftelse
        String confirm = input.getOptionalString("Vill du spara ändringarna? [(y)es/(n)o]: ").toLowerCase();
        if (confirm.equals("n") || confirm.equals("no")) {
            System.out.println("Ändringar avbröts.");
            logger.info("Användaren avbröt redigering av bokning {}", bookingId);
            return;
        }

        // Spara ändringar
        boolean success = bookingService.editBooking(bookingId, updated);
        if (success) {
            System.out.println("✅ Bokning uppdaterad.");
            logger.info("Bokning med ID {} uppdaterades.", bookingId);
        } else {
            System.out.println("❌ Bokning kunde inte uppdateras.");
            logger.warn("Misslyckades med att uppdatera bokning med ID {}", bookingId);
        }
    }




}