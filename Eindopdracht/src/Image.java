import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Image {
    private static final int NUM_PARTICLES = 1;
    private static final double INITIAL_SPEED = 100;
    private static final double EXPLOSION_SPEED = 25;

    private double fadeTime = 0;
    private double radius = 20;
    private double gravity = 0;
    private long activationTime = 0;

    private boolean spawned = false;
    private boolean active = false;

    private List<Particle> particles;
    private double x;
    private double y;

    private BufferedImage bird;


    public Image(double x, double y) {
        this.x = x;
        this.y = y;
        this.particles = new ArrayList<>();
        this.spawned = false;
        this.active = false;


        init();
    }

    public void update(double deltaTime) {
        if (active) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - activationTime > 2500) {
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

    public void init() {
        try {
            bird = ImageIO.read(getClass().getResource("/bird.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void activate() {
        active = true;
        activationTime = System.currentTimeMillis();
    }

    public void draw(FXGraphics2D graphics) {
        if (active) {
            for (Particle particle : particles) {
                AffineTransform tx = new AffineTransform();
                tx.translate(particle.x, particle.y);
                tx.scale(0.1, 0.1);

                graphics.drawImage(bird, tx, null);
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
        if (particles.size() >= 100) {
            particles.remove(particles.size()-1);
        }
    }

    public boolean isFinished() {
        return spawned && particles.isEmpty();
    }
}
