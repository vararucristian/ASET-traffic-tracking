package Data;

import java.util.List;

public class Intersection {
    private int id;
    private List<Street> streetsList;
    private String name;

    public Intersection(int id,  String name) {
        this.id = id;
        this.streetsList = null;
        this.name = name;
    }

    public Intersection(int id, List<Street> streetsList, String name) {
        this.id = id;
        this.streetsList = streetsList;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public List<Street> getStreetsList() {
        return streetsList;
    }

    public void setStreetsList(List<Street> streetsList) {
        this.streetsList = streetsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
