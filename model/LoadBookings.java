package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * This class functions as the file handling purpose of loading data from files.
 */
public class LoadBookings {
    private BufferedReader objReader;
    private final String flightBookingsFile = "FlightBookings.txt";
    private final String holidayBookingsFile = "HolidayBookings.txt";
    private ArrayList<Booking> bookings;

    /**
     * Accessor method for bookings.
     * @return copy of bookings.
     */
    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    /**
     * Class constructor to instantiate a new bookings array and perform the
     * necessary reading and creation of new objects to be added to the
     * bookings array.
     * @throws IOException propagates to user when an error occurs with opening
     *                      the record files.
     */
    public LoadBookings() throws IOException {
        this.bookings = new ArrayList<>();
        readFlightBookings();
        readHolidayBookings();
        //Sorting the array by bookingID ascending
        bookings.sort(Comparator.comparing(Booking::getBookingID));
    }

    /**
     * Method to open flight booking file to read, create FlightBooking
     * objects and add to array.
     * @throws IOException
     */
    private void readFlightBookings() throws IOException {
        int bookingID, invoiceNo;
        String customerName, flightNumber, destination;
        LocalDate bookingDate, departureDate;
        ArrayList<String> passengers = new ArrayList<>();
        double singleFlightCost, totalCost;

        String line;
        this.objReader =
                new BufferedReader(new FileReader(this.flightBookingsFile));
        //While loop until line is null to iterate through all the lines
        // in a txt file to find the right flight record.
        while ((line = this.objReader.readLine()) != null) {
            String[] values = line.split("<");
            bookingID = Integer.parseInt(values[0]);
            customerName = values[1];
            bookingDate = LocalDate.parse(values[2]);
            invoiceNo = Integer.parseInt(values[3]);
            flightNumber = values[4];
            destination = values[5];
            departureDate = LocalDate.parse(values[6]);
            String[] passengerList = values[7].split(">");
            passengers.addAll(Arrays.asList(passengerList));
            singleFlightCost = Double.parseDouble(values[8]);
            totalCost = Double.parseDouble(values[9]);
            //Create a FlightBooking object to add to array
            FlightBooking object = new FlightBooking(bookingID, customerName,
                    bookingDate, invoiceNo, flightNumber, destination,
                    departureDate, passengers, singleFlightCost, totalCost);
            this.bookings.add(object);
        }
        this.objReader.close();
    }

    /**
     * Method to open holiday booking file to read, create objects and add to
     * array.
     * @throws IOException
     */
    public void readHolidayBookings() throws IOException {
        //Variables for FlightBooking object in HolidayBooking
        int bookingID, invoiceNo;
        String customerName, flightNumber, destination;
        LocalDate bookingDate, departureDate;
        ArrayList<String> passengers = new ArrayList<>();
        double singleFlightCost, totalCost;

        //Variables for HolidayBooking
        String accommodationName, accommodationAddress;
        LocalDate checkIn, checkOut;
        double singleNightCost, HolidayTotalCost;

        String line;
        this.objReader =
                new BufferedReader(new FileReader(this.holidayBookingsFile));
        //While loop until line is null to iterate through all the lines
        // in a txt file to find the right flight record.
        while ((line = this.objReader.readLine()) != null) {
            String[] values = line.split("<");
            bookingID = Integer.parseInt(values[0]);
            customerName = values[1];
            bookingDate = LocalDate.parse(values[2]);
            invoiceNo = Integer.parseInt(values[3]);
            flightNumber = values[4];
            destination = values[5];
            departureDate = LocalDate.parse(values[6]);
            String[] passengerList = values[7].split(">");
            passengers.addAll(Arrays.asList(passengerList));
            singleFlightCost = Double.parseDouble(values[8]);
            totalCost = Double.parseDouble(values[9]);
            accommodationName = values[10];
            accommodationAddress = values[11];
            checkIn = LocalDate.parse(values[12]);
            checkOut = LocalDate.parse(values[13]);
            singleNightCost = Double.parseDouble(values[14]);
            HolidayTotalCost = Double.parseDouble(values[15]);

            //Create the FlightBooking object
            FlightBooking flightObject = new FlightBooking(bookingID,
                    customerName,
                    bookingDate, invoiceNo, flightNumber, destination,
                    departureDate, passengers, singleFlightCost, totalCost);

            //Create the HolidayBooking object.
            HolidayBooking holidayObject = new HolidayBooking(bookingID,
                    customerName, bookingDate, invoiceNo, flightObject,
                    accommodationName, accommodationAddress, checkIn,
                    checkOut, singleNightCost, HolidayTotalCost);

            this.bookings.add(holidayObject);
        }
        this.objReader.close();
    }
}