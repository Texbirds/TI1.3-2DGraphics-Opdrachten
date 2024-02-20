import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.util.ArrayList;

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    ArrayList<Block> blocks = new ArrayList<>();
    Block selectedBlock = null;
    double offsetX, offsetY;
    double cameraX = 0, cameraY = 0;
    double zoom = 1.0;


    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setOnScroll(e -> zoom(e));

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public void init() {
        blocks.add(new Block(0, 0, 50, 50, Color.RED));
        blocks.add(new Block(0, 50, 50, 50, Color.ORANGE));
        blocks.add(new Block(0, 100, 50, 50, Color.YELLOW));
        blocks.add(new Block(0, 150, 50, 50, Color.GREEN));
        blocks.add(new Block(0, 200, 50, 50, Color.BLUE));
        blocks.add(new Block(0, 250, 50, 50, Color.MAGENTA));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform cameraTransform = new AffineTransform();
        cameraTransform.translate(cameraX, cameraY);
        cameraTransform.scale(zoom, zoom);
        graphics.transform(cameraTransform);

        for (Block block : blocks) {
            block.draw(graphics);
        }
    }


    public static void main(String[] args)
    {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e) {
        if (e.isSecondaryButtonDown()) {
            offsetX = e.getX() - cameraX;
            offsetY = e.getY() - cameraY;
        } else {
            double mouseX = e.getX() / zoom - cameraX;
            double mouseY = e.getY() / zoom - cameraY;

            for (Block block : blocks) {
                if (block.contains(mouseX, mouseY)) {
                    selectedBlock = block;
                    offsetX = mouseX - block.getX();
                    offsetY = mouseY - block.getY();
                    break;
                }
            }
        }
    }

    private void mouseReleased(MouseEvent e) {
        selectedBlock = null;
    }

    private void mouseDragged(MouseEvent e) {
        if (e.isSecondaryButtonDown()) {
            cameraX = e.getX() - offsetX;
            cameraY = e.getY() - offsetY;
        } else if (selectedBlock != null) {
            double mouseX = e.getX() / zoom - cameraX;
            double mouseY = e.getY() / zoom - cameraY;
            selectedBlock.setPosition(mouseX - offsetX, mouseY - offsetY);
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        }
    }

    private void zoom(ScrollEvent e) {
        double delta = e.getDeltaY();
        if (delta > 0) {
            zoom *= 1.1;
        } else {
            zoom /= 1.1;
        }
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }
}
