import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Rectangle2D square2D = new Rectangle2D.Double(0, 0, 500, 500);
        Point2D point2D = new Point2D.Double(square2D.getWidth()/2, square2D.getHeight()/2);
        float[] fractions = new float[5];
        fractions[0] = 0.1f;
        fractions[1] = 0.2f;
        fractions[2] = 0.3f;
        fractions[3] = 0.4f;
        fractions[4] = 0.5f;
        Color[] colorArray = new Color[5];
        colorArray[0] = Color.RED;
        colorArray[1] = Color.ORANGE;
        colorArray[2] = Color.YELLOW;
        colorArray[3] = Color.PINK;
        colorArray[4] = Color.BLACK;
        RadialGradientPaint radialGradientPaint = new RadialGradientPaint(point2D, 300, fractions, colorArray);
        graphics.setPaint(radialGradientPaint);
        graphics.fill(square2D);
        graphics.draw(square2D);
        graphics.setColor(Color.BLACK);

        canvas.setOnMouseDragged(event -> {
            Point2D point2D1 = new Point2D.Double(event.getX(), event.getY());
            RadialGradientPaint radialGradientPaint1 = new RadialGradientPaint(point2D1, 300, fractions, colorArray);
            graphics.setPaint(radialGradientPaint1);
            graphics.fill(square2D);
        });
    }


    public static void main(String[] args)
    {
        launch(GradientPaintExercise.class);
    }

}
