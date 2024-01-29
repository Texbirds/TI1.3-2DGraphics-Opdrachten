import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Line2D;

public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1024, 768);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.draw(new Line2D.Double(200, 500, 400, 500));
        graphics.draw(new Line2D.Double(200, 500, 200, 300));
        graphics.draw(new Line2D.Double(400, 500, 400, 300));
        graphics.draw(new Line2D.Double(200, 300, 300, 200));
        graphics.draw(new Line2D.Double(400, 300, 300, 200));

        //deur
        graphics.draw(new Line2D.Double(220, 500, 220, 420));
        graphics.draw(new Line2D.Double(265, 500, 265, 420));
        graphics.draw(new Line2D.Double(220, 420, 265, 420));

        //ramen
        graphics.draw(new Line2D.Double(295, 460, 380, 460));
        graphics.draw(new Line2D.Double(295, 400, 380, 400));
        graphics.draw(new Line2D.Double(295, 400, 295, 460));
        graphics.draw(new Line2D.Double(380, 400, 380, 460));
    }



    public static void main(String[] args) {
        launch(House.class);
    }

}
