package model;

/**
 * This class defines a booking exception which thrown when a booking cannot
 * be created.
 */
public class BookingException extends Exception {
    public BookingException(String errorMessage) {
        super(errorMessage);
    }
}
