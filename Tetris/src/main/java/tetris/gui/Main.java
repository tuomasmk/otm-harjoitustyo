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
        
        GameLogic tetris = new GameLogic(size);
        
        Canvas canvas = new Canvas(gameWidth, gameHeight);
        root.getChildren().add(canvas);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Piece piece1 = new SquarePiece(tetris, gameWidth/2, 0);
//        Rectangle rect = new Rectangle(50, 50, 20, 20);
        
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
                gc.setLineWidth(5);
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
//                System.out.println("GetPieces: ");
//                tetris.tulostaTaulukko(pieces);
                for (int row = 0; row < pieces.length; row++) {
                    for (int col = 0; col < pieces[0].length; col++) {
                        if (pieces[row][col] != null) {
                            cPoint = pieces[row][col];
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
                if(currentNanoTime - lastTime < 1000000000 / 15) {
                    return;
                }
                lastTime = currentNanoTime;
                tetris.advance(1);
            }
        }.start();
        
        scene.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.UP)) {
                ;
            } else if (event.getCode().equals(KeyCode.DOWN)) {
//                System.out.println("DOWN pressed");
                tetris.getPiece().drop(1);
            } else if (event.getCode().equals(KeyCode.RIGHT)) {
//                System.out.println("RIGHT pressed");
                tetris.getPiece().move(Direction.RIGHT, 1);
            } else if (event.getCode().equals(KeyCode.LEFT)) {
//                System.out.println("LEFT pressed");
                tetris.getPiece().move(Direction.LEFT, 1);
            } else if (event.getCode().equals(KeyCode.SPACE)) {
                tetris.getPiece().dropAllTheWay();
            }
        });
        
        stage.show();
    }
}
