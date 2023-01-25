package view;

import controller.BookingManagementController;
import model.BookingException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class forms the user interface (view) of the program. It allows the
 * user to perform flight and holiday booking functions as well as viewing
 * details, invoices and itineraries.
 */
public class BookingManagementSystem {
    private Scanner sc;
    private BookingManagementController controller;

    /**
     * Constructor for the BookingManagementSystem class. Contains the
     * methods and instructions for the operation of this program.
     * @param controller BookingManagementController object containing the
     *                   methods that works with the model package.
     */
    public BookingManagementSystem(BookingManagementController controller) {
        this.controller = controller;
        String userChoice;
        System.out.println("Hello, welcome to Travel Adventure Booking " +
                "System.");
        /*
         * Try-catch statement for loading of data from files to array.
         */
        try {
            controller.loadFiles();
        } catch (IOException e) {
            System.out.println("There seems to be a problem loading the " +
                    "bookings.");
        }

        //Do-while loop and switch case for menu selection.
        do {
            userChoice = getMainMenuOption();
            switch (userChoice.toUpperCase()) {
                case "A":
                    bookAFlight();
                    break;
                case "B":
                    bookAHoliday();
                    break;
                case "C":
                    viewInvoice();
                    break;
                case "D":
                    getItinerary();
                    break;
                case "E":
                    this.controller.printAllDetails();
                    break;
                case "F":
                    updateHolidayBooking();
                    break;
            }
        } while (!userChoice.equalsIgnoreCase("X"));

        //Saving data into files from the array.
        try {
            controller.saveToFiles();
            System.out.println("Bookings has been saved to file.");
        } catch (IOException e) {
            System.out.println("There was an error saving to files.");
        }

        System.out.println("Goodbye.");
    }

    /**
     * Method to create a FlightBooking object by obtaining user input and
     * validating it.
     */
    private void bookAFlight() {
        String name, destination;
        int bookingID;
        LocalDate departureDate;
        ArrayList<String> passengers;

        System.out.println("***** Book a Flight *****");
        System.out.println("Please type in the answers when prompted.");
        destination = getUserInput("Destination: ");
        System.out.print("Checking for a flight to destination");
        //sleep function to simulate status.
        sleep();

        //Catch a thrown exception if destination is not found in the flight
        //records file.
        try {
            this.controller.checkFlightExists(destination);
            System.out.println("Flight to destination exists.");
            System.out.println("***** Please enter the booking details. *****");
            name = getUserInput("Your name: ");
            departureDate = getValidDate("Please enter the departure date.");
            passengers = getPassengers();
            System.out.println("You have added " + passengers.size() + " " +
                    "passengers.");
            bookingID = this.controller.addFlightBooking(name, destination,
                    departureDate,
                    passengers);
            System.out.println("Flight booking was successful. Here is your " +
                    "booking ID: " + bookingID + ".");
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("An error has occurred.");
        }
    }

    /**
     * Method to create a HolidayBooking object by obtaining user input and
     * validating it.
     */
    private void bookAHoliday() {
        String name, destination, accommodationName;
        LocalDate departureDate, checkIn, checkOut;
        ArrayList<String> passengers;
        String[] accommodationList;
        int bookingID, accommodationChoice, nights, count = 1;
        System.out.println("***** Book a Holiday *****");
        destination = getUserInput("Enter the destination: ");
        System.out.print("Checking for accommodation at destination");

        sleep();
        accommodationList = this.controller.getAccommodationList(destination);

        //Check if accommodation is available at destination.
        if (accommodationList.length > 0) {
            System.out.println("***** Found " + accommodationList.length +
                    " accommodations in " + destination + " *****");

            //Print accommodations in the form of a menu.
            for (String s : accommodationList) {
                System.out.println(count + ") " + s);
                count++;
            }
            accommodationChoice = getValidInteger("Enter the choice of " +
                    "accommodation: ");
            while (accommodationChoice > (count-1) || accommodationChoice < 0) {
                accommodationChoice = getValidInteger("Invalid choice. " +
                        "Re-enter: ");
            }
            accommodationName = accommodationList[accommodationChoice - 1];
            System.out.println("Thank You.");
            System.out.print("Now checking for a flight to destination");
            sleep();


            //Create the FlightBooking object that is a variable in HolidayBooking.
            try {
                this.controller.checkFlightExists(destination);
                System.out.println("Flight to " + destination + " is found.");
                System.out.println("***** Please enter the booking details *****");
                name = getUserInput("Your name: ");
                System.out.println("***** Book Holiday Flight *****");
                departureDate = getValidDate("Please enter the departure date.");
                passengers = getPassengers();
                System.out.println("You have added " + passengers.size() + " " +
                        "passengers.");
                System.out.println("***** Enter Check-In Details *****");

                //Check if check-in date is earlier than departure date.
                do {
                    checkIn = getValidDate("Enter Check-In Date: ");
                    if (checkIn.isBefore(departureDate)) {
                        System.out.println("Check-In cannot be earlier than " +
                                "departure date.");
                    }
                } while (checkIn.isBefore(departureDate));

                nights = getValidInteger("Enter the number of nights you wish" +
                        " to stay: ");
                checkOut = checkIn.plusDays(nights);
                bookingID = this.controller.addHolidayBooking(name, destination,
                        departureDate,
                        passengers, accommodationName, checkIn, checkOut);
                System.out.println("Holiday booking was successful. Here is " +
                        "your booking ID: " + bookingID + ".");
            } catch (BookingException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("An error has occurred.");
            }
        } else {
            System.out.println("Sorry, no accommodations found in " +
                    destination + ".");
        }
    }

    /**
     * Method to update a HolidayBooking check-in and check-out variables.
     */
    private void updateHolidayBooking() {
        int bookingID, nights;
        boolean bookingIDExists, bookingIsHoliday;
        LocalDate departureDate, checkIn, checkOut;

        bookingID = getValidInteger("Enter the booking ID: ");
        bookingIDExists = this.controller.checkBookingIDExist(bookingID);

        // Check if bookingID exists
        if (bookingIDExists) {
            bookingIsHoliday = this.controller.checkBookingIsHoliday(bookingID);
            // Check if bookingID is HolidayBooking
            if (bookingIsHoliday) {
                departureDate = this.controller.getDepartureDate(bookingID);
                System.out.println("***** Update Holiday Booking *****");
                checkIn = updateCheckIn(bookingID, departureDate);
                nights = getValidInteger("Enter the number of nights you wish" +
                        " to stay: ");
                checkOut = checkIn.plusDays(nights);
                //Update the HolidayBooking check-in and check-out dates.
                try {
                    this.controller.updateHolidayBooking(bookingID, checkIn,
                            checkOut);
                } catch (IOException e) {
                    System.out.println("An error has occurred.");
                }
                System.out.println("Update was successful.");
            } else {
                System.out.println("Booking ID provided is not a Holiday " +
                        "Booking.");
            }
        } else {
            System.out.println("Booking ID does not exist.");
        }
    }

    /**
     * Method to change the Flight departure date if user enters earlier
     * check-in date than departure while updating.
     * @param bookingID BookingID generated by the program assigned to each
     *                  booking.
     * @param departureDate Departure date for flight.
     * @return The check-in date for Holiday Booking.
     */
    private LocalDate updateCheckIn(int bookingID, LocalDate departureDate) {
        LocalDate checkIn;
        checkIn = getValidDate("Enter Check-In Date: ");
        //Check if check-in is before departure.
        if (checkIn.isBefore(departureDate)) {
            System.out.println("Flight departure date is after " + checkIn +
                    ".");
            System.out.println("Amending departure date");
            sleep();
            this.controller.setDepartureDate(bookingID, checkIn);
        }
        return checkIn;
    }

    /**
     * Method to get a valid and allowed date. For example, a user is unable
     * to make a booking to depart before the date of today.
     * @param prompt String prompt in form of query or menu.
     * @return a valid LocalDate.
     */
    private LocalDate getValidDate(String prompt) {
        int year, month, day, numOfDays;
        LocalDate validDate;
        System.out.println(prompt);
        //Disallow years before now.
        year = getValidInteger("Year: ");
        while (year < LocalDate.now().getYear()) {
            year = getValidInteger("Year cannot be before " +
                            LocalDate.now().getYear() + ". Re-enter: ");
        }

        //Validate that month entered.
        do {
            month = getValidInteger("Month: ");
            //Only allow values 1-12.
            if (month > 12 || month < 1) {
                System.out.println("Please enter a value between 1-12.");
            } else {
                //If year was this year, only allow remaining years of this
                // year.
                if (year == LocalDate.now().getYear() &&
                        month < LocalDate.now().getMonthValue()) {
                    System.out.println("Month cannot be before " +
                            LocalDate.now().getMonthValue() + ".");
                }
            }
        } while (month > 12 || month < 1 ||
                month < LocalDate.now().getMonthValue() &&
                year == LocalDate.now().getYear());

        //Get number of days in that month of year.
        numOfDays = getNumDays(year, month);
        //Validate day entered.
        do {
            day = getValidInteger("Day: ");
            //Only allow values available for the month and year entered.
            if (day < 1 || day > numOfDays) {
                System.out.println("Please enter a valid day.");
            } else {
                //If month and year entered same as now, only allow remaining
                // days of the month.
                if ((month == LocalDate.now().getMonthValue()) &&
                        (day < LocalDate.now().getDayOfMonth())){
                    System.out.println("Day cannot be before " +
                            LocalDate.now().getDayOfMonth() + ".");
                }
            }
        } while (day < 1 || day > numOfDays ||
                month == LocalDate.now().getMonthValue() &&
                day < LocalDate.now().getDayOfMonth());

        validDate = LocalDate.of(year, month, day);
        System.out.println("You have entered: " + validDate);
        return validDate;
    }

    /**
     * Method to get number of days in a month in year. [1]
     * @param year Year value entered by user.
     * @param month Month value entered by user.
     * @return Number of days in specific month.
     */
    private int getNumDays(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        return date.lengthOfMonth();
    }

    /**
     * Method to obtain the passengers entered by the user.
     * @return ArrayList of String type containing passenger names.
     */
    private ArrayList<String> getPassengers() {
        ArrayList<String> passengers = new ArrayList<>();
        String passengerName;
        String passengerChoice;

        passengerName = getUserInput("Please enter the first passenger's " +
                "name: ");
        passengers.add(passengerName);
        //Loop to prompt the user repeatedly to enter passenger names until
        // ceased.
        do {
            passengerChoice = getUserInput("Add another passenger? " +
                    "Type 'Y' to add another passenger: ");
            if (passengerChoice.equalsIgnoreCase("Y")) {
                passengerName = getUserInput("Enter the passenger name: ");
                passengers.add(passengerName);
            }
        } while (passengerChoice.equalsIgnoreCase("Y"));

        return passengers;
    }

    /**
     * Method to obtain bookingID input from user to print itinerary
     * associated with the bookingID.
     */
    private void getItinerary() {
        int bookingID;
        boolean bookingIDExists;

        bookingID = getValidInteger("Enter the booking ID: ");
        bookingIDExists = this.controller.checkBookingIDExist(bookingID);

        //Calls the printItinerary method if bookingID exists.
        if (bookingIDExists) {
            this.controller.printItinerary(bookingID);
        } else {
            System.out.println("Booking ID does not exist.");
        }
    }

    /**
     * Method to obtain bookingID input from user to view invoice associated
     * with the bookingID.
     */
    private void viewInvoice() {
        int bookingID;
        boolean bookingIDExists;

        bookingID = getValidInteger("Enter the booking ID: ");
        bookingIDExists = this.controller.checkBookingIDExist(bookingID);

        //Calls the viewInvoice method if bookingID exists.
        if (bookingIDExists) {
            this.controller.viewInvoice(bookingID);
        } else {
            System.out.println("Booking ID does not exist.");
        }
    }

    /**
     * Method to display menu and obtain user choice.
     * @return String of a valid user choice.
     */
    private String getMainMenuOption() {
        String userChoice;
        String mainMenu = "***** Main Menu *****\n";
        mainMenu += "A) Book A Flight\n";
        mainMenu += "B) Book A Holiday\n";
        mainMenu += "C) View Invoice\n";
        mainMenu += "D) Print Itinerary\n";
        mainMenu += "E) View Bookings\n";
        mainMenu += "F) Update Check-In Details\n";
        mainMenu += "X) Exit\n";
        mainMenu += "*********************\n";
        mainMenu += "Your choice: ";

        userChoice = getUserInput(mainMenu);
        //Only allow choices displayed in the text menu.
        while (!userChoice.equalsIgnoreCase("A") &&
                !userChoice.equalsIgnoreCase("B") &&
                !userChoice.equalsIgnoreCase("C") &&
                !userChoice.equalsIgnoreCase("D") &&
                !userChoice.equalsIgnoreCase("E") &&
                !userChoice.equalsIgnoreCase("F") &&
                !userChoice.equalsIgnoreCase("X")) {
            userChoice = getUserInput("No such option, Re-enter: ");
        }
        return userChoice;
    }

    /**
     * Method to obtain a user input that is not empty,
     * @param prompt String prompt in form of menu or query.
     * @return Validated string of user input.
     */
    private String getUserInput(String prompt) {
        sc = new Scanner(System.in);
        System.out.print(prompt);
        String userInput = sc.nextLine();
        //Loop to ensure user enters something.
        while (userInput.equals("")) {
            System.out.print("Input cannot be empty!Re-enter: ");
            userInput = sc.nextLine();
        }
        return userInput;
    }

    /**
     * Method to obtain an integer from the user.
     * @param prompt String prompt in form of menu or query.
     * @return integer value user input.
     */
    private int getValidInteger(String prompt) {
        int value = 0;
        boolean valid = false;
        //Loop to ensure user enters an integer.
        do {
            try {
                value = Integer.parseInt(getUserInput(prompt));
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Input must be an integer value. ");
            }
        } while (!valid);
        return value;
    }

    /**
     * Method to print dots that simulate loading status.
     */
    private void sleep() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
                System.out.print(". ");
            } catch (InterruptedException e) {
                System.out.println("Process was interrupted.");
            }
        }
        System.out.println();
    }
}
