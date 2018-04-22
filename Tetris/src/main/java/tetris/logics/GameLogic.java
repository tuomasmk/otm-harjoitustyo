package tetris.logics;

import java.util.PriorityQueue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import tetris.components.ColoredPoint;
import tetris.components.Piece;
import tetris.components.SquarePiece;


public class GameLogic {
    private int gameHeight;
    private int gameWidth;
    private int size;
    private ColoredPoint[][] pieces;
    private Piece piece;
    private boolean gameOver;

    public GameLogic(int size) {
        gameHeight = 40;
        gameWidth = 20;
        this.size = size;
        pieces = new ColoredPoint[gameHeight][gameWidth];
        for (int i = 0; i < gameHeight; i++) {
            for (int j = 0; j < gameWidth; j++) {
                pieces[i][j] = null;
            }
        }
        piece = new SquarePiece(this, this.gameWidth / 2, 0);
    }
    
    public void tulostaTaulukko(Object[][] taulukko) {
        System.out.println("");
        for (int row = 0; row < taulukko.length; row++) {
            System.out.printf("%02d", row);
            for (int col = 0; col < taulukko[0].length; col++) {
                if (taulukko[row][col] == null) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println("");
        }
    }
    
    public void dropPieces(int from) {
        for (int row = from; row > 0; row--) {
            for (int col = 0; col < pieces[0].length; col++) {
                    pieces[row][col] = pieces[row-1][col];
            }
        }
    }
    
    public void removeRow(int row) {
        for (int col = 0; col < pieces[0].length; col++) {
            pieces[row][col] = null;
        }
    }
    
    public boolean isFullRow(int row) {
        for (int col = 0; col < pieces[0].length; col++) {
            if (pieces[row][col] == null) {
                return false;
            }
        }
        return true;
    }
    
    public void advance(int shift) {
        PriorityQueue<Integer> deletedRows = new PriorityQueue();
        if (this.piece.drop(shift)) {
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
                tulostaTaulukko(pieces);
                dropPieces(deletedRows.poll());
                tulostaTaulukko(pieces);
            }
            piece = null;
        }
        if (piece == null) {
            piece = new SquarePiece(this, this.gameWidth / 2, 0);
        }
    }
    
    public boolean touches(Piece piece) {
        for (int i = 0; i < 4; i++) {
            int y = piece.getParts()[i].getY() + piece.getLocation().getY();
            int x = piece.getParts()[i].getX() + piece.getLocation().getX();
            if (pieces[y][x] != null) {
                return true;
            }
        }
        return false;
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

    public void setGameHeight(int gameHeight) {
        this.gameHeight = gameHeight;
    }

    public void setGameWidth(int gameWidth) {
        this.gameWidth = gameWidth;
    }

    public void setPieces(ColoredPoint[][] pieces) {
        this.pieces = pieces;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isGameOver() {
        for (int i = 0; i < gameWidth; i++) {
            if (pieces[0][i] != null) {
                return true;
            }
        }
        return false;
    }
    
    
}
