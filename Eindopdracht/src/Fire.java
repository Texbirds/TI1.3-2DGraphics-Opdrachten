import java.awt.*;
import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fire {
    private static final int NUM_PARTICLES = 100;
    private static final double INITIAL_SPEED = 40;
    private static final double ANGLE_VARIATION = Math.PI / 2;
    private static final double EXPLOSION_SPEED = 10;

    private List<Particle> particles;
    private double gravity;
    private double x;
    private double radius;
    private double y;
    private double fadeTime;
    private boolean spawned;
    private boolean active;
    private boolean exploded;

    private long activationTime;

    public Fire(double x, double y) {
        this.x = x;
        this.y = y;
        this.particles = new ArrayList<>();
        this.spawned = false;
        this.active = false;
        this.activationTime = 0;
        this.radius = 10;
        this.gravity = -300;
        this.fadeTime = 0.5;
        exploded = false;
    }

    public void update(double deltaTime) {
//        if (!exploded) {
//            explode();
//        }
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
//        if (particles.size() >= 60000) {
//
//        }
    }

    private void explode() {
        Random random = new Random();
        for (int i = 0; i < NUM_PARTICLES; i++) {

        }
        exploded = true;
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