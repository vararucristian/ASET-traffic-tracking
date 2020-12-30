package Data;

public class TrafficLight {
    private int id;
    private Color currentColor;
    private Color newColor;

    public TrafficLight(int id, Color currentColor, Color newColor) {
        this.id = id;
        this.currentColor = currentColor;
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
