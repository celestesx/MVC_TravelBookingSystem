package controller;

import view.BookingManagementSystem;

/**
 * This is the main method from which the program executes from.
 */
public class Main {
    public static void main(String[] args) {
        BookingManagementController object = new BookingManagementController();
        BookingManagementSystem ui = new BookingManagementSystem(object);
    }
}
