package tetris.ui;

import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import tetris.ColoredPoint;
import tetris.Direction;
import tetris.Piece;
import tetris.Point;
import tetris.Sovelluslogiikka;
import tetris.Square;
import tetris.SquarePiece;


public class Main extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }
        
    @Override
    public void start(Stage stage) throws InterruptedException {
        final int gameHeight = 960;
        final int gameWidth = 480;
        final int nrOfPieces = 40;
        final int size = gameHeight/nrOfPieces;
        
        stage.setTitle("Tetris");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
//        HBox hbox = new HBox();
//        hbox.setPadding(new Insets(15, 15, 15, 15));
//        hbox.setSpacing(10);
//        
//        root.getChildren().add(hbox);
        
        Sovelluslogiikka tetris = new Sovelluslogiikka(size);
        
        Canvas canvas = new Canvas(gameWidth, gameHeight);
        root.getChildren().add(canvas);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Piece piece1 = new SquarePiece(tetris, gameWidth/2, 0);
//        Rectangle rect = new Rectangle(50, 50, 20, 20);
        
        
        new AnimationTimer() {
            Piece piece; 
            ColoredPoint cPoint;
            ColoredPoint[][] pieces;
//            double newY = rect.getY() + rect.getHeight();
            long lastTime = System.nanoTime();
            public void handle(long currentNanoTime) {
                if(tetris.isGameOver()) {
                    this.stop();
                }
                if(currentNanoTime - lastTime < 1000000000 / 5) {
                    return;
                }
                lastTime = currentNanoTime;
                gc.clearRect(0, 0, gameWidth, gameHeight);
                gc.setFill(Color.LIGHTGRAY);
                gc.setLineWidth(5);
                gc.fillRect(0, 0, gameWidth, gameHeight);
//                gc.fillRect(gameWidth/3, 0, gameWidth/3, gameWidth/3 * 2);
//                gc.setFill(Color.RED);
//                gc.fillRect(50, 50, 20, 20);
//                gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
//                rect.setY(newY);
//                newY = rect.getY() + rect.getHeight() / 10;
//                if(newY+rect.getHeight() > gameHeight) {
//                    newY = 0;
//                }
                piece = tetris.getPiece();
                for(Point point : piece.getParts()) {
                    int x = piece.getLocation().getX() + point.getX();
                    int y = piece.getLocation().getY() + point.getY();
                    gc.setFill(piece.getBorderColor());
                    gc.fillRect(x * size, y * size, size, size);
                    gc.setFill(piece.getColor());
                    gc.fillRect(x * size + 1, y * size + 1, size - 2, size - 2);
                }
                pieces = tetris.getPieces();
                for (int i = 0; i < pieces.length; i++) {
                    for (int j = 0; j < pieces[0].length; j++) {
                        if (pieces[i][j] != null) {
                            cPoint = pieces[i][j];
                            gc.setFill(cPoint.getBorderColor());
                            gc.fillRect(cPoint.getX() * size,
                                        cPoint.getY() * size,
                                        size,
                                        size);
                            gc.setFill(cPoint.getColor());
                            gc.fillRect(cPoint.getX() * size + 1,
                                        cPoint.getY() * size + 1,
                                        size - 2,
                                        size - 2);
                        }
                    }
                    
                }
                tetris.advance(1);
//                piece1.move(Direction.DOWN, 5);
//                if(piece1.touches(gameWidth, gameHeight, size)) {
//                    piece1.moveTo(gameWidth/2, 0, size);
//                }
            }
        }.start();
        
        scene.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.UP)) {
                ;
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                System.out.println("DOWN pressed");
                tetris.getPiece().drop(1);
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                System.out.println("RIGHT pressed");
                tetris.getPiece().move(Direction.RIGHT, 1);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                System.out.println("LEFT pressed");
                tetris.getPiece().move(Direction.LEFT, 1);
            }
        });
        
        stage.show();
        
//        Alert alert = new Alert(AlertType.CONFIRMATION);
//            alert.setTitle("Game Over");
//            alert.setHeaderText("Game Over!");
//            alert.setContentText("Want to play again?");
//            Optional<ButtonType> result = alert.showAndWait();
//            if (result.get() == ButtonType.OK) {
//                
//            } else {
//                
//            }
                
//        
//        for (int i = 0; i < 10; i++) {
//            System.out.println("newY: " + newY);
//            
//            stage.show();
//            Thread.sleep(1000);
//        }
    }
}
