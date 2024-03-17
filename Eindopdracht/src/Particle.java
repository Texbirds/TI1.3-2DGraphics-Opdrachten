
import java.awt.*;

public class Particle {
    public double x;
    public double y;
    private double vx;
    private double vy;
    private double radius;

    public Particle(double x, double y, double vx, double vy, double radius) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
        vy += Firework.GRAVITY * deltaTime;
    }

    public double getRadius() {
        return radius;
    }
}