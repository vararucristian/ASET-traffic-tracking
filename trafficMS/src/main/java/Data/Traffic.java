package Data;

public class Traffic {
    int id;
    int streetId;
    int carsNr;
    String base64Img;

    public Traffic(int id, int streetId, int carsNr, String base64Img) {
        this.id = id;
        this.streetId = streetId;
        this.carsNr = carsNr;
        this.base64Img = base64Img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStreetId() {
        return streetId;
    }

    public void setStreetId(int streetId) {
        this.streetId = streetId;
    }

    public void setCarsNr(int carsNr) {
        this.carsNr = carsNr;
    }

    public String getBase64Img() {
        return base64Img;
    }

    public void setBase64Img(String base64Img) {
        this.base64Img = base64Img;
    }

    public int getCarsNr() {
        return carsNr;
    }

    public String getbase64Img() {
        return base64Img;
    }
}
