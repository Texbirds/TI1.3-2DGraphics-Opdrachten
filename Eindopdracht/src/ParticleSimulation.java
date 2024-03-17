import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

public class ParticleSimulation extends Application {

    private List<Firework> fireworks;
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        fireworks = new ArrayList<>();

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
//        canvas.setOnMouseDragged(e -> mouseDragged(e));

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Particle simulation");
        stage.show();
        draw(g2d);
    }

    public void init() {

    }

    private void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            fireworks.add(new Firework(e.getX(), e.getY(), createRandomColor()));
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
    }

    private void update(double deltaTime) {
        for (Firework firework : fireworks) {
            firework.update(deltaTime);
        }

        fireworks.removeIf(Firework::isFinished);
    }
}