package Data;

public class TrafficLight {
    private int id;
    private Color currentColor;
    private String image;

    public TrafficLight(int id, Color currentColor, String image) {
        this.id = id;
        this.currentColor = currentColor;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

}
