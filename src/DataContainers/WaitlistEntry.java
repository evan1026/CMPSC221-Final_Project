package DataContainers;

import java.sql.Timestamp;

public class WaitlistEntry {
    private final int       customerID;
    private final int       holidayID;
    private final Timestamp timeAdded;
    private final int       year;
    private final String    address;

    public WaitlistEntry(int customerID, int holidayID, Timestamp timeAdded, int year, String address) {
        this.customerID = customerID;
        this.holidayID = holidayID;
        this.timeAdded = timeAdded;
        this.year = year;
        this.address = address;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getHolidayID() {
        return holidayID;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public int getYear() {
        return year;
    }

    public String getAddress() {
        return address;
    }
    
    @Override
    public String toString() {
        return String.format("WaitlistEntry{customerID=%d, holidayID=%d, timeAdded=%s, year=%d, address=\"%s\"}", customerID, holidayID, timeAdded, year, address);
    }
}
