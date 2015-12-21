package DataContainers;

public class Holiday {
    private final int    id;
    private final String name;
    private final int    month;
    private final int    day;

    public Holiday(int id, String name, int month, int day) {
        this.id = id;
        this.name = name;
        this.month = month;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
    
    @Override
    public String toString() {
        return String.format("Holiday{id=%d, name=\"%s\", month=%d, day=%d}", id, name, month, day);
    }
}
