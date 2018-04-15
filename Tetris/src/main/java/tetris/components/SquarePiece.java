package tetris.components;

import tetris.logics.GameLogic;
import javafx.scene.paint.Color;


public class SquarePiece extends Piece {

    public SquarePiece(GameLogic tetris, int x, int y) {
        this.tetris = tetris;
        this.location = new Point(x, y);
        this.color = Color.CYAN;
        this.borderColor = Color.DARKCYAN;
        this.parts = new Point[4];
        parts[0] = new Point(0, 0);
        parts[1] = new Point(0, 1);
        parts[2] = new Point(1, 0);
        parts[3] = new Point(1, 1);
    }

    @Override
    public void moveTo(int x, int y, int size) {
        parts[0].setX(x);
        parts[0].setY(y);
        parts[1].setX(x);
        parts[1].setY(y + size);
        parts[2].setX(x + size);
        parts[2].setY(y);
        parts[3].setX(x + size);
        parts[3].setY(y + size);
                
    }
    
    
    
}
