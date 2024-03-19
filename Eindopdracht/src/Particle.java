public class Particle {
    private static final double INITIAL_ALPHA = 1.0;

    public double x;
    public double y;
    private double vx;
    private double vy;
    private double radius;
    private double age;
    private double alpha;
    private double gravity;
    private double fadeTime;

    public Particle(double x, double y, double vx, double vy, double radius, double gravity, double fadeTime) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.gravity = gravity;
        this.age = 0;
        this.alpha = INITIAL_ALPHA;
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