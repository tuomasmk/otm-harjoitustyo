package tetris.gui;

import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tetris.components.ColoredPoint;
import tetris.components.Direction;
import tetris.components.Piece;
import tetris.components.Point;
import tetris.logics.GameLogic;
import tetris.components.SquarePiece;


public class Main extends Application{
    private boolean spacePressed;
    
    public static void main(String[] args) {
        launch(args);
    }
        
    @Override
    public void start(Stage stage) throws InterruptedException {
        TextInputDialog dialog = new TextInputDialog("PlayerName");
        dialog.setTitle("Player Name");
        dialog.setHeaderText("Name");
        dialog.setContentText("Enter name:");
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
        Optional<String> result = dialog.showAndWait();
        String playerName = "";
        if (result.isPresent()) {
            playerName = result.get();
        }
        if (playerName.isEmpty()) {
            playerName = "Pingu";
        }
        GameLogic tetris = new GameLogic(playerName );
        try {
//            tetris.getDatabase().connect();
        } catch (Exception e) {
        
        }
        final int size = 30;
        final int gameHeight = tetris.getGameHeight() * size;
        final int gameWidth = tetris.getGameWidth() * size;
        final int npHeight = 10 * size;
        final int npWidth = 8 * size;
        
        
        stage.setTitle("Tetris");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        BorderPane bp = new BorderPane();
        Pane pane = new Pane();
        pane.setStyle("-fx-border-style: solid inside;" +
                        "-fx-border-width: 2" +
                        "-fx-border-color: black;");
        Canvas canvas = new Canvas(gameWidth, gameHeight);
        VBox vb = new VBox();
        vb.setStyle("-fx-border-style: solid;" +
                    "-fx-border-width: 2" +
                    "-fx-border-color; black");
        Canvas nextPiece = new Canvas(6 * size, gameHeight);
        
        pane.getChildren().add(canvas);
        bp.setCenter(pane);
        vb.getChildren().add(nextPiece);
        bp.setRight(vb);
        
        root.getChildren().add(bp);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc2 = nextPiece.getGraphicsContext2D();
        
        spacePressed = false;
        
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
                drawPiece(gc, piece, size);
                pieces = tetris.getPieces();
                drawPieces(gc, pieces, size);
                int score = tetris.getScore();
                int highscore = tetris.getHighscore();
                int personalBest = tetris.getPersonalBest();
                gc2.clearRect(0, 0, npWidth, gameHeight);
                gc2.setFill(Color.LIGHTGRAY);
                gc2.fillRect(0, 0, npWidth, gameHeight);
                gc2.setFill(Color.RED);
                gc2.setFont(Font.font("Comic Sans", FontWeight.BOLD, size/2));
                gc2.fillText("Score", size, size);
                gc2.fillText("" + score, size, 2 * size, npWidth);
                gc2.setLineWidth(1);
                gc2.setStroke(Color.DARKGRAY);
                gc2.strokeLine(0, 2.3 * size, npWidth, 2.3 * size);
                gc2.setFill(Color.WHITE);
                gc2.fillText("Highscore", size, 3 * size);
                gc2.fillText("" + (highscore > score ? highscore : score), size, 4 * size);
                gc2.strokeLine(0, 4.3 * size, npWidth, 4.3 * size);
                gc2.setFill(Color.BLACK);
                gc2.fillText("Presonal Best", size, 5 * size);
                gc2.fillText("" + (personalBest > score ? personalBest : score), size, 6 * size);
                gc2.strokeLine(0, 6.3 * size, npWidth, 6.3 * size);
                gc2.setFill(Color.ROYALBLUE);
                gc2.fillText("Next piece: ", size, 7 * size);
                drawPiece(gc2, tetris.getNextPiece(), size, 1, 8);
            }
        }.start();
        
        //move piece
        new AnimationTimer() {
            long lastTime = System.nanoTime();
            public void handle(long currentNanoTime) {
                if(tetris.isGameOver()) {
                    stop();
                    tetris.theEnd();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText("Game Over!");
                    Platform.runLater(alert::showAndWait);
                }
                if(!spacePressed && currentNanoTime - lastTime < 1000000000 / (1 + tetris.getGameSpeed())) {
                    return;
                }
                if (spacePressed) {
                    toggleSpacePressed();
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
                spacePressed = true;
                tetris.getPiece().dropAllTheWay();
            }
        });
        
        stage.show();
    }
    
    private void toggleSpacePressed() {
        spacePressed = false;
    }
    
    private void drawPiece(GraphicsContext gc, Piece piece, int size, int x, int y) {
        for(Point point : piece.getParts()) {
            int x1 = x + point.getX();
            int y1 = y + point.getY();
            gc.setFill(piece.getBorderColor());
            gc.fillRect(x1 * size, y1 * size, size, size);
            gc.setFill(piece.getColor());
            gc.fillRect(x1 * size + 1, y1 * size + 1, size - 2, size - 2);
        }
    }
    
    private void drawPiece(GraphicsContext gc, Piece piece, int size) {
        for(Point point : piece.getParts()) {
            int x = piece.getLocation().getX() + point.getX();
            int y = piece.getLocation().getY() + point.getY();
            gc.setFill(piece.getBorderColor());
            gc.fillRect(x * size, y * size, size, size);
            gc.setFill(piece.getColor());
            gc.fillRect(x * size + 1, y * size + 1, size - 2, size - 2);
        }
    }
    
    private void drawPieces(GraphicsContext gc, ColoredPoint[][] pieces, int size) {
        ColoredPoint cp;
        for (int row = 0; row < pieces.length; row++) {
            for (int col = 0; col < pieces[0].length; col++) {
                if (pieces[row][col] != null) {
                    cp = pieces[row][col];
                    gc.setFill(cp.getBorderColor());
                    gc.fillRect(col * size,
                                row * size,
                                size,
                                size);
                    gc.setFill(cp.getColor());
                    gc.fillRect(col * size + 1,
                                row * size + 1,
                                size - 2,
                                size - 2);
                }
            }

        }
    }
}
