package tetris.logics;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import tetris.components.ColoredPoint;
import tetris.components.IPiece;
import tetris.components.JPiece;
import tetris.components.LPiece;
import tetris.components.Piece;
import tetris.components.Point;
import tetris.components.ReverseSPiece;
import tetris.components.SPiece;
import tetris.components.Score;
import tetris.components.SquarePiece;
import tetris.components.TPiece;
import tetris.dao.Dao;
import tetris.dao.Database;
import tetris.dao.ScoresDao;
import tetris.dao.ScoresFileDao;


public class GameLogic {
    private int gameHeight;
    private int gameWidth;
    private Random random;
    private ColoredPoint[][] pieces;
    private Piece piece;
    private Piece nextPiece;
    private int rowsRemoved;
    private boolean gameOver;
    private String playerName;
    private int score;
    private int highscore;
    private int personalBest;
    private Dao scores;
    private Database database;

    public GameLogic(String playerName) {
        this();
        this.playerName = playerName;
        getHighScore();
    }
    
    public GameLogic() {
        gameHeight = 20;
        gameWidth = 10;
        pieces = new ColoredPoint[gameHeight][gameWidth];
        database = new Database("database.db");
        scores = new ScoresDao(database, "highscores");
        initializeGame();
//        scores = new ScoresFileDao("topscores.txt");
    }
    
    private void initializeGame() {
        pieces = new ColoredPoint[gameHeight][gameWidth];
        for (int i = 0; i < gameHeight; i++) {
            for (int j = 0; j < gameWidth; j++) {
                pieces[i][j] = null;
            }
        }
        rowsRemoved = 0;
        gameOver = false;
        score = 0;
        random = new Random();
        piece = createNewPiece();
        nextPiece = createNewPiece();
    }
    
    /**
     * Empties the game area and starts a new game.
     */
    public void newGame() {
        initializeGame();
    }
    
    /**
     * Saves score to a database or a file
     */
    public void theEnd() {
        try {
            scores.saveOrUpdate(new Score(playerName, score));
        } catch (Exception e) {
            System.out.println("theEnd error:");
            System.out.println(e.getMessage());
        }
    }
    
    private void getHighScore() {
        try {
            List<Score> scores = this.scores.findAll();
            Collections.sort(scores);
            if (!scores.isEmpty()) {
                highscore = scores.get(0).getScore();
            } else {
                highscore = 3;
            }
            personalBest = 0;
            for (int i = 0; i < scores.size(); i++) {
                if (scores.get(i).getPlayer().equals(playerName)) {
                    personalBest = scores.get(i).getScore();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void dropPieces(int from) {
        for (int row = from; row > 0; row--) {
            for (int col = 0; col < pieces[0].length; col++) {
                pieces[row][col] = pieces[row - 1][col];
            }
        }
        for (int col = 0; col < pieces[0].length; col++) {
            pieces[0][col] = null;
        }
    }
    
    private void removeRow(int row) {
        rowsRemoved++;
        for (int col = 0; col < pieces[0].length; col++) {
            pieces[row][col] = null;
        }
    }
    
    private boolean isFullRow(int row) {
        for (int col = 0; col < pieces[0].length; col++) {
            if (pieces[row][col] == null) {
                return false;
            }
        }
        return true;
    }
    
    int numberOfPiece = 0;
    private Piece createNewPiece() {
        int x = this.gameWidth / 2;
        int y = 0;
//        if (numberOfPiece++ < 6) {
//            return new IPiece(this, x, y);
//        } else {
//            return new LPiece(this, x, y);
//        }
        switch (random.nextInt(7)) {
            case 0: 
                return new SquarePiece(this, x, y);
            case 1:
                return new IPiece(this, x, y);
            case 2:
                return new SPiece(this, x, y);
            case 3:
                return new ReverseSPiece(this, x, y);
            case 4:
                return new TPiece(this, x, y);
            case 5:
                return new LPiece(this, x, y);
            case 6:
                return new JPiece(this, x, y);
        }
        return new SquarePiece(this, x, y);
    }
    
    /**
     * Advances the game.
     * i.e. drops the piece, removes full rows and 
     * drops static pieces when a row is removed.
     */
    public void advance() {
        PriorityQueue<Integer> deletedRows = new PriorityQueue();
        if (this.piece.drop()) {
            for (int i = 0; i < piece.getParts().length; i++) {
                int y = piece.getParts()[i].getY() + piece.getLocation().getY();
                int x = piece.getParts()[i].getX() + piece.getLocation().getX();
                pieces[y][x] = new ColoredPoint(x, y, piece.getColor(), piece.getBorderColor());
                if (isFullRow(y)) {
                    removeRow(y);
                    deletedRows.add(y);
                }
            }
            increaseScore(deletedRows.size());
            while (!deletedRows.isEmpty()) {
                dropPieces(deletedRows.poll());
            }
            piece = nextPiece;
            nextPiece = createNewPiece();
            gameOver = touchesFloor(piece);
        }
    }
    
    private void increaseScore(int rows) {
        switch (rows) {
            case 1: score += 100;
                break;
            case 2: score += 300;
                break;
            case 3: score += 600;
                break;
            case 4: score += 1000;
                break;
        }
    }
    
    /**
     * Checks if the given piece touches floor or other pieces.
     * 
     * @param piece
     * @return true if the piece touches floor or other piece.
     */
    public boolean touchesFloor(Piece piece) {
        for (int i = 0; i < 4; i++) {
            int y = piece.getParts()[i].getY() + piece.getLocation().getY();
            int x = piece.getParts()[i].getX() + piece.getLocation().getX();
            if (y > -1 && x > -1 && x < gameWidth) {
                if (y >= gameHeight) {
                    return true;
                } else if (pieces[y][x] != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if the given piece touches side of the game area or another piece 
     * i.e. if the given piece can be moved sidewards.
     * 
     * @param piece
     * @return true if the piece touches wall or another piece.
     */
    public boolean touchesWall(Piece piece) {
        for (int i = 0; i < 4; i++) {
            int y = piece.getParts()[i].getY() + piece.getLocation().getY();
            int x = piece.getParts()[i].getX() + piece.getLocation().getX();
            if (y > -1 && y < gameHeight) {
                if (x < 0 || x >= gameWidth) {
                    return true;
                } else if (pieces[y][x] != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Returns ture if there is an obstacle next to the piece on the left.
     * @param parts of a piece
     * @return true if the piece would collide with something when moved left.
     */
    public boolean blockOnTheLeft(Piece piece) {
        if(piece.getLocation().getX() == 0) {
                return true;
        }
        for (Point part : piece.getParts()) {
            int x = part.getX() + piece.getLocation().getX(); 
            int y = part.getY() + piece.getLocation().getY();
            if (x > -1 && y > -1 && x < gameWidth && y < gameHeight) {
                if (pieces[y][x-1] != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isOccupied(int x, int y) {
        return pieces[x][y] != null;
    }

    public int getGameHeight() {
        return gameHeight;
    }

    public int getGameWidth() {
        return gameWidth;
    }

    public ColoredPoint[][] getPieces() {
        return pieces;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getGameSpeed() {
        return rowsRemoved / 15;
    }

    public int getScore() {
        return score;
    }

    public Piece getNextPiece() {
        return nextPiece;
    }
    
    public int getHighscore() {
        return highscore;
    }

    public int getPersonalBest() {
        return personalBest;
    }

    public Database getDatabase() {
        return database;
    }
    
    public int getLevel() {
        return getGameSpeed() + 1;
    }
}
