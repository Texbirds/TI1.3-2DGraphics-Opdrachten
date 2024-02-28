import org.jfree.fx.FXGraphics2D;

public class Line {

    private int locationX1;
    private int locationY1;
    private int locationX2;
    private int locationY2;

    public Line(int locationX1, int locationY1, int locationX2, int locationY2) {
        this.locationX1 = locationX1;
        this.locationY1 = locationY1;
        this.locationX2 = locationX2;
        this.locationY2 = locationY2;
    }

    public void draw(FXGraphics2D gc) {
        gc.drawLine(locationX1, locationY1, locationX2, locationY2);
    }

    public int getLocationX1() {
        return locationX1;
    }

    public int getLocationY1() {
        return locationY1;
    }

    public int getLocationX2() {
        return locationX2;
    }

    public int getLocationY2() {
        return locationY2;
    }
}