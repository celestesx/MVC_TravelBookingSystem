package model;
import java.time.LocalDate;

/**
 * This class contains the variables and methods that describe a Booking
 * object. Is it abstract so that it cannot be instantiated.
 */
public abstract class Booking {
    private int bookingID;
    private String customerName;
    private LocalDate bookingDate;
    private int invoiceNo;
    //These variables are static to increase bookingID and invoiceNo with
    // every Booking created.
    protected static int count = 1000, invCount = 9900;

    /**
     * Accessor method for bookingID
     * @return copy of BookingID.
     */
    public int getBookingID() {
        return bookingID;
    }

    /**
     * Accessor method for customer name.
     * @return copy of customer name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Accessor method for booking date.
     * @return copy of booking date.
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Accessor method for invoice number.
     * @return copy of invoice number.
     */
    public int getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * Mutator method to set bookingID. (Used for loading files)
     * @param bookingID BookingID determined by program.
     */
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    /**
     * Mutator method to set invoiceNo. (Used for loading files)
     * @param invoiceNo InvoiceID determined by program.
     */
    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * Mutator method to set the customer name for FlightBooking objects
     * created in HolidayBooking objects.
     * @param customerName the customer name for holiday booking.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Mutator method to set the booking date for FlightBooking objects
     * created in HolidayBooking objects.
     * @param bookingDate the booking date for holiday booking.
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Constructor for all the variables of the class.
     * @param customerName Customer name typed by user.
     */
    public Booking (String customerName) {
        this.bookingID = count;
        this.invoiceNo = invCount;
        this.customerName = customerName;
        this.bookingDate = LocalDate.now();
    }

    /**
     * Overloaded constructor when customer name is the same between holiday
     * booking and flight booking. Setter methods used for assigning states
     * to the object.
     */
    public Booking() {
        this.bookingID = 0;
        this.invoiceNo = 0;
        this.customerName = "";
        this.bookingDate = null;
    }

    /**
     * Abstract method to calculate the booking cost.
     * @param fr FlightRecords object created from files.
     */
    public abstract void calculateBookingCost(FlightRecords fr);

    /**
     * Method to print booking details of this booking.
     */
    public void printBookingDetails () {
        System.out.println("\n***** Booking Details *****");
        System.out.printf("Booking ID: %s\n", this.bookingID);
        System.out.printf("Customer Name: %s\n", this.customerName);
        System.out.printf("Date: %s\n", this.bookingDate.toString());
        System.out.println("*************************************");
    }

    /**
     * Method to view the invoice for this booking.
     */
    public void viewInvoice() {
        System.out.println("\nInvoice");
        System.out.println("-------");
        System.out.printf("Invoice Number: %d\n", this.invoiceNo);
        System.out.println("From: TravelAdventure Pty. Ltd.");
        System.out.printf("Booking ID: %s\n", this.bookingID);
        System.out.printf("Date: %s\n", this.bookingDate.toString());
        System.out.printf("To Customer: %s\n\n", this.customerName);
        System.out.printf("%-50s%-10s%-10s%-20s\n", "Description", "Quantity",
                "Price", "Line Total");
    }
}