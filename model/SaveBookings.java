package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class functions as the file handling purpose of saving data onto files.
 */
public class SaveBookings {
    private final PrintWriter flightWriter;
    private final PrintWriter holidayWriter;
    private final String flightBookingsFile = "FlightBookings.txt";
    private final String holidayBookingsFile = "HolidayBookings.txt";

    /**
     * Class constructor to create new PrintWriter objects for different
     * files. Write objects from different files depending on its class type.
     * @param bookings ArrayList passed into this method from controller.
     * @throws IOException propagates to user when an error occurs with opening
     *                      the record files.
     */
    public SaveBookings(ArrayList<Booking> bookings) throws IOException {
        this.flightWriter =
                new PrintWriter(new FileWriter(this.flightBookingsFile));
        this.holidayWriter =
                new PrintWriter(new FileWriter(this.holidayBookingsFile));

        /* Iterate the bookings array to write into designated files depending
         * on class type.
         */
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i) instanceof FlightBooking) {
                writeFlight(this.flightWriter,bookings, i);
            } else if (bookings.get(i) instanceof HolidayBooking) {
                writeHoliday(this.holidayWriter, bookings, i);
            }
        }
        this.flightWriter.close();
        this.holidayWriter.close();
    }

    /**
     * Method to write FlightBooking objects to file.
     * @param writer PrintWriter object for this purpose.
     * @param bookings ArrayList passed into this method from controller.
     * @param i index number of object in array.
     */
    private void writeFlight(PrintWriter writer, ArrayList<Booking> bookings,
                             int i) {
        ArrayList passengers;
        FlightBooking fb = ((FlightBooking) bookings.get(i));
        //Split by "<" as addresses have commas.
        writer.print(fb.getBookingID() + "<");
        writer.print(fb.getCustomerName() + "<");
        writer.print(fb.getBookingDate() + "<");
        writer.print(fb.getInvoiceNo() + "<");
        writer.print(fb.getFlightNumber() + "<");
        writer.print(fb.getDestination() + "<");
        writer.print(fb.getDepartureDate() + "<");

        //Iterating passengers arraylist to write every item
        passengers = fb.getPassengers();
        for (int j = 0; j < passengers.size(); j++) {
            writer.print(passengers.get(j) + ">");
        }
        writer.print("<");

        writer.print(fb.getSingleFlightCost() + "<");
        writer.print(fb.getTotalCost() + "<");
    }

    /**
     * Method to write HolidayBooking objects to file
     * @param writer PrintWriter object for this purpose.
     * @param bookings ArrayList passed into this method from controller.
     * @param i index number of object in array.
     */
    private void writeHoliday(PrintWriter writer, ArrayList<Booking> bookings,
                              int i) {
        ArrayList<String> passengers;
        HolidayBooking hb = ((HolidayBooking) bookings.get(i));
        writer.print(hb.getBookingID() + "<");
        writer.print(hb.getCustomerName() + "<");
        writer.print(hb.getBookingDate() + "<");
        writer.print(hb.getInvoiceNo() + "<");

        //Getting the flight in holiday and writing to file
        FlightBooking fb = hb.getFlight();
        writer.print(fb.getFlightNumber() + "<");
        writer.print(fb.getDestination() + "<");
        writer.print(fb.getDepartureDate() + "<");

        //Iterating passengers arraylist to write every item
        passengers = fb.getPassengers();
        for (int j = 0; j < passengers.size(); j++) {
            writer.print(passengers.get(j) + ">");
        }
        writer.print("<");

        writer.print(fb.getSingleFlightCost() + "<");
        writer.print(fb.getTotalCost() + "<");

        writer.print(hb.getAccommodationName() + "<");
        writer.print(hb.getAccommodationAddress() + "<");
        writer.print(hb.getCheckIn() + "<");
        writer.print(hb.getCheckOut() + "<");
        writer.print(hb.getSingleNightCost() + "<");
        writer.print(hb.getTotalCost() + "<");
    }
}