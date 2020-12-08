package DTOs.Commands;

import DTOs.DTOOperation;

public class AddTrafficCommand extends DTOOperation {
    private int idTrafficLight;
    private int nrCars;
    private String image;

    public AddTrafficCommand(int idTrafficLight, int nrCars, String image) {
        this.idTrafficLight = idTrafficLight;
        this.nrCars = nrCars;
        this.image = image;
    }

    public int getIdTrafficLight() {
        return idTrafficLight;
    }

    public void setIdTrafficLight(int idTrafficLight) {
        this.idTrafficLight = idTrafficLight;
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
