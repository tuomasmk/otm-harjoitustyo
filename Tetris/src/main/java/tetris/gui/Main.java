package tetris.gui;

import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tetris.components.ColoredPoint;
import tetris.components.Direction;
import tetris.components.Piece;
import tetris.components.Point;
import tetris.logics.GameLogic;
import tetris.components.SquarePiece;


public class Main extends Application{
    
    public static void main(String[] args) {
        launch(args);
    }
        
    @Override
    public void start(Stage stage) throws InterruptedException {
        GameLogic tetris = new GameLogic();
        final int size = 30;
        final int gameHeight = tetris.getGameHeight() * size;
        final int gameWidth = tetris.getGameWidth() * size;
        
        
        stage.setTitle("Tetris");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
//        HBox hbox = new HBox();
//        hbox.setPadding(new Insets(15, 15, 15, 15));
//        hbox.setSpacing(10);
//        
//        root.getChildren().add(hbox);
        
        Canvas canvas = new Canvas(gameWidth, gameHeight);
        root.getChildren().add(canvas);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        boolean spacePressed = false;
        
        //draw layout
        new AnimationTimer() {
            Piece piece; 
            ColoredPoint cPoint;
            ColoredPoint[][] pieces;
            long lastTime = System.nanoTime();
            public void handle(long currentNanoTime) {
                if(tetris.isGameOver()) {
                    this.stop();
                }
                if(currentNanoTime - lastTime < 1000000000 / 60) {
                    return;
                }
                lastTime = currentNanoTime;
                gc.clearRect(0, 0, gameWidth, gameHeight);
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(0, 0, gameWidth, gameHeight);
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
                for (int row = 0; row < pieces.length; row++) {
                    for (int col = 0; col < pieces[0].length; col++) {
                        if (pieces[row][col] != null) {
                            cPoint = pieces[row][col];
                            gc.setFill(cPoint.getBorderColor());
                            gc.fillRect(col * size,
                                        row * size,
                                        size,
                                        size);
                            gc.setFill(cPoint.getColor());
                            gc.fillRect(col * size + 1,
                                        row * size + 1,
                                        size - 2,
                                        size - 2);
                        }
                    }
                    
                }
            }
        }.start();
        
        //move piece
        new AnimationTimer() {
            long lastTime = System.nanoTime();
            public void handle(long currentNanoTime) {
                if(tetris.isGameOver()) {
                    stop();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText("Game Over!");
                    Platform.runLater(alert::showAndWait);

                }
                if(currentNanoTime - lastTime < 1000000000 / (1 + tetris.getGameSpeed())) {
                    return;
                }
                lastTime = currentNanoTime;
                tetris.advance();
            }
        }.start();
        
        scene.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.UP)) {
                tetris.getPiece().rotate();
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                tetris.getPiece().drop();
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
                tetris.getPiece().move(Direction.RIGHT);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
                tetris.getPiece().move(Direction.LEFT);
            } else if (event.getCode().equals(KeyCode.SPACE)) {
                tetris.getPiece().dropAllTheWay();
            }
        });
        
        stage.show();
    }
}
