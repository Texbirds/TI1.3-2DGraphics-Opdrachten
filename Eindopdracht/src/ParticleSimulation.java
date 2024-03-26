import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.ResizableCanvas;

public class ParticleSimulation extends Application {

    private List<Smoke> smokes;
    private List<Firework> fireworks;
    private List<Fire> fires;
    private List<Image> images;
    private ResizableCanvas canvas;
    private boolean activateSmoke;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        mainPane.setTop(createButtonBar());
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        fireworks = new ArrayList<>();
        smokes = new ArrayList<>();
        fires = new ArrayList<>();
        images = new ArrayList<>();
        activateSmoke = false;


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
//        canvas.setOnMouseClicked(e -> mouseClicked(e));
        canvas.setOnMousePressed(e -> mousePressed(e));
//        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Particle simulation");
        stage.show();
        draw(g2d);
    }

    private HBox createButtonBar() {
        Button fireButton = new Button("Fire");
        Button fireworkButton = new Button("Firework");
        Button smokeButton = new Button("Smoke");
        Button imageButton = new Button("Image");

        HBox buttonBar = new HBox(fireButton, fireworkButton, smokeButton, imageButton);
        buttonBar.setSpacing(10);
        buttonBar.setAlignment(Pos.CENTER);

        return buttonBar;
    }


    private void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            fireworks.add(new Firework(e.getX(), e.getY(), createRandomColor()));
            activateSmoke = false;
        } else if (e.getButton() == MouseButton.PRIMARY) {
            activateSmoke = true;
        } else if (e.getButton() == MouseButton.MIDDLE) {
            if (e.isShiftDown()) {
                Image currentImage = new Image(e.getX(), e.getY());
                images.add(currentImage);
                currentImage.activate();
            } else {
                Fire currentFire = new Fire(e.getX(), e.getY());
                fires.add(currentFire);
                currentFire.activate();
            }
            activateSmoke = false;
        } else {
            activateSmoke = false;
        }
    }

    private void mouseDragged(MouseEvent e) {
        if (activateSmoke) {
            smokes.add(new Smoke(e.getX(), e.getY()));
        }
    }

    private Color createRandomColor() {
        Random random = new Random();
        return Color.getHSBColor(random.nextFloat(), 1, 1);
    }

    private void draw(FXGraphics2D graphics) {
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        for (Firework firework : fireworks) {
            firework.draw(graphics);
        }

        for (Smoke smoke : smokes) {
            smoke.draw(graphics);
        }

        for (Fire fire : fires) {
            fire.draw(graphics);
        }

        for (Image image : images) {
            image.draw(graphics);
        }
    }

    private void update(double deltaTime) {
        for (Firework firework : fireworks) {
            firework.update(deltaTime);
        }

        for (Smoke smoke : smokes) {
            smoke.update(deltaTime);
        }

        for (Fire fire : fires) {
            fire.update(deltaTime);
        }

        for (Image image : images) {
            image.update(deltaTime);
        }

        fires.removeIf(Fire::isFinished);
        fireworks.removeIf(Firework::isFinished);
        smokes.removeIf(Smoke::isFinished);
        images.removeIf(Image::isFinished);
    }
}