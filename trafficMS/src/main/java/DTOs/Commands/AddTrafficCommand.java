package DTOs.Commands;

import DTOs.DTOOperation;

public class AddTrafficCommand extends DTOOperation {
    private int streetId;
    private int nrCars;
    private String image;

    public AddTrafficCommand(int streetId, int nrCars, String image) {
        this.streetId = streetId;
        this.nrCars = nrCars;
        this.image = image;
    }

    public int getStreetId() {
        return streetId;
    }

    public void setStreetId(int streetId) {
        this.streetId = streetId;
    }

    public int getNrCars() {
        return nrCars;
    }

    public void setNrCars(int nrCars) {
        this.nrCars = nrCars;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
