package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class contains the variables and methods that describe a FlightRecord
 * object extracted from a file.
 */
public class FlightRecords {
    private String flightNumber;
    private String flightDestination;
    private double flightCost;
    private BufferedReader objReader;
    private String filename = "FlightRecords.txt";

    /**
     * Accessor method for flightNumber.
     * @return copy of flightNumber.
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Accessor method for flightDestination.
     * @return copy of flightDestination.
     */
    public String getFlightDestination() {
        return flightDestination;
    }

    /**
     * Accessor method for flightCost.
     * @return copy of flightCost.
     */
    public double getFlightCost() {
        return flightCost;
    }

    /**
     * Class constructor for FlightRecords to create a new object using the
     * destination entered by the user.
     * @param destination String destination entered by the user.
     * @throws IOException propagates to user when an error occurs with opening
     *                      the record files.
     */
    public FlightRecords(String destination) throws IOException {
            String line;
            this.objReader = new BufferedReader(new FileReader(this.filename));
            //While loop until line is null to iterate through all the lines
            // in a txt file to find the right flight record.
            while ((line = this.objReader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[1].equalsIgnoreCase(destination)) {
                    this.flightNumber = values[0];
                    this.flightDestination = values[1];
                    this.flightCost = Double.parseDouble(values[2]);
                }
            }
            this.objReader.close();
    }
}