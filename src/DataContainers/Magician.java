package DataContainers;

public class Magician {
    private final int    id;
    private final String name;

    public Magician(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return String.format("Magician{id=%d, name=\"%s\"}", id, name);
    }
}
