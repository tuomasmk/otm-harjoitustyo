package tetris.logics;

import java.util.PriorityQueue;
import java.util.Random;
import tetris.components.ColoredPoint;
import tetris.components.IPiece;
import tetris.components.JPiece;
import tetris.components.LPiece;
import tetris.components.Piece;
import tetris.components.ReverseSPiece;
import tetris.components.SPiece;
import tetris.components.SquarePiece;
import tetris.components.TPiece;


public class GameLogic {
    private int gameHeight;
    private int gameWidth;
    private ColoredPoint[][] pieces;
    private Piece piece;
    private int rowsRemoved;
    private int gameSpeed;
    private boolean gameOver;

    public GameLogic() {
        gameHeight = 20;
        gameWidth = 10;
        pieces = new ColoredPoint[gameHeight][gameWidth];
        for (int i = 0; i < gameHeight; i++) {
            for (int j = 0; j < gameWidth; j++) {
                pieces[i][j] = null;
            }
        }
        piece = createNewPiece(this, this.gameWidth / 2, 0);
        rowsRemoved = 0;
        gameSpeed = 0;
        gameOver = false;
    }
    
    private void dropPieces(int from) {
        for (int row = from; row > 0; row--) {
            for (int col = 0; col < pieces[0].length; col++) {
                pieces[row][col] = pieces[row - 1][col];
            }
        }
    }
    
    private void removeRow(int row) {
        rowsRemoved++;
        if (rowsRemoved % 10 == 0) {
            gameSpeed++;
        }
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
    
    private Piece createNewPiece(GameLogic tetris, int x, int y) {
        Random random = new Random();
        switch (random.nextInt(7)) {
            case 0: 
                return new SquarePiece(tetris, x, y);
            case 1:
                return new IPiece(tetris, x, y);
            case 2:
                return new SPiece(tetris, x, y);
            case 3:
                return new ReverseSPiece(tetris, x, y);
            case 4:
                return new TPiece(tetris, x, y);
            case 5:
                return new LPiece(tetris, x, y);
            case 6:
                return new JPiece(tetris, x, y);
        }
        return new SquarePiece(tetris, x, y);
    }
    
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
            while (!deletedRows.isEmpty()) {
                dropPieces(deletedRows.poll());
            }
            piece = createNewPiece(this, this.gameWidth / 2, 0);
            gameOver = touchesFloor(piece);
        }
    }
    
    public boolean touchesFloor(Piece piece) {
        for (int i = 0; i < 4; i++) {
            int y = piece.getParts()[i].getY() + piece.getLocation().getY();
            int x = piece.getParts()[i].getX() + piece.getLocation().getX();
            if (y >= gameHeight) {
                return true;
            } else if (pieces[y][x] != null) {
                return true;
            }
        }
        return false;
    }
    
    public boolean touchesWall(Piece piece) {
        for (int i = 0; i < 4; i++) {
            int y = piece.getParts()[i].getY() + piece.getLocation().getY();
            int x = piece.getParts()[i].getX() + piece.getLocation().getX();
            if (x < 0 || x >= gameWidth) {
                return true;
            } else if (pieces[y][x] != null) {
                return true;
            }
        }
        return false;
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
        return this.gameSpeed;
    }
    
    
}
