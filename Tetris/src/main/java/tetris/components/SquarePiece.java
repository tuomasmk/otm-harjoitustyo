package tetris.components;

import tetris.logics.GameLogic;
import javafx.scene.paint.Color;


public class SquarePiece extends Piece {

    public SquarePiece(GameLogic tetris, int x, int y) {
        this.tetris = tetris;
        this.location = new Point(x, y);
        this.color = Color.web("5884b3"); //Color.DODGERBLUE;
        this.borderColor = Color.web("38587a"); //Color.BLUE;
        this.parts = new Point[4];
        parts[0] = new Point(0, 0);
        parts[1] = new Point(0, 1);
        parts[2] = new Point(1, 0);
        parts[3] = new Point(1, 1);
    }

    @Override
    public void rotate() {
    }

    @Override
    protected void undoRotate() {
        
    }
}
