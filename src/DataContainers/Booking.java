package DataContainers;

import java.sql.Timestamp;

public class Booking {
    private final int       customerID;
    private final int       holidayID;
    private final int       magicianID;
    private final int       year;
    private final String    address;
    private final Timestamp timestamp;

    public Booking(int customerID, int holidayID, int magicianID, int year, String address, Timestamp timestamp) {
        this.customerID = customerID;
        this.holidayID = holidayID;
        this.magicianID = magicianID;
        this.year = year;
        this.address = address;
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public int getCustomerID() {
        return customerID;
    }

    public int getHolidayID() {
        return holidayID;
    }

    public int getMagicianID() {
        return magicianID;
    }

    public int getYear() {
        return year;
    }

    public String getAddress() {
        return address;
    }
    
    @Override
    public String toString() {
        return String.format("Booking{customerID=%d, holidayID=%d, magicianID=%d, year=%d, address=%s}", customerID, holidayID, magicianID, year, address);
    }
}
