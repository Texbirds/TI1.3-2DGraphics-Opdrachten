import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Colors extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Rectangle2D square1 = new Rectangle2D.Double(20, 20, 20, 30);
        Rectangle2D square2 = new Rectangle2D.Double(40, 20, 20, 30);
        Rectangle2D square3 = new Rectangle2D.Double(60, 20, 20, 30);
        Rectangle2D square4 = new Rectangle2D.Double(80, 20, 20, 30);
        Rectangle2D square5 = new Rectangle2D.Double(100, 20, 20, 30);
        Rectangle2D square6 = new Rectangle2D.Double(120, 20, 20, 30);
        Rectangle2D square7 = new Rectangle2D.Double(140, 20, 20, 30);
        Rectangle2D square8 = new Rectangle2D.Double(160, 20, 20, 30);
        Rectangle2D square9 = new Rectangle2D.Double(180, 20, 20, 30);
        Rectangle2D square10 = new Rectangle2D.Double(200, 20, 20, 30);
        Rectangle2D square11 = new Rectangle2D.Double(220, 20, 20, 30);
        Rectangle2D square12 = new Rectangle2D.Double(240, 20, 20, 30);
        Rectangle2D square13 = new Rectangle2D.Double(260, 20, 20, 30);

        graphics.setColor(Color.BLACK);
        graphics.fill(square1);
        graphics.setColor(Color.BLUE);
        graphics.fill(square2);
        graphics.setColor(Color.CYAN);
        graphics.fill(square3);
        graphics.setColor(Color.DARK_GRAY);
        graphics.fill(square4);
        graphics.setColor(Color.GRAY);
        graphics.fill(square5);
        graphics.setColor(Color.GREEN);
        graphics.fill(square6);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fill(square7);
        graphics.setColor(Color.MAGENTA);
        graphics.fill(square8);
        graphics.setColor(Color.ORANGE);
        graphics.fill(square9);
        graphics.setColor(Color.PINK);
        graphics.fill(square10);
        graphics.setColor(Color.RED);
        graphics.fill(square11);
        graphics.setColor(Color.WHITE);
        graphics.fill(square12);
        graphics.setColor(Color.YELLOW);
        graphics.fill(square13);
        graphics.setColor(Color.BLACK);

        graphics.draw(square1);
        graphics.draw(square2);
        graphics.draw(square3);
        graphics.draw(square4);
        graphics.draw(square5);
        graphics.draw(square6);
        graphics.draw(square7);
        graphics.draw(square8);
        graphics.draw(square9);
        graphics.draw(square10);
        graphics.draw(square11);
        graphics.draw(square12);
        graphics.draw(square13);


    }


    public static void main(String[] args)
    {
        launch(Colors.class);
    }

}
