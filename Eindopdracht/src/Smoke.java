import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Smoke {
    private static final int NUM_PARTICLES = 10;
    private static final double INITIAL_SPEED = 20;
    private static final double EXPLOSION_SPEED = 30;

    private double radius = 15;
    private double gravity = -200;
    private double fadeTime = 1.0;

    private boolean spawned = false;

    private List<Particle> particles;
    private double x;
    private double y;


    public Smoke(double x, double y) {
        this.x = x;
        this.y = y;
        particles = new ArrayList<>();
    }

    public void update(double deltaTime) {
        if (!spawned) {
            spawn();
        } else {
            for (Particle particle : particles) {
                particle.update(deltaTime);
            }
        }
    }

    public void draw(FXGraphics2D graphics) {
        if (!spawned) {
            graphics.setColor(Color.WHITE);
        } else {
            for (Particle particle : particles) {
                Color particleColor = new Color(255, 255, 255, (int) (particle.getAlpha() * 255));
                graphics.setColor(particleColor);
                graphics.fillOval((int) particle.x, (int) particle.y, (int) particle.getRadius(), (int) particle.getRadius());
            }
        }
    }

    private void spawn() {
        Random random = new Random();
        for (int i = 0; i < NUM_PARTICLES; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double speed = random.nextDouble() * (EXPLOSION_SPEED - INITIAL_SPEED) + INITIAL_SPEED;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            particles.add(new Particle(x, y, vx, vy, radius, gravity, fadeTime));
        }
        spawned = true;
    }

    public boolean isFinished() {
        return spawned && particles.isEmpty();
    }
}
