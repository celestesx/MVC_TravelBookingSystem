package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class contains the variables and methods that describe a
 * FlightBooking object.
 */
public class FlightBooking extends Booking {
    private String flightNumber;
    private String destination;
    private LocalDate departureDate;
    private ArrayList<String> passengers;
    private double singleFlightCost;
    private double totalCost;

    /**
     * Accessor method for flightNumber.
     * @return copy of flightNumber.
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Accessor method for passengers.
     * @return copy of passengers arraylist.
     */
    public ArrayList<String> getPassengers() {
        return passengers;
    }

    /**
     * Accessor method for singleFlightCost.
     * @return copy of singleFlightCost.
     */
    public double getSingleFlightCost() {
        return singleFlightCost;
    }

    /**
     * Accessor method for totalCost.
     * @return copy of totalCost.
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Accessor method for destination.
     * @return copy of destination.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Accessor method for departureDate.
     * @return copy of departureDate.
     */
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    /**
     * Mutator method to set the departureDate. (Used for updating check-in)
     * @param departureDate departureDate determined by the program.
     */
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Mutator method to set the destination. (Used for flights in
     * HolidayBooking).
     * @param destination destination entered by the user.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Class constructor to instantiate a FlightBooking object.
     * @param customerName Customer's name.
     * @param flightNumber Flight number determined by the program obtained
     *                     from flight records.
     * @param destination Destination entered by user.
     * @param departureDate Date of departure entered by user.
     * @param passengers String array of passengers.
     */
    public FlightBooking(String customerName,
                         String flightNumber,
                         String destination,
                         LocalDate departureDate,
                         ArrayList<String> passengers) {
        super(customerName);
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureDate = departureDate;
        this.passengers = passengers;
    }

    /**
     * Overloaded class constructor to instantiate a FlightBooking object for
     * HolidayBooking objects.
     * @param customerName Customer's name.
     * @param flightNumber Flight number determined by the program obtained
     *                     from flight records.
     * @param departureDate Date of departure entered by user.
     * @param passengers String array of passengers.
     */
    public FlightBooking(String customerName, String flightNumber,
                         LocalDate departureDate,
                         ArrayList<String> passengers) {
        super(customerName);
        this.setBookingID(count);
        this.setInvoiceNo(invCount);
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.passengers = passengers;
    }

    /**
     * Overloaded class constructor used to instantiate FlightBooking objects
     * for loading data from files to array.
     * @param bookingID BookingID of booking.
     * @param customerName Customer's name.
     * @param bookingDate Date the booking is made.
     * @param invoiceNo Invoice number of booking.
     * @param flightNumber Flight number of a flight.
     * @param destination Destination of the flight.
     * @param departureDate Departure date of the flight.
     * @param passengers Array of passengers.
     * @param singleFlightCost Single cost of this flight.
     * @param totalCost Total cost accounting for all passengers entered.
     */
    public FlightBooking(int bookingID,
                         String customerName,
                         LocalDate bookingDate,
                         int invoiceNo,
                         String flightNumber,
                         String destination,
                         LocalDate departureDate,
                         ArrayList<String> passengers,
                         double singleFlightCost,
                         double totalCost) {
        this.setCustomerName(customerName);
        this.setBookingID(bookingID);
        this.setInvoiceNo(invoiceNo);
        this.setBookingDate(bookingDate);
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureDate = departureDate;
        this.passengers = passengers;
        this.singleFlightCost = singleFlightCost;
        this.totalCost = totalCost;
    }

    /**
     * Method for calculating the booking cost.
     * @param fr FlightRecords object created from files.
     */
    @Override
    public void calculateBookingCost(FlightRecords fr) {
        this.singleFlightCost = fr.getFlightCost();
        this.totalCost = this.singleFlightCost * this.passengers.size();
    }

    /**
     * Method to print the details of this flight.
     */
    @Override
    public void printBookingDetails() {
        super.printBookingDetails();
        printFlightInfo();
    }

    /**
     * Method to print details of this flight. In its own method for use in
     * HolidayBooking.
     */
    public void printFlightInfo() {
        System.out.printf("Flight Number: %s\n", this.flightNumber);
        System.out.printf("Destination: %s\n", this.destination);
        System.out.printf("Departure Date: %s\n",
                this.departureDate.toString());
        System.out.printf("Passengers: %s\n", passengerNames());
        System.out.println("*************************************\n");
    }

    /**
     * Method to view invoice for this flight.
     */
    @Override
    public void viewInvoice() {
        super.viewInvoice();
        printPassengerLine(this.singleFlightCost);
        System.out.printf("%70s%.2f\n", "TOTAL COST: $", this.totalCost);
        System.out.println("-------\n");
    }

    /**
     * Method to iterate ArrayList of passengers to print it out for invoices.
     * @param cost Cost per passenger.
     */
    public void printPassengerLine(double cost) {
        //Iterate the passengers ArrayList to get details and print.
        for (String passenger: this.passengers) {
            System.out.printf("%-50s%-10d%-10.2f%-20.2f\n",
                    "Flight for " + passenger, 1, cost, cost);
        }
    }

    /**
     * Method to iterate ArrayList of passengers to print it out for flight
     * details.
     * @return Single string of passenger names.
     */
    private String passengerNames() {
        String passengerNames = "";
        int i = 1;
        //Iterate the passengers ArrayList to get details and print.
        for (String passenger: this.passengers) {
            passengerNames += i + ")" + passenger + " ";
            i++;
        }
        return passengerNames;
    }

    /**
     * Method to count the number of passengers.
     * @return Integer value of number of passengers.
     */
    public int passengerCount() {
        return this.passengers.size();
    }
}