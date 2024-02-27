import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Line {

    private Color color;
    private int locationX1;
    private int locationY1;
    private int locationX2;
    private int locationY2;

    public Line(Color color, int locationX1, int locationY1, int locationX2, int locationY2) {
        this.color = color;
        this.locationX1 = locationX1;
        this.locationY1 = locationY1;
        this.locationX2 = locationX2;
        this.locationY2 = locationY2;
    }

    public void draw(FXGraphics2D gc) {
        gc.setColor(this.color);
        gc.drawLine(locationX1, locationY1, locationX2, locationY2);
    }
}
