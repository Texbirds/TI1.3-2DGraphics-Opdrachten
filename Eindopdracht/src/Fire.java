import java.awt.*;
import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fire {
    private static final int NUM_PARTICLES = 100;
    private static final double INITIAL_SPEED = 40;
    private static final double ANGLE_VARIATION = Math.PI / 2;

    private List<Particle> particles;
    private double gravity;
    private double x;
    private double radius;
    private double y;
    private double fadeTime;
    private boolean spawned;
    private boolean active;

    private long activationTime;

    public Fire(double x, double y) {
        this.x = x;
        this.y = y;
        this.particles = new ArrayList<>();
        this.spawned = false;
        this.active = false;
        this.activationTime = 0;
        this.radius = 15;
        this.gravity = -200;
        this.fadeTime = 2.0;
    }

    public void update(double deltaTime) {
        if (active) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - activationTime > 5000) {
                active = false;
                return;
            }

            spawnParticles();
            for (Particle particle : particles) {
                particle.update(deltaTime);
            }
        }
    }

    public void activate() {
        active = true;
        activationTime = System.currentTimeMillis();
    }

    public void draw(FXGraphics2D graphics) {
        if (active) {
            for (Particle particle : particles) {
                int alphaCalculated = (int) (particle.getAlpha()*255);
                Color particleColor = new Color(255, alphaCalculated, 1, alphaCalculated);
                graphics.setColor(particleColor);
                graphics.fillOval((int) particle.x, (int) particle.y, (int) particle.getRadius(), (int) particle.getRadius());
            }
        }
    }


    private void spawnParticles() {
        Random random = new Random();
        for (int i = 0; i < NUM_PARTICLES; i++) {
            double angle = Math.PI + random.nextDouble() * ANGLE_VARIATION * 2 - ANGLE_VARIATION;
            double speed = random.nextDouble() * INITIAL_SPEED;
            double vx = Math.cos(angle) * speed;
            double vy = -Math.abs(Math.sin(angle) * speed);
            particles.add(new Particle(x, y, vx, vy, 3, 0, fadeTime));
        }
    }

    public boolean isFinished() {
        return spawned && particles.isEmpty();
    }
}