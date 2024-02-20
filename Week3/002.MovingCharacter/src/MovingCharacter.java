import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private BufferedImage[] tiles;

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

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }

    public void init()  {
        try {
            BufferedImage image = ImageIO.read(new File("C:/Users/kwint/OneDrive/Documenten/GitHub/TI1.3-2DGraphics-Opdrachten/Week3/002.MovingCharacter/resources/images/sprite.png"));
            tiles = new BufferedImage[65];
            for(int i = 0; i < 65; i++)
                tiles[i] = image.getSubimage(64 * (i%8), 64 * (i/8), 64, 64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        int imageWidth = 64;
        int imageHeight = 64;
        int spacing = 64;
        int imagesPerRow = 8;
        int row = 0;
        int col = 0;

        for (int i = 0; i < 65; i++) {
            int x = col * (imageWidth + spacing);
            int y = row * (imageHeight + spacing);
            graphics.drawImage(tiles[i], x, y, null);

            col++;
            if (col >= imagesPerRow) {
                col = 0;
                row++;
            }
        }
    }


    public void update(double deltaTime) {

    }

    public static void main(String[] args)
    {
        launch(MovingCharacter.class);
    }

}
