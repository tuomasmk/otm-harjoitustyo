package tetris;

import javafx.scene.shape.Rectangle;


public class Sovelluslogiikka {
    private int gameHeight;
    private int gameWidth;
    private int size;
    private ColoredPoint[][] pieces;
    private Piece piece;
    private boolean gameOver;

    public Sovelluslogiikka(int size) {
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
        gameOver = false;
    }
    
    public void advance(int shift) {
        if (this.piece.drop(shift)) {
            for (int i = 0; i < piece.getParts().length; i++) {
                int y = piece.getParts()[i].getY()+piece.getLocation().getY();
                int x = piece.getParts()[i].getX()+piece.getLocation().getX();
                pieces[y][x] = new ColoredPoint(x, y, piece.getColor());
            }
            piece = null;
        }
        if (piece == null) {
            piece = new SquarePiece(this, this.gameWidth / 2, 0);
        }
    }
    
    public boolean touches(Piece piece) {
        for (int i = 0; i < 4; i++) {
            int y = piece.getParts()[i].getY()+piece.getLocation().getY();
            int x = piece.getParts()[i].getX()+piece.getLocation().getX();
            if (pieces[y][x] != null) {
                return true;
            }
        }
        return false;
    }
    
    public boolean touchesFloor(Piece piece) {
        for (int i = 0; i < 4; i++) {
            int y = piece.getParts()[i].getY()+piece.getLocation().getY();
            int x = piece.getParts()[i].getX()+piece.getLocation().getX();
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
            int y = piece.getParts()[i].getY()+piece.getLocation().getY();
            int x = piece.getParts()[i].getX()+piece.getLocation().getX();
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
        return gameOver;
    }
    
    
}
