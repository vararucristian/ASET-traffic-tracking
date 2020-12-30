package Data;

public class Traffic {
    int id;
    int trafficLightId;
    int carsNr;
    String base64Img;

    public Traffic(int id, int trafficLightId, int carsNr, String base64Img) {
        this.id = id;
        this.trafficLightId = trafficLightId;
        this.carsNr = carsNr;
        this.base64Img = base64Img;
    }

    public int getId() {
        return id;
    }

    public int getTrafficLightId() {
        return trafficLightId;
    }

    public int getCarsNr() {
        return carsNr;
    }

    public String getbase64Img() {
        return base64Img;
    }
}
