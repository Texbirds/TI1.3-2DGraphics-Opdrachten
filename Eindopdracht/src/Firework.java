import java.awt.*;
import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Firework {
    private static final int NUM_PARTICLES = 500;
    private static final double INITIAL_SPEED = 300;
    private static final double EXPLOSION_SPEED = 700;

    private double radius = 10;
    private double fadeTime = 2.0;
    private double gravity = 200;

    private boolean exploded = false;

    private List<Particle> particles;
    private double x;
    private double y;

    private Color color;

    public Firework(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        particles = new ArrayList<>();
    }

    public void update(double deltaTime) {
        if (!exploded) {
            explode();
        } else {
            for (Particle particle : particles) {
                particle.update(deltaTime);
            }
        }
    }

    public void draw(FXGraphics2D graphics) {
        if (!exploded) {
            graphics.setColor(color);
            graphics.fillOval((int) x, (int) y, 10, 10);
        } else {
            for (Particle particle : particles) {
                Color particleColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (particle.getAlpha() * 255));
                graphics.setColor(particleColor);
                graphics.fillOval((int) particle.x, (int) particle.y, (int) particle.getRadius(), (int) particle.getRadius());
            }
        }
    }

    private void explode() {
        Random random = new Random();
        for (int i = 0; i < NUM_PARTICLES; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double speed = random.nextDouble() * (EXPLOSION_SPEED - INITIAL_SPEED) + INITIAL_SPEED;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            particles.add(new Particle(x, y, vx, vy, radius, gravity, fadeTime));
        }
        exploded = true;
    }

    public boolean isFinished() {
        return exploded && particles.isEmpty();
    }
}