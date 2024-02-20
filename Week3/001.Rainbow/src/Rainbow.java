import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Rainbow extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        String text = "regenboog";
        Font font = new Font("Arial", Font.PLAIN, 30);

        float hue = 0.0f;

        double angleIncrement = Math.PI / (text.length() - 1);
        double angle = -Math.PI;

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            String letterStr = String.valueOf(letter);

            graphics.setColor(Color.getHSBColor(hue, 1.0f, 1.0f));

            AffineTransform transform = new AffineTransform();
            transform.rotate(angle, canvas.getWidth() / 2, canvas.getHeight() / 2);
            graphics.setTransform(transform);

            graphics.setFont(font);
            graphics.drawString(letterStr, (float) (canvas.getWidth() / 2 + 100), (float) (canvas.getHeight() / 2));

            angle += angleIncrement;
            hue += 1.0f / text.length();
        }
    }


    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
