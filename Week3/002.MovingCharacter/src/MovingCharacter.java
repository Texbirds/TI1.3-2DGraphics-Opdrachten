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

    private int currentFrame = 32; // Start frame of the walking animation
    private double characterX = 100; // Initial position of the character (right side of the screen)
    private double characterY = 300; // Fixed position of the character
    private int i = 0;
    private int j = 0;
    private double speed = 50;

    private boolean isJumping = false;
    private int jumpFrame = 41;

    @Override
    public void start(Stage stage) throws Exception
    {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        canvas.setOnMouseClicked(e -> jump());
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

        boolean flip = speed < 0;

        if (!flip) {
            graphics.drawImage(tiles[currentFrame], (int) characterX, (int) characterY, null);
        } else {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-(characterX + imageWidth), characterY);
            graphics.drawImage(tiles[currentFrame], tx, null);
        }
    }


    public void update(double deltaTime) {
        characterX += speed * deltaTime;

        i++;
        if (i % 20 == 0) {
            currentFrame++;
        }

        if (currentFrame > 39) {
            currentFrame = 32;
        }

        if (characterX + 64 > canvas.getWidth()) {
            characterX = canvas.getWidth() - 64;
            speed = -speed;
        }

        if (characterX < 0) {
            characterX = 0;
            speed = Math.abs(speed);
        }

        if (isJumping) {
            currentFrame = jumpFrame;
            if (i % 15 == 0) {
                jumpFrame++;
            }
            if (jumpFrame > 48) {
                jumpFrame = 41;
                isJumping = false;
            }
        }
    }

    private void jump() {
        if (!isJumping) {
            isJumping = true;
        }
    }

    public static void main(String[] args)
    {
        launch(MovingCharacter.class);
    }

}
