public class Particle {

    public double x;
    public double y;
    private double vx;
    private double vy;
    private double radius;
    private double gravity;
    private double fadeTime;

    private double age = 0;
    private double alpha = 1.0;

    public Particle(double x, double y, double vx, double vy, double radius, double gravity, double fadeTime) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.gravity = gravity;
        this.fadeTime = fadeTime;
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
        vy += gravity * deltaTime;
        age += deltaTime;

        alpha = 1.0 - Math.min(age / fadeTime, 1.0);
    }

    public double getRadius() {
        return radius;
    }

    public double getAlpha() {
        return alpha;
    }
}