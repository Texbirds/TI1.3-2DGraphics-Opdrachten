import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {
    private TextField v1;
    private TextField v2;
    private TextField v3;
    private TextField v4;

    private Canvas canvas;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(1920, 1080);
       
        VBox mainBox = new VBox();
        HBox topBar = new HBox();
        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(new Group(canvas));
        
        topBar.getChildren().add(v1 = new TextField("20"));
        topBar.getChildren().add(v2 = new TextField("1"));
        topBar.getChildren().add(v3 = new TextField("20"));
        topBar.getChildren().add(v4 = new TextField("10"));
                
        v1.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v2.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v3.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        v4.textProperty().addListener(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));
        
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        //you can use Double.parseDouble(v1.getText()) to get a double value from the first textfield
        //feel free to add more textfields or other controls if needed, but beware that swing components might clash in naming
        graphics.setTransform(new AffineTransform());
        graphics.setColor(Color.white);
        graphics.fillRect(0,0,1920,1080);
        graphics.setColor(Color.black);
        graphics.translate(this.canvas.getWidth()/2, this.canvas.getHeight()/2);
        graphics.scale(1, -1);
        double resolution = 0.1;
        double scale = 10.0;
        double lastX = 0;
        double lastY = 0;
        double n = 0.1;

        //        x = a × cos(b × i) + c × cos(d × i)
        //        y = a × sin(b × i) + c × sin(d × i)

        for (double i = 0; i < 100; i += 0.1) {
            double x = Double.parseDouble(v1.getText()) * Math.cos(Double.parseDouble(v2.getText()) * i) + Double.parseDouble(v3.getText()) * Math.cos(Double.parseDouble(v4.getText()) * i);
            double y = Double.parseDouble(v1.getText()) * Math.sin(Double.parseDouble(v2.getText()) * i) + Double.parseDouble(v3.getText()) * Math.sin(Double.parseDouble(v4.getText()) * i);
            graphics.draw(new Line2D.Double(lastX * scale, lastY * scale, x * scale, y * scale));
            lastX = x;
            lastY = y;
        }
    }
    
    
    
    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
