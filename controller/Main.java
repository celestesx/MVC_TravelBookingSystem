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

/*
 *Bibliography
 * [1]Java2Blog, “Get number of days in Month in java - Java2Blog,”
 * java2blog.com, Feb. 07, 2022. https://java2blog.com/get-number-of-days-month-java/
 * (accessed May 20, 2022).
 *
 * [2]BeginnersBook, “Java 8 - Calculate days between two dates,”
 * beginnersbook.com, Oct. 17, 2017.
 * https://beginnersbook.com/2017/10/java-8-calculate-days-between-two-dates/
 * (accessed May 20, 2022).
 */