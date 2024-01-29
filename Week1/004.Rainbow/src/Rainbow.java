import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        for(int i = 0; i < 10000; i++) {
            int radiusBinnen = 400;
            int radiusBuiten = 500;
            float hoek = i/3141.5f;
            float x1 = (float) (radiusBinnen * -Math.cos(hoek) + (1920 / 2));
            float y1 = (float) (radiusBinnen * -Math.sin(hoek) + (1080 / 2));
            float x2 = (float) (radiusBuiten * -Math.cos(hoek) + (1920 / 2));
            float y2 = (float) (radiusBuiten * -Math.sin(hoek) + (1080 / 2));
            graphics.setColor(Color.getHSBColor(i/10000.0f, 1, 1));
            graphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
