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

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private BufferedImage image1;
    private BufferedImage image2;
    private float alpha = 0.0f;
    private boolean fadingIn = true;

    @Override
    public void start(Stage stage) throws Exception {
        loadImages();

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }

    private void loadImages() {
        try {
            this.image1 = ImageIO.read(new File("C:\\Users\\kwint\\OneDrive\\Documenten\\GitHub\\TI1.3-2DGraphics-Opdrachten\\Week3\\003.FadingImage\\resources\\images\\img_1.png"));
            this.image2 = ImageIO.read(new File("C:\\Users\\kwint\\OneDrive\\Documenten\\GitHub\\TI1.3-2DGraphics-Opdrachten\\Week3\\003.FadingImage\\resources\\images\\img.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        int width = (int)canvas.getWidth();
        int height = (int)canvas.getHeight();

        graphics.drawImage(image1, 0, 0, width, height, null);

        if (alpha > 0) {
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            graphics.drawImage(image1, 0, 0, width, height, null);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1-alpha));
            graphics.drawImage(image2, 0, 0, width, height, null);
        }
    }

    public void update(double deltaTime) {
        final float fadeSpeed = 0.5f;
        if (fadingIn) {
            alpha += fadeSpeed * deltaTime;
            if (alpha >= 1.0f) {
                alpha = 1.0f;
                fadingIn = false;
            }
        } else {
            alpha -= fadeSpeed * deltaTime;
            if (alpha <= 0.0f) {
                alpha = .0f;
                fadingIn = true;
            }
        }
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }
}