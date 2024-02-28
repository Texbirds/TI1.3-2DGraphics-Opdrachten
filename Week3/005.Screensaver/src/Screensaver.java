import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private Point2D[] points;
    private Point2D[] velocities;
    private ArrayList<Line> history;
    private int startList = 0;
    private int updateCounter;
    private boolean drawNow;

    @Override
    public void start(Stage stage) throws Exception
    {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now)
            {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        canvas.setOnMouseClicked(event -> {
            points[0] = new Point2D(150, 100);
            points[1] = new Point2D(300, 100);
            points[2] = new Point2D(300, 250);
            points[3] = new Point2D(150, 250);

            velocities[0] = new Point2D(-50, -50);
            velocities[1] = new Point2D(-50, 50);
            velocities[2] = new Point2D(50, -50);
            velocities[3] = new Point2D(50,50);
        });

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.black);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setColor(Color.MAGENTA);

        for (int i = 0; i < 4; i++) {
            Point2D point1 = points[i];
            Point2D point2 = points[(i + 1) % 4];
            graphics.drawLine((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY());
            history.add(new Line((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY()));
        }

        for (int i = startList; i < history.size(); i++) {
           graphics.drawLine(history.get(i).getLocationX1(), history.get(i).getLocationY1(), history.get(i).getLocationX2(), history.get(i).getLocationY2());
        }
        if (history.size() >= 400) {
            startList = history.size() - 200;
        }
        updateCounter++;
    }

    public void init() {
        points = new Point2D[4];
        velocities = new Point2D[4];
        history = new ArrayList<>();

        points[0] = new Point2D(150, 100);
        points[1] = new Point2D(300, 100);
        points[2] = new Point2D(300, 250);
        points[3] = new Point2D(150, 250);

        velocities[0] = new Point2D(-50, -50);
        velocities[1] = new Point2D(-50, 50);
        velocities[2] = new Point2D(50, -50);
        velocities[3] = new Point2D(50,50);
    }

    public void update(double deltaTime) {
        for (int i = 0; i < 4; i++) {
            Point2D point = points[i];
            Point2D velocity = velocities[i];
            point = point.add(velocity.multiply(deltaTime));
            if (point.getX() <= 0 || point.getX() >= canvas.getWidth())
                velocity = new Point2D(-velocity.getX(), velocity.getY());
            if (point.getY() <= 0 || point.getY() >= canvas.getHeight())
                velocity = new Point2D(velocity.getX(), -velocity.getY());
            points[i] = point;
            velocities[i] = velocity;
            Point2D point1 = points[i];
            Point2D point2 = points[(i + 1) % 4];
            history.add(new Line((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY()));
        }
    }

    public static void main(String[] args)
    {
        launch(Screensaver.class);
    }

}
