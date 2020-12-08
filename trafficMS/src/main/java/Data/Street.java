package Data;

public class Street {
    private int id;
    private int nrLanes;
    private TrafficLight trafficLight;

    public Street(int id, int nrLanes, TrafficLight trafficLight) {
        this.id = id;
        this.nrLanes = nrLanes;
        this.trafficLight = trafficLight;
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

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }
}
