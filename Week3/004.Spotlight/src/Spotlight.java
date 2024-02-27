import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private double mouseX = -1;
    private double mouseY = -1;
    private ArrayList<Line> lines = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        canvas.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            draw(g2d);
        });

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Spotlight");
        stage.show();
        draw(g2d);

        Random r = new Random();
        for (int i = 0; i < 600; i++) {
            float currentColor = r.nextFloat();
            int locationX1 = r.nextInt((int) canvas.getWidth());
            int locationY1 = r.nextInt((int) canvas.getHeight());
            int locationX2 = r.nextInt((int) canvas.getWidth());
            int locationY2 = r.nextInt((int) canvas.getHeight());


            g2d.setPaint(Color.getHSBColor(currentColor, 1, 1));
            g2d.drawLine((int) (locationX1 % canvas.getWidth()), (int) (locationY1 % canvas.getHeight()), (int) (locationX2 % canvas.getWidth()), (int) (locationY2 % canvas.getHeight()));

            lines.add(new Line((Color.getHSBColor(currentColor, 1, 1)), locationX1, locationY1, locationX2, locationY2));
        }
    }


    public void draw(FXGraphics2D g2d) {
        g2d.setTransform(new AffineTransform());
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        g2d.draw(new Ellipse2D.Double(mouseX-75, mouseY-75, 150, 150));
        g2d.setClip(new Ellipse2D.Double(mouseX-75, mouseY-75, 150, 150));
        for (Line line : lines) {
            line.draw(g2d);
        }
        g2d.setClip(null);
        g2d.setColor(Color.BLACK);
    }

    public static void main(String[] args)
    {
        launch(Spotlight.class);
    }

}
