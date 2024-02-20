import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Block {
    private double x;
    private double y;
    private double width;
    private double height;
    private Color color;
    private boolean selected;
    private double dragOffsetX;
    private double dragOffsetY;

    public Block(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void draw(FXGraphics2D gc) {
        gc.setColor(this.color);
        gc.fill(new Rectangle2D.Double(x, y, width, height));
    }

    public boolean contains(double x, double y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setDragOffset(double offsetX, double offsetY) {
        this.dragOffsetX = offsetX;
        this.dragOffsetY = offsetY;
    }

    public double getDragOffsetX() {
        return dragOffsetX;
    }

    public double getDragOffsetY() {
        return dragOffsetY;
    }
}
