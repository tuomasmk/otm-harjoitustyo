package tetris.ui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tetris.Direction;
import tetris.Piece;
import tetris.Square;
import tetris.SquarePiece;


public class Main extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }
        
    @Override
    public void start(Stage stage) throws InterruptedException {
        final int gameHeight = 480;
        final int gameWidth = 640;
        
        stage.setTitle("Tetris");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        Canvas canvas = new Canvas(gameWidth, gameHeight);
        root.getChildren().add(canvas);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Piece piece1 = new SquarePiece(gameWidth/2, 0, 20);
        Rectangle rect = new Rectangle(50, 50, 20, 20);
               
        
        new AnimationTimer() {
            double newY = rect.getY() + rect.getHeight();
            public void handle(long currentNanoTime) {
                gc.clearRect(0, 0, gameWidth, gameHeight);
//                gc.setFill(Color.RED);
//                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
//                rect.setY(newY);
//                newY = rect.getY() + rect.getHeight() / 10;
//                if(newY+rect.getHeight() > gameHeight) {
//                    newY = 0;
//                }
                gc.setFill(Color.CYAN);
                for(Square square : piece1.getParts()) {
                    gc.fillRect(square.getX(), square.getY(), square.getSize(), square.getSize());
                }
                piece1.move(Direction.DOWN, 5);
                if(piece1.touches(gameWidth, gameHeight)) {
                    piece1.moveTo(gameWidth/2, 0);
                }
            }
        }.start();
        
        scene.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.UP)) {
                ;
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                piece1.move(Direction.DOWN);
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                piece1.move(Direction.RIGHT);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                piece1.move(Direction.LEFT);
            }
        });
        
        stage.show();
        
//        
//        for (int i = 0; i < 10; i++) {
//            System.out.println("newY: " + newY);
//            
//            stage.show();
//            Thread.sleep(1000);
//        }
    }
}
