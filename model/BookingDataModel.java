package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static model.Booking.count;
import static model.Booking.invCount;

/**
 * This class contains the array of all the bookings as well as the methods
 * to manipulate it.
 */
public class BookingDataModel {
    private ArrayList<Booking> bookings;
    private FlightRecords fr;
    private HolidayRecords hr;

    /**
     * Class constructor of the BookingDataModel to instantiate a new ArrayList.
     */
    public BookingDataModel() {
        this.bookings = new ArrayList<>();
    }

    /**
     * Method to check if a flight exists to the destination entered by the
     * user.
     * @param destination String destination entered by the user.
     * @throws BookingException propagates to user when a flight does not exist
     *                          in a destination.
     * @throws IOException propagates to user when an error occurs with opening
     *                      the record files.
     */
    public void checkFlightExists(String destination) throws BookingException, IOException {
        this.fr = new FlightRecords(destination);
        /*
         * Throw a BookingException to the user if FlightRecords object is null.
         */
        if (this.fr.getFlightNumber() == null) {
            throw new BookingException("***** Destination not found in " +
                    "flights. *****");
        }
    }

    /**
     * Method to get an array of accommodation names from files.
     * @param destination Destination entered by user.
     * @return String array of accommodation names at destination.
     */
    public String[] getAccommodationList(String destination) {
        this.hr = new HolidayRecords(destination);
        ArrayList<Accommodation> accommodations =
                this.hr.getAccommodationArrayList();
        String[] accommodationList = new String[(accommodations.size())];

        /*
         * Taking accommodation name values from the ArrayList object to
         * assign to string array.
         */
        for (int i = 0; i < accommodations.size(); i++) {
            accommodationList[i] =
                    accommodations.get(i).getAccommodationName();
        }
        return accommodationList;
    }

    /**
     * Method to add a FlightBooking object to the array.
     * @param customerName Customer's name.
     * @param destination Destination entered by user.
     * @param departureDate Date of departure entered by user.
     * @param passengers String array of passengers.
     * @return BookingID as determined by the program.
     * @throws BookingException propagates to user when a flight does not exist
     *                          in a destination.
     * @throws IOException propagates to user when an error occurs with opening
     *                      the record files.
     */
    public int addFlightBooking(String customerName, String destination,
                                LocalDate departureDate,
                                ArrayList<String> passengers) throws BookingException, IOException {
        checkFlightExists(destination);
        String flightNumber = this.fr.getFlightNumber();
        String flightDestination = this.fr.getFlightDestination();
        //Create the FlightBooking object.
        FlightBooking fb = new FlightBooking(customerName, flightNumber,
                flightDestination, departureDate, passengers);
        fb.calculateBookingCost(this.fr);
        //Add booking to array.
        this.bookings.add(fb);
        //BookingID and InvoiceNo increases with every booking created.
        count++;
        invCount++;
        return fb.getBookingID();
    }

    /**
     * Method to create a FlightBooking object for HolidayBooking objects.
     * @param customerName Customer's name.
     * @param destination Destination entered by user.
     * @param departureDate Date of departure entered by user.
     * @param passengers String array of passengers.
     * @return FlightBooking object to be added to HolidayBooking object.
     * @throws BookingException propagates to user when a flight does not exist
     *                          in a destination.
     * @throws IOException propagates to user when an error occurs with opening
     *                      the record files.
     */
    public FlightBooking addHolidayFlight(String customerName,
                                 String destination, LocalDate departureDate,
                                 ArrayList<String> passengers) throws BookingException, IOException {

        checkFlightExists(destination);
        String flightNumber = this.fr.getFlightNumber();
        //Create the FlightBooking object.
        FlightBooking flightObject = new FlightBooking(customerName,
                flightNumber, departureDate, passengers);
        return flightObject;
    }

    /**
     * Method to create a HolidayBooking object to be added to the array.
     * @param customerName Customer's name.
     * @param destination Destination entered by user.
     * @param departureDate Date of departure entered by user.
     * @param passengers String array of passengers.
     * @param accommodationName Name of the accommodation provided by the
     *                          program by user choice.
     * @param checkIn Date of check-in entered by the user.
     * @param checkOut Check-out date generated by the program by taking the
     *                  number of nights from user.
     * @return BookingID as determined by the program.
     * @throws BookingException propagates to user when a flight does not exist
     *                          in a destination.
     * @throws IOException propagates to user when an error occurs with opening
     *                      the record files.
     */
    public int addHolidayBooking(String customerName, String destination,
                                  LocalDate departureDate,
                                  ArrayList<String> passengers,
                                  String accommodationName,
                                  LocalDate checkIn,
                                  LocalDate checkOut) throws BookingException, IOException {
        String accommodationAddress;
        double singleNightCost;
        //Create the FlightBooking object.
        FlightBooking flightObject = addHolidayFlight(customerName,
                destination, departureDate, passengers);
        flightObject.setDestination(destination);
        accommodationAddress = hr.getAddress(accommodationName);
        singleNightCost = this.hr.getCost(accommodationName);
        //Create the HolidayBooking object.
        HolidayBooking hb = new HolidayBooking(customerName, flightObject,
                accommodationName, accommodationAddress, singleNightCost,
                checkIn, checkOut);

        hb.calculateBookingCost(this.fr);
        //Add booking to array.
        this.bookings.add(hb);
        //BookingID and InvoiceNo increases with every booking created.
        count++;
        invCount++;
        return hb.getBookingID();
    }

    /**
     * Method to update a holiday booking check-in and check-out dates.
     * @param bookingID BookingID entered by the user to find booking.
     * @param checkIn Date of check-in entered by the user.
     * @param checkOut Check-out date generated by the program by taking
     *                 the number of nights from user.
     * @throws IOException  propagates to user when an error occurs with
     *                      opening the record files.
     */
    public void updateHolidayBooking(int bookingID, LocalDate checkIn,
                                     LocalDate checkOut) throws IOException {
        HolidayBooking booking;

         //Loop to find the booking that has the bookingID entered by the user.
        for (Booking i: this.bookings) {
            if (i.getBookingID() == bookingID) {
                ((HolidayBooking) i).setCheckIn(checkIn);
                ((HolidayBooking) i).setCheckOut(checkOut);
                booking = (HolidayBooking) i;
                this.fr = new FlightRecords(booking.getFlight().getDestination());
                booking.calculateBookingCost(this.fr);
            }
        }

    }

    /**
     * Method to print details of all the bookings in the array.
     */
    public void printAllBookingDetails() {
        int count = 1;
        //Iterate the array to print every booking.
        for (Booking i: this.bookings) {
            System.out.println("[BOOKING " + count + "]");
            i.printBookingDetails();
            count++;
        }
        //Check if bookings array is empty.
        if (this.bookings.size() == 0) {
            System.out.println("There are no bookings recorded.");
        }
    }

    /**
     * Method to print an itinerary containing flight information with
     * provided bookingID.
     * @param bookingID BookingID entered by the user to find booking.
     */
    public void printItinerary(int bookingID) {
        //Iterate the array to find instances of FlightBooking objects.
        for (Booking i: this.bookings) {
            if (i instanceof FlightBooking) {
                if (i.getBookingID() == bookingID) {
                    i.printBookingDetails();
                }
            } else {
                if (i.getBookingID() == bookingID) {
                    ((HolidayBooking) i).getFlight().printBookingDetails();
                }
            }
        }
    }

    /**
     * Method to view the invoice for a booking.
     * @param bookingID BookingID entered by the user.
     */
    public void viewSingleInvoice(int bookingID) {
        //Iterate the array to find matching booking with bookingID provided.
        for (Booking i: this.bookings) {
            if (i.getBookingID() == bookingID) {
                i.viewInvoice();
            }
        }
    }

    /**
     * Method to check if a booking exists.
     * @param bookingID BookingID entered by the user to find booking.
     * @return true if booking exists, false if it doesn't.
     */
    public boolean checkBookingIDExist(int bookingID) {
        int idFound = 0;
        boolean idExist = false;
        //Iterate the array to find the booking.
        for (Booking i: this.bookings) {
            if (i.getBookingID() == bookingID) {
                idFound++;
            }
        }
        if (idFound > 0) {
            idExist = true;
        }
        return idExist;
    }

    /**
     * Method to check if a booking is a HolidayBooking object.
     * @param bookingID BookingID entered by the user to find the booking.
     * @return true if booking is a HolidayBooking, false if it isn't.
     */
    public boolean checkBookingIsHoliday(int bookingID) {
        boolean isHoliday = false;
        //Iterate the array to find HolidayBooking with bookingID.
        for (Booking i: this.bookings) {
            if (i.getBookingID() == bookingID && i instanceof HolidayBooking) {
                isHoliday = true;
                break;
            }
        }
        return isHoliday;
    }

    /**
     * Method to instantiate the saving of array data into files.
     * @throws IOException  propagates to user when an error occurs with
     *                      opening the record files.
     */
    public void saveToFiles() throws IOException {
        new SaveBookings(this.bookings);
    }

    /**
     * Method to load data from files to array.
     * @throws IOException  propagates to user when an error occurs with
     *                      opening the record files.
     */
    public void loadFiles() throws IOException {
        LoadBookings loading = new LoadBookings();
        this.bookings = loading.getBookings();
        //BookingID and invoiceNo updated to reflect continuous adding of
        // bookings.
        if (this.bookings.size() > 0) {
            count = this.bookings.get(this.bookings.size() - 1).getBookingID()
                    + 1;
            invCount = this.bookings.get(this.bookings.size() - 1).getBookingID()
                    + 1;
        }
    }

    /**
     * Method to get the departure date of a booking to be used to validate
     * check-in dates.
     * @param bookingID BookingID provided by the user or program.
     * @return LocalDate object representing the departure date of a booking.
     */
    public LocalDate getDepartureDate(int bookingID) {
        LocalDate date = null;
        //Iterate the array to find the booking.
        for (Booking i: this.bookings) {
            if (i.getBookingID() == bookingID) {
                date = ((HolidayBooking) i).getFlight().getDepartureDate();
            }
        }
        return date;
    }

    /**
     * Method to set the departure date of a flight if the user decides to
     * enter an earlier check-in date.
     * @param bookingID BookingID provided by the user.
     * @param departureDate new departure date provided by the program.
     */
    public void setDepartureDate(int bookingID, LocalDate departureDate) {
        for (Booking i: this.bookings) {
            if (i.getBookingID() == bookingID) {
                ((HolidayBooking) i).getFlight().setDepartureDate(departureDate);
            }
        }
    }
}