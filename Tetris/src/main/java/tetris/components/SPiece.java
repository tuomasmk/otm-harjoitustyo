package tetris.components;

import javafx.scene.paint.Color;
import tetris.logics.GameLogic;


public class SPiece extends Piece {

    public SPiece(GameLogic tetris, int x, int y) {
        this.tetris = tetris;
        this.location = new Point(x, y);
        this.color = Color.DARKSEAGREEN;
        this.borderColor = Color.GREEN;
        this.parts = new Point[4];
        parts[0] = new Point(0, 1);
        parts[1] = new Point(1, 1);
        parts[2] = new Point(1, 0);
        parts[3] = new Point(2, 0);
        rotation = 0;
    }
    
    
    public void setRotation(int rotation) {
        if (rotation == 0) {
            parts[0] = new Point(0, 0);
            parts[1] = new Point(0, 1);
            parts[2] = new Point(1, 1);
            parts[3] = new Point(1, 2);
        } else {
            parts[0] = new Point(0, 1);
            parts[1] = new Point(1, 1);
            parts[2] = new Point(1, 0);
            parts[3] = new Point(2, 0);
        }
        super.rotate();
    }
    
    @Override
    public void rotate() {
        setRotation(rotation++ % 2);
        super.rotate();
    }
    
    protected void undoRotate() {
        rotation++;
        setRotation(rotation % 2);
    }
}
