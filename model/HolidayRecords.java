package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class describes a HolidayRecord object with variables and methods.
 */
public class HolidayRecords {
    private ArrayList<Accommodation> accommodationArrayList;
    private BufferedReader objReader;
    private String filename = "HolidayRecords.txt";

    /**
     * Accessor for the Accommodation ArrayList.
     * @return copy of array.
     */
    public ArrayList<Accommodation> getAccommodationArrayList() {
        return accommodationArrayList;
    }

    /**
     * Class constructor to instantiate a HolidayRecords object.
     * @param destination Destination entered by the user.
     */
    public HolidayRecords(String destination) {
        this.accommodationArrayList = new ArrayList<>();
        populateArray(destination);
    }

    /**
     * Method for extracting data from holiday record file and populating the
     * array.
     * @param destination Destination as entered by the user.
     */
    private void populateArray(String destination){
        String location, accommodationName, accommodationAddress;
        double costPerNight;

        //Try-catch statement to open a file, read it, extract the data and
        // close it.
        try {
            String line;
            this.objReader = new BufferedReader(new FileReader(this.filename));
            while ((line = this.objReader.readLine()) != null) {
                String[] values = line.split("/");
                //Finding accommodations that matches the destination entered
                // by the user.
                if (values[0].equalsIgnoreCase(destination)) {
                    location = values[0];
                    accommodationName = values[1];
                    accommodationAddress = values[2];
                    costPerNight = Double.parseDouble(values[3]);
                    //Create an accommodation object to add to the
                    // accommodation array.
                    Accommodation accommodation = new Accommodation(location,
                            accommodationName, accommodationAddress,
                            costPerNight);
                    this.accommodationArrayList.add(accommodation);
                }
            }
            this.objReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("An error has occurred.");
        }
    }

    /**
     * Method to get the cost per night from a single object from the
     * accommodation array.
     * @param accommodationName Accommodation name provided by the program by
     *                         user choice.
     * @return double cost per night value.
     */
    public double getCost(String accommodationName) {
        double cost = 0;
        //Iterate the accommodation array to find the matching object.
        for (Accommodation i: this.accommodationArrayList) {
            if (i.getAccommodationName().equalsIgnoreCase(accommodationName)) {
                cost = i.getCostPerNight();
            }
        }
        return cost;
    }

    /**
     * Method to get address of a chosen accommodation.
     * @param accommodationName Accommodation name provided by the program by
     *                         user choice.
     * @return String address of the chosen accommodation.
     */
    public String getAddress(String accommodationName) {
        String address = "";
        //Iterate the accommodation array to find the matching object.
        for (Accommodation i: this.accommodationArrayList) {
            if (i.getAccommodationName().equalsIgnoreCase(accommodationName)) {
                address = i.getAccommodationAddress();
            }
        }
        return address;
    }
}