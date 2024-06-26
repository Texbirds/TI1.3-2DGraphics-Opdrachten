
import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;

public class AngryBirds extends Application {

    private ResizableCanvas canvas;
    private World world;
    private MousePicker mousePicker;
    private Camera camera;
    private boolean debugSelected = false;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    private double mousePressX;
    private double mousePressY;
    private double mouseReleaseX;
    private double mouseReleaseY;
    private boolean isDragging = false;
    private int index;
    private Vector2 startVector;
    private Vector2 forceVector;

    private Body bird;
    private Body birdHolder;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();

        // Add debug button
        javafx.scene.control.CheckBox showDebug = new CheckBox("Show debug");
        showDebug.setOnAction(e -> {
            debugSelected = showDebug.isSelected();
        });
        mainPane.setTop(showDebug);

        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1) {
                    last = now;
                }
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        canvas.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.MIDDLE) {
                try {
                    init();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            mousePressed(e);
        });
        canvas.setOnMouseReleased(e -> mouseReleased(e));

        stage.setScene(new Scene(mainPane, 1920, 1080));
        stage.setTitle("Angry Birds");
        stage.show();
        draw(g2d);
    }

    private void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseButton.MIDDLE) {
            return;
        }
        mousePressX = e.getX();
        mousePressY = e.getY();
        startVector = new Vector2(e.getX(), e.getY());
    }

    private void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseButton.MIDDLE) {
            return;
        }
        mouseReleaseX = e.getX();
        mouseReleaseY = e.getY();

        Vector2 forceVector = new Vector2(startVector.x-e.getX(), e.getY()-startVector.y);
        this.bird.setMass(MassType.NORMAL);
        this.bird.applyForce(forceVector);
    }

    public void init() throws IOException {
        double floorSize = 23.4375;
        double wallLocationX = floorSize + (floorSize/2);

        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        Body background = new Body();
        background.addFixture(Geometry.createRectangle(1, 1));
        background.setMass(MassType.INFINITE);
        gameObjects.add(new GameObject("/background.png", background, new Vector2(0, 0), 1.8));
        index = gameObjects.size()-1;

        Body floor = new Body();
        floor.addFixture(Geometry.createRectangle(floorSize, 1));
        floor.getTransform().setTranslation(0, -5);
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);
        gameObjects.add(new GameObject("/floor.png", floor, new Vector2(0,0), 1.6));

        Body floor2 = new Body();
        floor2.addFixture(Geometry.createRectangle(floorSize, 1));
        floor2.getTransform().setTranslation(floorSize, -5);
        floor2.setMass(MassType.INFINITE);
        world.addBody(floor2);
        gameObjects.add(new GameObject("/floor.png", floor2, new Vector2(0,0), 1.6));

        Body floor3 = new Body();
        floor3.addFixture(Geometry.createRectangle(floorSize, 1));
        floor3.getTransform().setTranslation(-floorSize, -5);
        floor3.setMass(MassType.INFINITE);
        world.addBody(floor3);
        gameObjects.add(new GameObject("/floor.png", floor3, new Vector2(0,0), 1.6));

        Body wall2 = new Body();
        wall2.addFixture(Geometry.createRectangle(0.15, 10));
        wall2.getTransform().setTranslation(-wallLocationX,0);
        wall2.setMass(MassType.INFINITE);
        world.addBody(wall2);

        Body wall1 = new Body();
        wall1.addFixture(Geometry.createRectangle(0.15, 10));
        wall1.getTransform().setTranslation(wallLocationX,0);
        wall1.setMass(MassType.INFINITE);
        world.addBody(wall1);

        bird = new Body();
        bird.addFixture(Geometry.createCircle(0.2));
        bird.getTransform().setTranslation(-5,-3);
        bird.setMass(MassType.INFINITE);
        bird.getFixture(0).setRestitution(0.15);
        world.addBody(bird);
        bird.setBullet(true);
        gameObjects.add(new GameObject("/bird.png", bird, new Vector2(0,0), 0.1));

        createObject(6, -3.45, 0.3586, 3, "/woodVertical.png", true);
        createObject(8.8, -3.45, 0.3586, 3, "/woodVertical.png", true);
        createObject(7.5, 3.2-5, 3, 0.3586, "/woodHorizontal.png", true);
        createObject(6.75, 4.9-5, 0.3586, 3, "/woodVertical.png", true);
        createObject(8.25, 4.9-5, 0.3586, 3, "/woodVertical.png", true);
        createObject(7.5, 6.4-5, 3, 0.25, "/woodHorizontal.png", true);
    }

    private void createObject(double locationX, double locationY, double width, double height, String imageFile, boolean useImage) {
        Body object = new Body();
        object.addFixture(Geometry.createRectangle(width, height));
        object.getTransform().setTranslation(locationX, locationY);
        object.setMass(MassType.INFINITE);
        world.addBody(object);
        if (useImage) {
            object.setMass(MassType.NORMAL);
            gameObjects.add(new GameObject(imageFile, object, new Vector2(0, 0), 1.65));
        }
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        AffineTransform originalTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));
        graphics.scale(1, -1);

        if (debugSelected) {
            graphics.setColor(Color.blue);
            DebugDraw.draw(graphics, world, 100);
        }

        for (GameObject go : gameObjects) {
            go.draw(graphics);
        }

        graphics.setTransform(originalTransform);
    }

    public void update(double deltaTime) {
        world.update(deltaTime);

        //werkt niet omdat canvas.getwidth/2 en de andere niet automatisch translaten naar de camera
//        gameObjects.remove(index);
//        Body background = new Body();
//        background.addFixture(Geometry.createRectangle(1, 1));
//        background.getTransform().setTranslation(canvas.getWidth()/2,canvas.getHeight()/2);
//        background.setMass(MassType.INFINITE);
//        gameObjects.add(new GameObject("/background.png", background, new Vector2(0, 0), 1.1));
//        index = gameObjects.size()-1;
    }

    public static void main(String[] args) {
        launch(AngryBirds.class);
    }

}
