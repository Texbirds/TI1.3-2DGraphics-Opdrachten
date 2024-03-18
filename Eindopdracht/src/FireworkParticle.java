public class FireworkParticle {
    public static final double FADE_TIME = 1.0;
    private static final double INITIAL_ALPHA = 1.0;

    public double x;
    public double y;
    private double vx;
    private double vy;
    private double radius;
    private double age;
    private double alpha;

    public FireworkParticle(double x, double y, double vx, double vy, double radius) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.age = 0;
        this.alpha = INITIAL_ALPHA;
    }

    public void update(double deltaTime) {
        x += vx * deltaTime;
        y += vy * deltaTime;
        vy += Firework.GRAVITY * deltaTime;
        age += deltaTime;

        alpha = 1.0 - Math.min(age / FADE_TIME, 1.0);
    }

    public double getRadius() {
        return radius;
    }

    public double getAlpha() {
        return alpha;
    }
}