package Database;

import DataContainers.Booking;
import DataContainers.Customer;
import DataContainers.Holiday;
import DataContainers.Magician;
import DataContainers.WaitlistEntry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler {

    PreparedStatement getAvailableMagicians;
    PreparedStatement bookMagicians;
    PreparedStatement addToWaitlist;
    PreparedStatement getHolidayStatus;
    PreparedStatement getMagicianStatus;
    PreparedStatement getWaitlistStatus;
    PreparedStatement getMagicians;
    PreparedStatement getMagicianFromID;
    PreparedStatement getMagicianIDFromName;
    PreparedStatement getHolidays;
    PreparedStatement getHolidayFromID;
    PreparedStatement getHolidayIDFromDate;
    PreparedStatement getHolidayIDFromName;
    PreparedStatement getCustomers;
    PreparedStatement getCustomerFromID;
    PreparedStatement getCustomerIDFromInfo;
    PreparedStatement addCustomer;
    PreparedStatement deleteBooking;
    PreparedStatement deleteWaitlist;
    PreparedStatement addMagician;
    PreparedStatement dropMagician;
    PreparedStatement addHoliday;
    
    private static final DatabaseHandler INSTANCE = new DatabaseHandler();
    
    private Connection connection;
    private boolean isConnected = false;
    
    public static void main (String[] args) {
        try {
            DatabaseHandler.getInstance().connect();
            for (int i = 0; i < 5; ++i) {
                DatabaseHandler.getInstance().tryBookAndWaitlist(i, 1, 2015, "poop");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private DatabaseHandler() {
        connect();
    }
    
    public static DatabaseHandler getInstance() {
        return INSTANCE;
    }
    
    public final boolean connect() {
        try {
            if (isConnected) {
                connection.close();
                isConnected = false; //just in case it fails later
            }
            
            connection = DriverManager.getConnection(DatabaseStrings.DATABASE_URL, DatabaseStrings.DATABASE_USER, DatabaseStrings.DATABASE_PASS);
            isConnected = true; //If we got here, we did it
            
            getAvailableMagicians = connection.prepareStatement(DatabaseStrings.GET_AVAILABLE_MAGICIANS_QUERY);
            bookMagicians         = connection.prepareStatement(DatabaseStrings.BOOK_MAGICIAN_QUERY);
            addToWaitlist         = connection.prepareStatement(DatabaseStrings.ADD_TO_WAITLIST_QUERY);
            getHolidayStatus      = connection.prepareStatement(DatabaseStrings.GET_HOLIDAY_STATUS_QUERY);
            getMagicianStatus     = connection.prepareStatement(DatabaseStrings.GET_MAGICIAN_STATUS_QUERY);
            getWaitlistStatus     = connection.prepareStatement(DatabaseStrings.GET_WAITLIST_STATUS_QUERY);
            getMagicians          = connection.prepareStatement(DatabaseStrings.GET_MAGICIANS_QUERY);
            getMagicianFromID     = connection.prepareStatement(DatabaseStrings.GET_MAGICIAN_FROM_ID_QUERY);
            getMagicianIDFromName = connection.prepareStatement(DatabaseStrings.GET_MAGICIAN_ID_FROM_NAME_QUERY);
            getHolidays           = connection.prepareStatement(DatabaseStrings.GET_HOLIDAYS_QUERY);
            getHolidayFromID      = connection.prepareStatement(DatabaseStrings.GET_HOLIDAY_FROM_ID_QUERY);
            getHolidayIDFromDate  = connection.prepareStatement(DatabaseStrings.GET_HOLIDAY_ID_FROM_DATE_QUERY);
            getHolidayIDFromName  = connection.prepareStatement(DatabaseStrings.GET_HOLIDAY_ID_FROM_NAME_QUERY);
            getCustomers          = connection.prepareStatement(DatabaseStrings.GET_CUSTOMERS_QUERY);
            getCustomerFromID     = connection.prepareStatement(DatabaseStrings.GET_CUSTOMER_FROM_ID_QUERY);
            getCustomerIDFromInfo = connection.prepareStatement(DatabaseStrings.GET_CUSTOMER_ID_FROM_INFO_QUERY);
            addCustomer           = connection.prepareStatement(DatabaseStrings.ADD_CUSTOMER_QUERY);
            deleteBooking         = connection.prepareStatement(DatabaseStrings.DELETE_BOOKING_QUERY);
            deleteWaitlist        = connection.prepareStatement(DatabaseStrings.DELETE_WAITLIST_QUERY);
            addMagician           = connection.prepareStatement(DatabaseStrings.ADD_MAGICIAN_QUERY);
            dropMagician          = connection.prepareStatement(DatabaseStrings.DROP_MAGICIAN_QUERY);
            addHoliday            = connection.prepareStatement(DatabaseStrings.ADD_HOLIDAY_QUERY);
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isConnected;
    }
    
    public int tryBook(int customerID, int holidayID, int year, String address) throws SQLException {
        return tryBook(customerID, holidayID, year, address, new Timestamp(Calendar.getInstance().getTime().getTime()));
    }
    
    public int tryBook(int customerID, int holidayID, int year, String address, Timestamp t) throws SQLException {
        if (!isConnected) {
            throw new SQLException("Not connected to database!");
        }
        
        Magician m = getAvailableMagician(holidayID, year);
        
        if (m == null) {
            return -1;
        } else {
            bookMagicians.setInt(1, customerID);
            bookMagicians.setInt(2, holidayID);
            bookMagicians.setInt(3, m.getId());
            bookMagicians.setInt(4, year);
            bookMagicians.setString(5, address);
            bookMagicians.setTimestamp(6, t);
            
            bookMagicians.executeUpdate();
            
            return m.getId();
        }
    }
    
    //Returns -1 if waitlisted, magicianID otherwise
    public int tryBookAndWaitlist(int customerID, int holidayID, int year, String address) throws SQLException {
        return tryBookAndWaitlist(customerID, holidayID, year, address, new Timestamp(Calendar.getInstance().getTime().getTime()));
    }
    
    public int tryBookAndWaitlist(int customerID, int holidayID, int year, String address, Timestamp t) throws SQLException {
        int result = tryBook(customerID, holidayID, year, address);
        
        if (result == -1) {
            addToWaitlist.setInt(1, customerID);
            addToWaitlist.setInt(2, holidayID);
            addToWaitlist.setTimestamp(3, t);
            addToWaitlist.setInt(4, year);
            addToWaitlist.setString(5, address);
            
            addToWaitlist.executeUpdate();
        }
        
        return result;
    }
    
    public Magician getAvailableMagician(int holidayID, int year) throws SQLException {
        if (!isConnected) {
            throw new SQLException("Not connected to database!");
        }

        getAvailableMagicians.setInt(1, holidayID);
        getAvailableMagicians.setInt(2, year);

        ResultSet results = getAvailableMagicians.executeQuery();

        if (!results.next()){
            return null;
        }
        
        //Next bit grabs a random magician from the available ones
        //That way, one guy isn't booked for every holiday with the others doing nothing
        ArrayList<Magician> magicians = new ArrayList<>();
        
        do {
            int id      = results.getInt(DatabaseStrings.MAGICIANS_TABLE_COLUMN_ID);
            String name = results.getString(DatabaseStrings.MAGICIANS_TABLE_COLUMN_NAME);
            magicians.add(new Magician(id, name));
        } while (results.next());
        
        Random r = new Random();
        int magicianIndex = r.nextInt(magicians.size());
        
        return magicians.get(magicianIndex);
    }
    
    public ArrayList<Booking> getHolidayStatus(int holidayID, int year) throws SQLException {
        ArrayList<Booking> bookings = new ArrayList<>();
        
        getHolidayStatus.setInt(1, holidayID);
        getHolidayStatus.setInt(2, year);
        
        ResultSet results = getHolidayStatus.executeQuery();
        
        while (results.next()) {
            int customerID = results.getInt(DatabaseStrings.BOOKINGS_TABLE_COLUMN_CUSTOMER_ID);
            int magicianID = results.getInt(DatabaseStrings.BOOKINGS_TABLE_COLUMN_MAGICIAN_ID);
            String address = results.getString(DatabaseStrings.BOOKINGS_TABLE_COLUMN_ADDRESS);
            Timestamp t    = results.getTimestamp(DatabaseStrings.BOOKINGS_TABLE_COLUMN_TIMESTAMP);
            
            bookings.add(new Booking(customerID, holidayID, magicianID, year, address, t));
        }
        
        return bookings;
    }
    
    public ArrayList<Booking> getMagicianStatus(int magicianID) throws SQLException {
        ArrayList<Booking> bookings = new ArrayList<>();
        
        getMagicianStatus.setInt(1, magicianID);
        
        ResultSet results = getMagicianStatus.executeQuery();
        
        while (results.next()) {
            int customerID = results.getInt(DatabaseStrings.BOOKINGS_TABLE_COLUMN_CUSTOMER_ID);
            int holidayID  = results.getInt(DatabaseStrings.BOOKINGS_TABLE_COLUMN_HOLIDAY_ID);
            int year       = results.getInt(DatabaseStrings.BOOKINGS_TABLE_COLUMN_YEAR_BOOKED);
            String address = results.getString(DatabaseStrings.BOOKINGS_TABLE_COLUMN_ADDRESS);
            Timestamp t    = results.getTimestamp(DatabaseStrings.BOOKINGS_TABLE_COLUMN_TIMESTAMP);
            
            bookings.add(new Booking(customerID, holidayID, magicianID, year, address, t));
        }
        
        return bookings;
    }
    
    public ArrayList<WaitlistEntry> getWaitlistStatus() throws SQLException {
        ArrayList<WaitlistEntry> entries = new ArrayList<>();
        
        ResultSet results = getWaitlistStatus.executeQuery();
        
        while (results.next()) {
            int customerID = results.getInt(DatabaseStrings.WAITLIST_TABLE_COLUMN_CUSTOMER_ID);
            int holidayID  = results.getInt(DatabaseStrings.WAITLIST_TABLE_COLUMN_HOLIDAY_ID);
            int year       = results.getInt(DatabaseStrings.WAITLIST_TABLE_COLUMN_YEAR_BOOKED);
            Timestamp time = results.getTimestamp(DatabaseStrings.WAITLIST_TABLE_COLUMN_TIMEADDED);
            String address = results.getString(DatabaseStrings.WAITLIST_TABLE_COLUMN_ADDRESS);
            
            entries.add(new WaitlistEntry(customerID, holidayID, time, year, address));
        }
        
        return entries;
    }
    
    public ArrayList<Magician> getMagicians() throws SQLException {
        ArrayList<Magician> entries = new ArrayList<>();
        
        ResultSet results = getMagicians.executeQuery();
        
        while (results.next()) {
            int id      = results.getInt(DatabaseStrings.MAGICIANS_TABLE_COLUMN_ID);
            String name = results.getString(DatabaseStrings.MAGICIANS_TABLE_COLUMN_NAME);
            
            entries.add(new Magician(id, name));
        }
        
        return entries;
    }
    
    public Magician getMagicianFromID(int magicianID) throws SQLException {
        getMagicianFromID.setInt(1, magicianID);
        ResultSet results = getMagicianFromID.executeQuery();
        
        if (!results.next()) {
            return null;
        } else {
            String name = results.getString(DatabaseStrings.MAGICIANS_TABLE_COLUMN_NAME);
            
            return new Magician(magicianID, name);
        }
    }
    
    //returns first if there are multiple
    public int getMagicianIDFromName(String name) throws SQLException {
        getMagicianIDFromName.setString(1, name);
        ResultSet results = getMagicianIDFromName.executeQuery();
        
        if (!results.next()) {
            return -1;
        } else {
            return results.getInt(DatabaseStrings.MAGICIANS_TABLE_COLUMN_ID);
        }
    }
    
    public ArrayList<Holiday> getHolidays() throws SQLException {
        ArrayList<Holiday> entries = new ArrayList<>();
        
        ResultSet results = getHolidays.executeQuery();
        
        while (results.next()) {
            int id      = results.getInt(DatabaseStrings.HOLIDAYS_TABLE_COLUMN_ID);
            String name = results.getString(DatabaseStrings.HOLIDAYS_TABLE_COLUMN_NAME);
            int month   = results.getInt(DatabaseStrings.HOLIDAYS_TABLE_COLUMN_MONTH);
            int day     = results.getInt(DatabaseStrings.HOLIDAYS_TABLE_COLUMN_DAY);
            
            entries.add(new Holiday(id, name, month, day));
        }
        
        return entries;
    }
    
    public Holiday getHolidayFromID(int holidayID) throws SQLException {
        getHolidayFromID.setInt(1, holidayID);
        ResultSet results = getHolidayFromID.executeQuery();
        
        if (!results.next()) {
            return null;
        } else {
            String name = results.getString(DatabaseStrings.HOLIDAYS_TABLE_COLUMN_NAME);
            int month   = results.getInt(DatabaseStrings.HOLIDAYS_TABLE_COLUMN_MONTH);
            int day     = results.getInt(DatabaseStrings.HOLIDAYS_TABLE_COLUMN_DAY);
            
            return new Holiday(holidayID, name, month, day);
        }
    }
    
    public int getHolidayIDFromDate(int month, int day) throws SQLException {
        getHolidayIDFromDate.setInt(1, month);
        getHolidayIDFromDate.setInt(2, day);
        ResultSet results = getHolidayIDFromDate.executeQuery();
        
        if (!results.next()) {
            return -1;
        } else {
            return results.getInt(DatabaseStrings.HOLIDAYS_TABLE_COLUMN_ID);
        }
    }
    
    public int getHolidayIDFromName(String name) throws SQLException {
        getHolidayIDFromName.setString(1, name);
        ResultSet results = getHolidayIDFromName.executeQuery();
        
        if (!results.next()) {
            return -1;
        } else {
            return results.getInt(DatabaseStrings.HOLIDAYS_TABLE_COLUMN_ID);
        }
    }
    
    public ArrayList<Customer> getCustomers() throws SQLException {
        ArrayList<Customer> entries = new ArrayList<>();
        
        ResultSet results = getCustomers.executeQuery();
        
        while (results.next()) {
            int id       = results.getInt(DatabaseStrings.CUSTOMERS_TABLE_COLUMN_ID);
            String name  = results.getString(DatabaseStrings.CUSTOMERS_TABLE_COLUMN_NAME);
            String phone = results.getString(DatabaseStrings.CUSTOMERS_TABLE_COLUMN_PHONE);
            
            entries.add(new Customer(id, name, phone));
        }
        
        return entries;
    }
    
    public Customer getCustomerFromID(int customerID) throws SQLException {
        getCustomerFromID.setInt(1, customerID);
        ResultSet results = getCustomerFromID.executeQuery();
        
        if (!results.next()) {
            return null;
        } else {
            String name  = results.getString(DatabaseStrings.CUSTOMERS_TABLE_COLUMN_NAME);
            String phone = results.getString(DatabaseStrings.CUSTOMERS_TABLE_COLUMN_PHONE);
            
            return new Customer(customerID, name, phone);
        }
    }
    
    public int getCustomerIDFromInfo(String name, String phone) throws SQLException {
        getCustomerIDFromInfo.setString(1, name);
        getCustomerIDFromInfo.setString(2, phone);
        ResultSet results = getCustomerIDFromInfo.executeQuery();
        
        if (!results.next()) {
            return -1;
        } else {
            return results.getInt(DatabaseStrings.CUSTOMERS_TABLE_COLUMN_ID);
        }
    }
    
    public int addCustomer(String name, String phone) throws SQLException {
        addCustomer.setString(1, name);
        addCustomer.setString(2, phone);
        
        addCustomer.executeUpdate();
        
        return getCustomerIDFromInfo(name, phone);
    }
    
    public void deleteBooking(int customerID, int magicianID, int holidayID, int year) throws SQLException {
        deleteBooking.setInt(1, customerID);
        deleteBooking.setInt(2, holidayID);
        deleteBooking.setInt(3, magicianID);
        deleteBooking.setInt(4, year);
        
        deleteBooking.execute();
    }
    
    public void deleteWaitlistEntry(int customerID, int holidayID, int year, long timestamp) throws SQLException {
        deleteWaitlist.setInt(1, customerID);
        deleteWaitlist.setInt(2, holidayID);
        deleteWaitlist.setInt(3, year);
        deleteWaitlist.setTimestamp(4, new Timestamp(timestamp));
        
        deleteWaitlist.execute();
    }
    
    public void addMagician(String name) throws SQLException {
        addMagician.setString(1, name);
        addMagician.execute();
    }
    
    public void dropMagician(int id) throws SQLException {
        dropMagician.setInt(1, id);
        dropMagician.execute();
    }
    
    public void addHoliday(int month, int day, String name) throws SQLException {
        addHoliday.setInt(1, month);
        addHoliday.setInt(2, day);
        addHoliday.setString(3, name);
        addHoliday.execute();
    }
}
