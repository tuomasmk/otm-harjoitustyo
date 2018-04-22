package tetris.components;

import javafx.scene.paint.Color;
import tetris.logics.GameLogic;


public class ReverseSPiece extends Piece {
    
    public ReverseSPiece(GameLogic tetris, int x, int y) {
        this.tetris = tetris;
        this.location = new Point(x, y);
        this.color = Color.ORANGE;
        this.borderColor = Color.CHOCOLATE;
        this.parts = new Point[4];
        parts[0] = new Point(0, 0);
        parts[1] = new Point(1, 0);
        parts[2] = new Point(1, 1);
        parts[3] = new Point(2, 1);
        rotation = 0;
    }
    
    public void rotate() {
        if (rotation++ % 2 == 0) {
            parts[0] = new Point(1, 0);
            parts[1] = new Point(1, 1);
            parts[2] = new Point(0, 1);
            parts[3] = new Point(0, 2);
        } else {
            parts[0] = new Point(0, 0);
            parts[1] = new Point(1, 0);
            parts[2] = new Point(1, 1);
            parts[3] = new Point(2, 1);
        }
        while (tetris.touchesWall(this)) {
            doMove(Direction.LEFT, 1);
        }
        while (tetris.touchesFloor(this)) {
            doMove(Direction.UP, 1);
        }
    }
}
