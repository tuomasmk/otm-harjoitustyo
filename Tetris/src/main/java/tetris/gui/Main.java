package tetris.gui;

import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
    private GameLogic tetris;
    private AnimationTimer[] ats;
    private boolean spacePressed;
    private boolean pause;
    
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
        tetris = new GameLogic(playerName );
        try {
            tetris.getDatabase().connect();
        } catch (Exception e) {
            e.printStackTrace();
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
//        pane.setStyle("-fx-border-style: solid inside;" +
//                        "-fx-border-width: 2;" +
//                        "-fx-border-color: black;");
        Canvas canvas = new Canvas(gameWidth, gameHeight);
        VBox vb = new VBox();
//        vb.setStyle("-fx-border-style: solid;" +
//                    "-fx-border-width: 2;" +
//                    "-fx-border-color: black;");
        Canvas nextPiece = new Canvas(6 * size, gameHeight);
        
        bp.setTop(addMenu());
        pane.getChildren().add(canvas);
        bp.setCenter(pane);
        vb.getChildren().add(nextPiece);
        bp.setRight(vb);
        
        root.getChildren().add(bp);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gc2 = nextPiece.getGraphicsContext2D();
        
        ats = new AnimationTimer[2];
        spacePressed = false;
        pause = false;
        
        //draw layout
        AnimationTimer at1 = new AnimationTimer() {
            Piece piece; 
            ColoredPoint cPoint;
            ColoredPoint[][] pieces;
            long lastTime = System.nanoTime();
            @Override
            public void handle(long currentNanoTime) {
                if(tetris.isGameOver()) {
                    this.stop();
                }
                if( pause || currentNanoTime - lastTime < 1000000000 / 60) {
                    return;
                }
                lastTime = currentNanoTime;
                gc.clearRect(0, 0, gameWidth, gameHeight);
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, gameWidth, gameHeight);
                piece = tetris.getPiece();
                drawPiece(gc, piece, size);
                pieces = tetris.getPieces();
                drawPieces(gc, pieces, size);
                drawScores(gc2, tetris, gameHeight, npWidth, size);
            }
        };
        ats[0] = at1;
        at1.start();
        
        //move piece
        AnimationTimer at2 = new AnimationTimer() {
            long lastTime = System.nanoTime();
            @Override
            public void handle(long currentNanoTime) {
                if(tetris.isGameOver()) {
                    stop();
                    gameOver(tetris, new AnimationTimer[] {
                        at1,
                        this
                    });
                }
                if (pause || (!spacePressed && currentNanoTime - lastTime < 1000000000 / (1 + tetris.getGameSpeed()))) {
                    return;
                }
                if (spacePressed) {
                    toggleSpacePressed();
                }
                lastTime = currentNanoTime;
                tetris.advance();
            }
        };
        ats[1] = at2;
        at2.start();
        
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
            } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                togglePause(tetris, gc, size);
            } else if (event.getCode().equals(KeyCode.F2)) {
                newGame(tetris, ats);
            }
        });
        
        stage.show();
    }
    
    private void togglePause(GameLogic tetris, GraphicsContext gc, int size) {
        pause = !pause;
        if (pause) {
            gc.setFill(Color.CYAN);
            gc.setFont(Font.font("Comic Sans", FontWeight.BOLD, 50));
            gc.fillText("PAUSE", tetris.getGameWidth() * size / 5, tetris.getGameHeight() * size / 2); 
        }
    }
    
    private MenuBar addMenu() {
        MenuBar menubar = new MenuBar();
        Menu menu = new Menu("Tetris");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                newGame(tetris, ats);
            }
        });
        MenuItem close = new MenuItem("Close");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        menu.getItems().addAll(newGame, close);
        menubar.getMenus().addAll(menu);
        return menubar;
    }
    
    private void newGame(GameLogic tetris, AnimationTimer[] ats) {
        tetris.newGame();
        for (AnimationTimer at : ats) {
            at.start();
        }
    }
    
    private void gameOver(GameLogic tetris, AnimationTimer[] ats) {
        tetris.theEnd();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Game Over! New Game?");
        alert.getButtonTypes().clear(); 
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        Platform.runLater(new Runnable(){
           @Override
           public void run() {
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES){
                     newGame(tetris, ats);
                }
           }
        });
    }
    
    private void toggleSpacePressed() {
        spacePressed = false;
    }
    
    private void drawScores(GraphicsContext gc2, GameLogic tetris, int gameHeight, int npWidth, int size) {
        int level = tetris.getLevel();
        int score = tetris.getScore();
        int highscore = tetris.getHighscore();
        int personalBest = tetris.getPersonalBest();
        int i = 1;
        gc2.clearRect(0, 0, npWidth, gameHeight);
        gc2.setFill(Color.LIGHTGRAY);
        gc2.fillRect(0, 0, npWidth, gameHeight);
        gc2.setFont(Font.font("Comic Sans", FontWeight.BOLD, size/2));
        gc2.setFill(Color.GREEN);
        gc2.fillText("Level " + level, size, i++ * size);
        gc2.strokeLine(0, 1.3 * size, npWidth, 1.3 * size);
        gc2.setFill(Color.RED);
        gc2.fillText("Score", size, i++ * size);
        gc2.fillText("" + score, size, i++ * size, npWidth);
        gc2.setLineWidth(1);
        gc2.setStroke(Color.DARKGRAY);
        gc2.strokeLine(0, 3.3 * size, npWidth, 3.3 * size);
        gc2.setFill(Color.WHITE);
        gc2.fillText("Highscore", size, i++ * size);
        gc2.fillText("" + (highscore > score ? highscore : score), size, i++ * size);
        gc2.strokeLine(0, 5.3 * size, npWidth, 5.3 * size);
        gc2.setFill(Color.BLACK);
        gc2.fillText("Personal Best", size, i++ * size);
        gc2.fillText("" + (personalBest > score ? personalBest : score), size, i++ * size);
        gc2.strokeLine(0, 7.3 * size, npWidth, 7.3 * size);
        gc2.setFill(Color.ROYALBLUE);
        gc2.fillText("Next piece: ", size, i++ * size);
        drawPiece(gc2, tetris.getNextPiece(), size, 1, i);
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
