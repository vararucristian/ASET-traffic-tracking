package Data;

public class Street {
    private int id;
    private int nrLanes;
    private int idIntersection;


    public Street(int id, int nrLanes, int idIntersection) {
        this.id = id;
        this.nrLanes = nrLanes;

        this.idIntersection = idIntersection;
    }

    public int getId() {
        return id;
    }

    public int getNrLanes() {
        return nrLanes;
    }

    public void setNrLanes(int nrLanes) {
        this.nrLanes = nrLanes;
    }

}
