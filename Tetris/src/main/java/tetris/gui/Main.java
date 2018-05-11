package tetris.gui;

import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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


public class Main extends Application{
    private GameLogic tetris;
    private AnimationTimer[] ats;
    private boolean spacePressed;
    private boolean pause;
    private MediaPlayer mPlayer;
    
    public static void main(String[] args) {
        launch(args);
    }
        
    @Override
    public void start(Stage stage) throws InterruptedException {
        tetris = new GameLogic(askPlayerName());
        try {
            tetris.getDatabase().connect();
        } catch (Exception e) {
            
        }
        final int size = 30;
        final int gameHeight = tetris.getGameHeight() * size;
        final int gameWidth = tetris.getGameWidth() * size;
        final int npHeight = 10 * size;
        final int npWidth = 8 * size;
        
        //set scene
        stage.setTitle("Tetris");
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        //create panes
        BorderPane bp = new BorderPane();
        Pane pane = new Pane();
        Canvas canvas = new Canvas(gameWidth, gameHeight);
        VBox vb = new VBox();
        Canvas nextPiece = new Canvas(6 * size, gameHeight);
        
        bp.setTop(addMenu());
        pane.getChildren().add(canvas);
        bp.setCenter(pane);
        vb.getChildren().add(nextPiece);
        bp.setRight(vb);
        
        //add music
        bp.setBottom(loadSoundtrack());
        
        root.getChildren().addAll(bp);
        
        //game area
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        //side area
        GraphicsContext gc2 = nextPiece.getGraphicsContext2D();
        
        ats = new AnimationTimer[2];
        spacePressed = false;
        pause = false;
        
        //draw
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
                if (mPlayer != null) {
                    mPlayer.setRate(1 + tetris.getGameSpeed() / 10.0);
                }
                tetris.advance();
            }
        };
        ats[1] = at2;
        at2.start();
        
        //user input
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
            } else if (event.getCode().equals(KeyCode.M)) {
                toggleMute(mPlayer);
            }
        });
        
        stage.show();
        if (mPlayer != null) {
            mPlayer.setCycleCount(-1);
            mPlayer.play();
        }
    }
    
    private MediaView loadSoundtrack() {
        try {
            Media sound = new Media(getClass().getResource("/tetris.mp3").toURI().toString());
            mPlayer = new MediaPlayer(sound);
            return new MediaView(mPlayer);
        } catch (Exception e) {
            mPlayer = null;
        }
        return null;
    }
    
    private String askPlayerName() {
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
        return playerName;
    }
    
    private void toggleMute(MediaPlayer player) {
        if (player != null) {
            player.setMute(!player.isMute());
        }
    }
    
    private void togglePause(GameLogic tetris, GraphicsContext gc, int size) {
        pause = !pause;
        if (pause) {
            gc.setFill(Color.CYAN);
            gc.setFont(Font.font("Comic Sans", FontWeight.BOLD, 50));
            gc.fillText("PAUSE", tetris.getGameWidth() * size / 5, tetris.getGameHeight() * size / 2); 
        }
    }
    
    //add menu
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
    
    /**
     * Game Over. Restart the game if the user wants to.
     * @param tetris
     * @param ats 
     */
    private void gameOver(GameLogic tetris, AnimationTimer[] ats) {
        tetris.saveScore();
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
    
    //draws the side panel
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
            drawSquare(gc, size, x1, y1, piece.getBorderColor(), piece.getColor());
        }
    }
    
    private void drawPiece(GraphicsContext gc, Piece piece, int size) {
        int x = piece.getLocation().getX();
        int y = piece.getLocation().getY();
        drawPiece(gc, piece, size, x, y);
    }
    
    private void drawPieces(GraphicsContext gc, ColoredPoint[][] pieces, int size) {
        ColoredPoint cp;
        for (int row = 0; row < pieces.length; row++) {
            for (int col = 0; col < pieces[0].length; col++) {
                if (pieces[row][col] != null) {
                    cp = pieces[row][col];
                    drawSquare(gc, size, col, row, cp.getBorderColor(), cp.getColor());
                }
            }

        }
    }
    
    /**
     * Draws one square. ShiftX and shiftY are used to prevent drawing
     * double margins between squares.
     * @param gc
     * @param size
     * @param x 
     * @param y
     * @param bColor
     * @param color 
     */
    private void drawSquare(GraphicsContext gc, int size, int x, int y, Color bColor, Color color) {
        int shiftX = 1;
        int shiftY = 1;
        if (x == 0) {
            shiftX = 0;
        }
        if (y == 0) {
            shiftY = 0;
        }
        gc.setFill(bColor);
        gc.fillRect(Math.max(0, x * size - 1),
                    Math.max(0, y * size - 1),
                    size + shiftX,
                    size + shiftY);
        gc.setFill(color);
        gc.fillRect(x * size + Math.abs(shiftX - 1),
                    y * size + Math.abs(shiftY - 1),
                    size + shiftX - 2 ,
                    size + shiftY - 2);
    }
}
