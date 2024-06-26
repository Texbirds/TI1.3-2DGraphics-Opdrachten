import java.awt.*;
import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fire {
    private static final int NUM_PARTICLES = 5;
    private static final double INITIAL_SPEED = 40;
    private static final double EXPLOSION_SPEED = 100;

    private double radius = 20;
    private double fadeTime = 0.5;
    private double gravity = -300;
    private long activationTime = 0;

    private boolean spawned = false;
    private boolean active = false;

    private List<Particle> particles;

    private double x;
    private double y;


    public Fire(double x, double y) {
        this.x = x;
        this.y = y;
        this.particles = new ArrayList<>();
    }

    public void update(double deltaTime) {
        if (active) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - activationTime > 5000) {
                active = false;
                particles.clear();
                spawned = true;
                return;
            }

            spawnParticles();
            for (Particle particle : particles) {
                particle.update(deltaTime);
            }
        }
        System.out.println(particles.size());
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
            double angle = random.nextDouble() * 2 * Math.PI;
            double speed = random.nextDouble() * (EXPLOSION_SPEED - INITIAL_SPEED) + INITIAL_SPEED;
            double vx = Math.cos(angle) * speed;
            double vy = Math.sin(angle) * speed;
            particles.add(new Particle(x, y, vx, vy, radius, gravity, fadeTime));
        }
    }

    public boolean isFinished() {
        return spawned && particles.isEmpty();
    }
}