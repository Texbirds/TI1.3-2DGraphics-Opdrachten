
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class VerletEngine extends Application {

    private ResizableCanvas canvas;
    private ArrayList<Particle> particles = new ArrayList<>();
    private ArrayList<Constraint> constraints = new ArrayList<>();
    private PositionConstraint mouseConstraint = new PositionConstraint(null);

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        // Mouse Events
        canvas.setOnMouseClicked(e -> mouseClicked(e));
        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Verlet Engine");
        stage.show();
        draw(g2d);
    }

    public void init() {
        for (int i = 0; i < 20; i++) {
            particles.add(new Particle(new Point2D.Double(100 + 50 * i, 100)));
        }

        for (int i = 0; i < 10; i++) {
            constraints.add(new DistanceConstraint(particles.get(i), particles.get(i + 1)));
        }

        constraints.add(new PositionConstraint(particles.get(10)));
        constraints.add(mouseConstraint);
    }

    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (Constraint c : constraints) {
            c.draw(graphics);
        }

        for (Particle p : particles) {
            p.draw(graphics);
        }
    }

    private void update(double deltaTime) {
        for (Particle p : particles) {
            p.update((int) canvas.getWidth(), (int) canvas.getHeight());
        }

        for (Constraint c : constraints) {
            c.satisfy();
        }
    }

    // Functionaliteit:
    // MB1 = nieuw particle met distanceconstraint
    // MB1 + ctrl = nieuw particle met positionconstraint
    // MB1 + shift = nieuw particle met ropeconstraint

    // MB3 = nieuw particle met 2 distanceconstraints
    // MB3 + ctrl = nieuw particle met 2 distanceconstraints met beide dezelfde lengte (100)
    // MB3 + shift = nieuw constraint tussen 2 dichtsbijzijndste particles

    // MB2 = reset
    // MB2 + shift = spawnt een doek

    // Save functionaliteit:
    // MB1 + alt = saved gehele scene
    // MB3 + alt = laadt gehele scene
    private void mouseClicked(MouseEvent e) {
        Point2D mousePosition = new Point2D.Double(e.getX(), e.getY());
        Particle nearest = getNearest(mousePosition);
        Particle newParticle = new Particle(mousePosition);


        if (e.getButton() == MouseButton.SECONDARY) {
            Collections.sort(particles, (a, b) -> {
                return (int) Math.signum(a.getPosition().distance(mousePosition) -
                        b.getPosition().distance(mousePosition));
            });
            if (e.isControlDown()) {
                Particle particle = new Particle(mousePosition);
                constraints.add(new DistanceConstraint(particles.get(1), particle, 100));
                constraints.add(new DistanceConstraint(particles.get(0), particle, 100));
                particles.add(particle);
            } else if (e.isShiftDown()) {
                constraints.add(new DistanceConstraint(particles.get(0), particles.get(1)));
            } else if (e.isAltDown()) {
                loadFromFile();
            } else {
                particles.add(newParticle);
                constraints.add(new DistanceConstraint(newParticle, nearest));
                ArrayList<Particle> sorted = new ArrayList<>();
                sorted.addAll(particles);

                //sorteer alle elementen op afstand tot de muiscursor. De toegevoegde particle staat op 0, de nearest op 1, en de derde op 2
                Collections.sort(sorted, new Comparator<Particle>() {
                    @Override
                    public int compare(Particle o1, Particle o2) {
                        return (int) (o1.getPosition().distance(mousePosition) - o2.getPosition().distance(mousePosition));
                    }
                });
                constraints.add(new DistanceConstraint(newParticle, sorted.get(2)));
            }
        } else if (e.getButton() == MouseButton.MIDDLE) {
            if (e.isShiftDown()) {
                particles.clear();
                constraints.clear();
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        Particle doekParticle = new Particle(new Point2D.Double(100 * i + 50, 100 * j + 50));
                        particles.add(doekParticle);

                        if (j == 0) {
                            constraints.add(new PositionConstraint(doekParticle));
                        }

                        if (j > 0) {
                            Particle topNeighbor = particles.get((i * 10) + (j - 1));
                            constraints.add(new RopeConstraint(doekParticle, topNeighbor));
                        }
                    }
                }
                for (int i = 0; i < 90; i++) {
                    constraints.add(new RopeConstraint(particles.get(i), particles.get(i+10)));
                }
                constraints.add(mouseConstraint);
            } else {
                // Reset
                particles.clear();
                constraints.clear();
                init();
            }
        } else if (e.getButton() == MouseButton.PRIMARY) {
            if (e.isControlDown()) {
                particles.add(newParticle);
                constraints.add(new PositionConstraint(newParticle));
            } else if (e.isShiftDown()) {
                particles.add(newParticle);
                constraints.add(new RopeConstraint(newParticle, nearest));
            } else if (e.isAltDown()) {
                saveToFile();
            } else {
                particles.add(newParticle);
                constraints.add(new DistanceConstraint(newParticle, nearest));
            }
        }
    }

    private void saveToFile() {
        File saveFile = new File("Saves/SaveFile.ser");
        try {
            FileOutputStream fos = new FileOutputStream(saveFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(particles);
            oos.writeObject(constraints);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        String saveFilePath = "Saves/SaveFile.ser";
        try{
            FileInputStream fis = new FileInputStream(saveFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            particles = (ArrayList<Particle>) ois.readObject();
            constraints = (ArrayList<Constraint>) ois.readObject();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Particle getNearest(Point2D point) {
        Particle nearest = particles.get(0);
        for (Particle p : particles) {
            if (p.getPosition().distance(point) < nearest.getPosition().distance(point)) {
                nearest = p;
            }
        }
        return nearest;
    }

    private void mousePressed(MouseEvent e) {
        Point2D mousePosition = new Point2D.Double(e.getX(), e.getY());
        Particle nearest = getNearest(mousePosition);
        if (nearest.getPosition().distance(mousePosition) < 10) {
            mouseConstraint.setParticle(nearest);
        }
    }

    private void mouseReleased(MouseEvent e) {
        mouseConstraint.setParticle(null);
    }

    private void mouseDragged(MouseEvent e) {
        mouseConstraint.setFixedPosition(new Point2D.Double(e.getX(), e.getY()));
    }

    public static void main(String[] args) {
        launch(VerletEngine.class);
    }

}
