import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.*;

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        // assenstelsel
        graphics.setColor(Color.BLACK);
        graphics.draw(new Line2D.Double(canvasWidth / 2, 0, canvasWidth / 2, canvasHeight)); // Y-axis
        graphics.draw(new Line2D.Double(0, canvasHeight / 2, canvasWidth, canvasHeight / 2)); // X-axis

        // lijntje
        graphics.setColor(Color.BLUE);
        GeneralPath line = new GeneralPath();
        line.moveTo(0, (canvasHeight / 2 + 2.5 * canvasWidth / 2));
        line.lineTo(canvasWidth, (canvasHeight / 2 - 2.5 * canvasWidth / 2));
        graphics.draw(line);

        // doos
        double squareWidth = 100;
        double squareHeight = 100;
        Rectangle2D.Double square = new Rectangle2D.Double((canvasWidth/2 - squareWidth/2), (canvasHeight/2 - squareHeight/2 - 150), squareHeight, squareWidth);
        graphics.setColor(Color.RED);
        graphics.draw(square);

        // doos versie 2

    }

    public static void main(String[] args)
    {
        launch(Mirror.class);
    }

}
