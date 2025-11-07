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
import com.example.service.EmailService;
import com.example.exception.BookingConflictException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BookingMenu {

    private final InputHelper input;
    private final RegistrationValidator validator = new RegistrationValidator();
    private final EmailValidator emailValidator = new EmailValidator();
    private final BookingService bookingService;
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(BookingMenu.class);
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("EEEE d MMM yyyy 'kl.' HH:mm");


    public BookingMenu(InputHelper input, BookingService bookingService, EmailService emailService) {
        this.input = input;
        this.bookingService = bookingService;
        this.emailService = emailService;
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
                    case 4 -> completeBooking();
                    case 5 -> editBooking();
                    case 6 -> running = false;
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
        System.out.println("2. Visa bokningar");
        System.out.println("3. Avboka");
        System.out.println("4. Avsluta bokning");
        System.out.println("5. Redigera bokning");
        System.out.println("6. Gå tillbaka till huvudmenyn");
    }

    private void showCreateBooking()
    {
        System.out.println("\n--- Skapa ny bokning ---");
        String name = input.getString("Kundens namn & efternamn:  ");
        String email;
        do {
            email = input.getString("Kundens e-postadress: ");
            if (!emailValidator.isValid(email))
            {
                System.out.println("Fel email format. Måste innehålla @ och domän " +
                        "(email@email.se)");
            }
        } while (!emailValidator.isValid(email));
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

        System.out.println("Du valde " + serviceType);

        //Fråga efter åtgärd om reparation
        String action = null;
        if (serviceType == ServiceType.REPARATION) {
            action = input.getString("Beskriv vad som ska repareras (åtgärd): ");
        }

        showAvailableBookings();
        String bookTime = input.getString("Bokningstid: ").trim().toUpperCase();

        LocalDateTime chosenTime = bookingService.getAvailableTimes().get(bookTime);

        if (chosenTime == null ) {
            System.out.println("Ogiltig kod. Ingen bokning skapad.");
            return;
        }

        String confirm = input.getString("Vill du skapa denna bokning [(y)es/(n)o]:").trim().toLowerCase();
        if (confirm.equals("n") || confirm.equals("no"))
        {
            logger.info("Användaren valde att avbryta sin bokning");
            return;
        }

        try {
            //  Uppdaterad metod med åtgärd
            Booking newBooking = bookingService.createBooking(customer, vehicle, chosenTime, serviceType, bookTime, action);
            System.out.println("\n Bokning skapad!");
            newBooking.printInfo(FORMATTER);

            logger.info("Ny bokning skapad för kund: {} vid tid: {}", name, chosenTime.format(FORMATTER));

            emailService.sendBookingConfirmation(newBooking);

        } catch (BookingConflictException e) {
            System.out.println("Kan inte boka: " + e.getMessage());
            logger.warn("Dubbelbokning försökte skapas vid tidkod {}", bookTime);
        } catch (Exception e) {
            System.out.println("Ett oväntat fel uppstod: " + e.getMessage());
            logger.error("Fel vid bokning med kund {} och fordon {}", name, regNum, e);
        }
    }

    private void showAllBookings()
    {
        System.out.println("\n--- Alternativ ---");
        System.out.println("1. Hitta Bokning");
        System.out.println("2. Visa Bokningar efter Datum");
        System.out.println("3. Visa Bokningar efter Status");
        System.out.println("0. Gå tillbaka");

        List<Booking> bookingsToDisplay = new ArrayList<>();

        int choice = input.getInt("");
        switch (choice)
        {
            case 1 -> bookingsToDisplay.add(
                    bookingService.bookingRepository.getBooking(input.getInt("Boknings ID: ")));
            case 2 -> bookingsToDisplay.addAll(
                    bookingService.bookingRepository.getBookingsSortedByDate());
            case 3 -> bookingsToDisplay.addAll(
                    bookingService.bookingRepository.getBookingsSortedByStatus());
            case 0 -> {return;}
        }

        if (bookingsToDisplay.isEmpty() || bookingsToDisplay.get(0) == null)
        {
            return;
        }

        bookingsToDisplay.forEach(b -> b.printInfo(FORMATTER));
    }

    private void showAvailableBookings()
    {
        System.out.println("\n---> Tillgängliga tider <---");

        var times = bookingService.getAvailableTimes();

        times.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry-> System.out.printf("%s → %s%n",
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

    private void completeBooking()
    {
        System.out.println("\n---Markera bokning som klar---");

        var allBookings = bookingService.getAllBookings();
        var pendingBookings = allBookings.stream()
                .filter(b -> !b.isCompleted())
                .toList();

        if (pendingBookings.isEmpty())
        {
            System.out.println("Inga oavslutade bokningar finns");
            return;
        }

        System.out.println("\n Pågående bokningar: ");
        pendingBookings.forEach(b-> System.out.printf("ID: %d \t Kund: %s \t Typ: %s \t Datum: %s%n"
                , b.getId(), b.getCustomer().getName(), b.getServiceType(), b.getDate()
        ));

        int bookingId = input.getInt("\n Skriv boknings-ID att markera som klar: ");

        Booking booking = allBookings.stream()
                .filter(b->b.getId() == bookingId)
                .findFirst()
                .orElse(null);

        if (booking== null)
        {
            System.out.println("Bokning hittades inte.");
            logger.warn("Kunde inte markera bokning {} som klar för den hittades inte", bookingId);
            return;
        }

        if (booking.isCompleted())
        {
            System.out.println("Bokningen är redan klar");
            return;
        }

        Double reparationPrice = null;
        if (booking.getServiceType() == ServiceType.REPARATION)
        {
            System.out.println("\nDenna bokning är en reparation och behöver prissättas högre än 0 kr.");
            reparationPrice = input.getDouble("Ange slutgiltigt pris för reparationen: ");

            if (reparationPrice<=0)
            {
                System.out.println("Priset måste vara högre än 0.");
                return;
            }
        }

        boolean klar = bookingService.completeBooking(bookingId, reparationPrice);

        if (klar)
        {
            System.out.println("Bokningen är avklarad.");
            System.out.println("Boknings-ID: " + booking.getId()
                    + " för kunden " + booking.getCustomer().getName()
                    + " är avslutad och kostar " + booking.getPrice()
                    + " och ett mejl kommer skickas till " + booking.getCustomer().getEmail()
                    + " för att informera om att bilen är redo att hämtas.");

            emailService.sendCompletionNotification(booking);
            logger.info("Bokningen med ID {} är markerad som klar.", bookingId);
        } else
        {
            System.out.println("Kunde inte markera som klar.");
            logger.warn("Kunde inte markera bokning som klar.");
        }
    }

    private void editBooking() {
        System.out.println("\n--- Redigera bokning ---");
        int bookingId = input.getInt("Ange boknings-ID att redigera: ");
        Booking existing = bookingService.getBookingById(bookingId);

        if (existing == null) {
            System.out.println("Ingen bokning med ID " + bookingId + " hittades.");
            logger.warn("Försök att redigera bokning med ogiltigt ID: {}", bookingId);
            return;
        }

        System.out.println("Nuvarande bokning:");
        System.out.println(existing);

        String newName = input.getOptionalString("Nytt namn (" + existing.getCustomer().getName() + "): ");
        String newEmail = input.getOptionalString("Ny e-post (" + existing.getCustomer().getEmail() + "): ");
        if (!newEmail.isBlank() && !emailValidator.isValid(newEmail)) {
            System.out.println("Ogiltig e-postadress. Ändring ignoreras.");
            newEmail = existing.getCustomer().getEmail();
        }

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName(newName.isBlank() ? existing.getCustomer().getName() : newName);
        updatedCustomer.setEmail(newEmail.isBlank() ? existing.getCustomer().getEmail() : newEmail);

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

        LocalDateTime currentDateTime = existing.getDate();
        LocalDateTime newDateTime = input.getOptionalDateTime(
                "Nytt datum och tid (yyyy-MM-dd HH:mm) [" + currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "]: "
        );
        LocalDateTime finalDateTime = (newDateTime == null) ? currentDateTime : newDateTime;

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

        //  Fråga efter ny åtgärd om reparation
        String newAction = existing.getAction();
        if (newType == ServiceType.REPARATION) {
            String editedAction = input.getOptionalString("Ny åtgärd (" + (newAction == null ? "ingen" : newAction) + "): ");
            if (!editedAction.isBlank()) {
                newAction = editedAction;
            }
        }

        System.out.println("Ändra status:");
        System.out.println("1. Inte klar");
        System.out.println("2. Klar");
        int statusChoice = input.getOptionalInt("Ditt val (1-2): ");
        boolean newStatus = switch (statusChoice) {
            case 1 -> false;
            case 2 -> true;
            default -> existing.isCompleted();
        };

        Booking updated = new Booking(
                bookingId,
                updatedCustomer,
                updatedVehicle,
                finalDateTime,
                newType,
                existing.getPrice(),
                newStatus,
                newAction
        );

        String confirm = input.getOptionalString("Vill du spara ändringarna? [(y)es/(n)o]: ").toLowerCase();
        if (confirm.equals("n") || confirm.equals("no")) {
            System.out.println("Ändringar avbröts.");
            logger.info("Användaren avbröt redigering av bokning {}", bookingId);
            return;
        }

        boolean success = bookingService.editBooking(bookingId, updated);
        if (success) {
            System.out.println("Bokning uppdaterad.");
            logger.info("Bokning med ID {} uppdaterades.", bookingId);
        } else {
            System.out.println("Bokning kunde inte uppdateras.");
            logger.warn("Misslyckades med att uppdatera bokning med ID {}", bookingId);
        }
    }
}
