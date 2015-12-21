package Database;

public class DatabaseStrings {
    
    //URL needed to be done this way to get it to connect
    static final String DATABASE_URL = "jdbc:derby://localhost:1527/MagicianAgent";
    static final String DATABASE_USER = "java";
    static final String DATABASE_PASS = "java";
    
    static final String BOOKINGS_TABLE_NAME  = "Bookings";
    static final String MAGICIANS_TABLE_NAME = "Magicians";
    static final String HOLIDAYS_TABLE_NAME  = "Holidays";
    static final String CUSTOMERS_TABLE_NAME = "Customers";
    static final String WAITLIST_TABLE_NAME  = "Waitlist";
    
    static final String BOOKINGS_TABLE_COLUMN_CUSTOMER_ID = "CustomerID";
    static final String BOOKINGS_TABLE_COLUMN_HOLIDAY_ID  = "HolidayID";
    static final String BOOKINGS_TABLE_COLUMN_MAGICIAN_ID = "MagicianID";
    static final String BOOKINGS_TABLE_COLUMN_YEAR_BOOKED = "YearBooked";
    static final String BOOKINGS_TABLE_COLUMN_ADDRESS     = "Address";
    static final String BOOKINGS_TABLE_COLUMN_TIMESTAMP   = "Timestamp";
    
    static final String CUSTOMERS_TABLE_COLUMN_ID    = "ID";
    static final String CUSTOMERS_TABLE_COLUMN_NAME  = "Name";
    static final String CUSTOMERS_TABLE_COLUMN_PHONE = "Phone";
    
    static final String HOLIDAYS_TABLE_COLUMN_ID    = "ID";
    static final String HOLIDAYS_TABLE_COLUMN_NAME  = "Name";
    static final String HOLIDAYS_TABLE_COLUMN_MONTH = "Month";
    static final String HOLIDAYS_TABLE_COLUMN_DAY   = "Day";
    
    static final String MAGICIANS_TABLE_COLUMN_ID   = "ID";
    static final String MAGICIANS_TABLE_COLUMN_NAME = "Name";
    
    static final String WAITLIST_TABLE_COLUMN_CUSTOMER_ID = "CustomerID";
    static final String WAITLIST_TABLE_COLUMN_HOLIDAY_ID  = "HolidayID";
    static final String WAITLIST_TABLE_COLUMN_TIMEADDED   = "TimeAdded";
    static final String WAITLIST_TABLE_COLUMN_YEAR_BOOKED = "YearBooked";
    static final String WAITLIST_TABLE_COLUMN_ADDRESS     = "Address";
    
    static final String GET_AVAILABLE_MAGICIANS_QUERY = 
            "SELECT " + MAGICIANS_TABLE_COLUMN_ID + ", " + MAGICIANS_TABLE_COLUMN_NAME + 
            " FROM " + MAGICIANS_TABLE_NAME + " WHERE " + MAGICIANS_TABLE_COLUMN_ID + " NOT IN " + 
                "( SELECT " + BOOKINGS_TABLE_COLUMN_MAGICIAN_ID + " FROM " + BOOKINGS_TABLE_NAME + 
                    " WHERE " + BOOKINGS_TABLE_NAME + "." + BOOKINGS_TABLE_COLUMN_HOLIDAY_ID + " = ? " + 
                    "AND " + BOOKINGS_TABLE_NAME + "." + BOOKINGS_TABLE_COLUMN_YEAR_BOOKED + " = ?)";
    
    static final String BOOK_MAGICIAN_QUERY = 
            "INSERT INTO " + BOOKINGS_TABLE_NAME + 
            " (" + BOOKINGS_TABLE_COLUMN_CUSTOMER_ID + ", " + BOOKINGS_TABLE_COLUMN_HOLIDAY_ID + ", " + BOOKINGS_TABLE_COLUMN_MAGICIAN_ID + 
                ", " + BOOKINGS_TABLE_COLUMN_YEAR_BOOKED + ", " + BOOKINGS_TABLE_COLUMN_ADDRESS + ", " + BOOKINGS_TABLE_COLUMN_TIMESTAMP + ") " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    
    static final String ADD_TO_WAITLIST_QUERY = 
            "INSERT INTO " + WAITLIST_TABLE_NAME +
            " (" + WAITLIST_TABLE_COLUMN_CUSTOMER_ID + ", " + WAITLIST_TABLE_COLUMN_HOLIDAY_ID + ", " + WAITLIST_TABLE_COLUMN_TIMEADDED + 
                ", " + WAITLIST_TABLE_COLUMN_YEAR_BOOKED + ", " + WAITLIST_TABLE_COLUMN_ADDRESS + ") " +
            "VALUES (?, ?, ?, ?, ?)";
    
    static final String GET_HOLIDAY_STATUS_QUERY = 
            "SELECT " + BOOKINGS_TABLE_COLUMN_ADDRESS + "," + BOOKINGS_TABLE_COLUMN_CUSTOMER_ID + 
                "," + BOOKINGS_TABLE_COLUMN_MAGICIAN_ID + "," + BOOKINGS_TABLE_COLUMN_TIMESTAMP + " " +
            "FROM " + BOOKINGS_TABLE_NAME + " " +
            "WHERE " + BOOKINGS_TABLE_COLUMN_HOLIDAY_ID + " = ? AND " + BOOKINGS_TABLE_COLUMN_YEAR_BOOKED + " = ?";
    
    static final String GET_MAGICIAN_STATUS_QUERY = 
            "SELECT " + BOOKINGS_TABLE_COLUMN_ADDRESS + "," + BOOKINGS_TABLE_COLUMN_CUSTOMER_ID + 
                "," + BOOKINGS_TABLE_COLUMN_HOLIDAY_ID + "," + BOOKINGS_TABLE_COLUMN_YEAR_BOOKED +
                "," + BOOKINGS_TABLE_COLUMN_TIMESTAMP + " " +
            "FROM " + BOOKINGS_TABLE_NAME + " " +
            "WHERE " + BOOKINGS_TABLE_COLUMN_MAGICIAN_ID + " = ?";
    
    static final String GET_WAITLIST_STATUS_QUERY = 
            "SELECT " + WAITLIST_TABLE_COLUMN_ADDRESS + "," + WAITLIST_TABLE_COLUMN_CUSTOMER_ID + "," + WAITLIST_TABLE_COLUMN_HOLIDAY_ID +
                "," + WAITLIST_TABLE_COLUMN_TIMEADDED + "," + WAITLIST_TABLE_COLUMN_YEAR_BOOKED + " " +
            "FROM " + WAITLIST_TABLE_NAME + " " +
            "ORDER BY " + WAITLIST_TABLE_COLUMN_TIMEADDED + " ASC";
    
    static final String GET_MAGICIANS_QUERY = 
            "SELECT " + MAGICIANS_TABLE_COLUMN_ID + "," + MAGICIANS_TABLE_COLUMN_NAME + " " + 
            "FROM " + MAGICIANS_TABLE_NAME;
    
    static final String GET_MAGICIAN_FROM_ID_QUERY =
            "SELECT " + MAGICIANS_TABLE_COLUMN_NAME + " " +
            "FROM " + MAGICIANS_TABLE_NAME + " " +
            "WHERE " + MAGICIANS_TABLE_COLUMN_ID + " = ?";
    
    static final String GET_MAGICIAN_ID_FROM_NAME_QUERY =
            "SELECT " + MAGICIANS_TABLE_COLUMN_ID + " " +
            "FROM " + MAGICIANS_TABLE_NAME + " " +
            "WHERE " + MAGICIANS_TABLE_COLUMN_NAME + " = ?";
    
    static final String GET_HOLIDAYS_QUERY =
            "SELECT " + HOLIDAYS_TABLE_COLUMN_DAY + "," + HOLIDAYS_TABLE_COLUMN_ID + "," +
                HOLIDAYS_TABLE_COLUMN_MONTH + "," + HOLIDAYS_TABLE_COLUMN_NAME + " " +
            "FROM " + HOLIDAYS_TABLE_NAME;
    
    static final String GET_HOLIDAY_FROM_ID_QUERY =
            "SELECT " + HOLIDAYS_TABLE_COLUMN_DAY + "," + HOLIDAYS_TABLE_COLUMN_MONTH + "," + HOLIDAYS_TABLE_COLUMN_NAME + " " +
            "FROM " + HOLIDAYS_TABLE_NAME + " " +
            "WHERE " + HOLIDAYS_TABLE_COLUMN_ID + " = ?";
    
    static final String GET_HOLIDAY_ID_FROM_DATE_QUERY = 
            "SELECT " + HOLIDAYS_TABLE_COLUMN_ID + " " +
            "FROM " + HOLIDAYS_TABLE_NAME + " " +
            "WHERE " + HOLIDAYS_TABLE_COLUMN_MONTH + " = ? AND " + HOLIDAYS_TABLE_COLUMN_DAY + " = ?";
    
    static final String GET_HOLIDAY_ID_FROM_NAME_QUERY =
            "SELECT " + HOLIDAYS_TABLE_COLUMN_ID + " " +
            "FROM " + HOLIDAYS_TABLE_NAME + " " +
            "WHERE " + HOLIDAYS_TABLE_COLUMN_NAME + " = ?";
    
    static final String GET_CUSTOMERS_QUERY =
            "SELECT " + CUSTOMERS_TABLE_COLUMN_ID + "," + CUSTOMERS_TABLE_COLUMN_NAME + "," + CUSTOMERS_TABLE_COLUMN_PHONE + " " +
            "FROM " + CUSTOMERS_TABLE_NAME;
    
    static final String GET_CUSTOMER_FROM_ID_QUERY =
            "SELECT " + CUSTOMERS_TABLE_COLUMN_NAME + "," + CUSTOMERS_TABLE_COLUMN_PHONE + " " +
            "FROM " + CUSTOMERS_TABLE_NAME + " " +
            "WHERE " + CUSTOMERS_TABLE_COLUMN_ID + " = ?";
    
    static final String GET_CUSTOMER_ID_FROM_INFO_QUERY = 
            "SELECT " + CUSTOMERS_TABLE_COLUMN_ID + " " + 
            "FROM " + CUSTOMERS_TABLE_NAME + " " +
            "WHERE " + CUSTOMERS_TABLE_COLUMN_NAME + " = ? AND " + CUSTOMERS_TABLE_COLUMN_PHONE + " = ?";
    
    static final String ADD_CUSTOMER_QUERY = 
            "INSERT INTO " + CUSTOMERS_TABLE_NAME + " " +
            " (" + CUSTOMERS_TABLE_COLUMN_NAME + "," + CUSTOMERS_TABLE_COLUMN_PHONE + ") " +
            "VALUES (?, ?)";
    
    static final String DELETE_BOOKING_QUERY =
            "DELETE FROM " + BOOKINGS_TABLE_NAME + " " +
            "WHERE " + BOOKINGS_TABLE_COLUMN_CUSTOMER_ID + " = ? AND " +
                BOOKINGS_TABLE_COLUMN_HOLIDAY_ID + " = ? AND " +
                BOOKINGS_TABLE_COLUMN_MAGICIAN_ID + " = ? AND " +
                BOOKINGS_TABLE_COLUMN_YEAR_BOOKED + " = ?";
    
    static final String DELETE_WAITLIST_QUERY =
            "DELETE FROM " + WAITLIST_TABLE_NAME + " " +
            "WHERE " + WAITLIST_TABLE_COLUMN_CUSTOMER_ID + " = ? AND " +
                WAITLIST_TABLE_COLUMN_HOLIDAY_ID + " = ? AND " +
                WAITLIST_TABLE_COLUMN_YEAR_BOOKED  + " = ? AND " +
                WAITLIST_TABLE_COLUMN_TIMEADDED + " = ?";
    
    static final String ADD_MAGICIAN_QUERY =
            "INSERT INTO " + MAGICIANS_TABLE_NAME + " " +
            "(" + MAGICIANS_TABLE_COLUMN_NAME + ") " +
            "VALUES(?)";
    
    static final String DROP_MAGICIAN_QUERY = 
            "DELETE FROM " + MAGICIANS_TABLE_NAME + " " +
            "WHERE " + MAGICIANS_TABLE_COLUMN_ID + " = ?";
    
    static final String ADD_HOLIDAY_QUERY =
            "INSERT INTO " + HOLIDAYS_TABLE_NAME + " " +
            "(" + HOLIDAYS_TABLE_COLUMN_DAY + "," + HOLIDAYS_TABLE_COLUMN_MONTH + "," + HOLIDAYS_TABLE_COLUMN_NAME + ") " +
            "VALUES(?,?,?)";
}
