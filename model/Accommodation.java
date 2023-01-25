package model;

/**
 * This class contains the blueprint for accommodation record obtained from
 * the files provided.
 */
public class Accommodation {
    private String location;
    private String accommodationName;
    private String accommodationAddress;
    private double costPerNight;

    /**
     * Method to get the accommodation name.
     * @return A copy of the accommodation name.
     */
    public String getAccommodationName() {
        return accommodationName;
    }

    /**
     * Method to get the accommodation address.
     * @return A copy of the accommodation address.
     */
    public String getAccommodationAddress() {
        return accommodationAddress;
    }

    /**
     * Method to get the cost per night.
     * @return A copy of the cost per night.
     */
    public double getCostPerNight() {
        return costPerNight;
    }

    /**
     * This is the class constructor that creates the accommodation object.
     * @param location Location of the accommodation. Used to compare with
     *                 user entered destination.
     * @param accommodationName Name of the accommodation.
     * @param accommodationAddress Address of the accommodation.
     * @param costPerNight Cost per night at accommodation.
     */
    public Accommodation(String location, String accommodationName,
                         String accommodationAddress, double costPerNight) {
        this.location = location;
        this.accommodationName = accommodationName;
        this.accommodationAddress = accommodationAddress;
        this.costPerNight = costPerNight;
    }
}