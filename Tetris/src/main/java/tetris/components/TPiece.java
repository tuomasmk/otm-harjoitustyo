package tetris.components;

import javafx.scene.paint.Color;
import tetris.logics.GameLogic;


public class TPiece extends Piece {
    
    public TPiece(GameLogic tetris, int x, int y) {
        this.tetris = tetris;
        this.location = new Point(x, y);
        this.color = Color.DARKGREY;
        this.borderColor = Color.DIMGREY;
        this.parts = new Point[4];
        parts[0] = new Point(0, 1);
        parts[1] = new Point(1, 1);
        parts[2] = new Point(2, 1);
        parts[3] = new Point(1, 0);
        rotation = 0;
    }
    
    @Override
    public void rotate() {
        switch (rotation++ % 4) {
            case 0:
                parts[0] = new Point(0, 0);
                parts[1] = new Point(0, 1);
                parts[2] = new Point(0, 2);
                parts[3] = new Point(1, 1);
                break;
            case 1:
                parts[0] = new Point(0, 0);
                parts[1] = new Point(1, 0);
                parts[2] = new Point(2, 0);
                parts[3] = new Point(1, 1);
                break;
            case 2:
                parts[0] = new Point(1, 0);
                parts[1] = new Point(1, 1);
                parts[2] = new Point(1, 2);
                parts[3] = new Point(0, 1);
                break;
            case 3:
                parts[0] = new Point(1, 0);
                parts[1] = new Point(0, 1);
                parts[2] = new Point(1, 1);
                parts[3] = new Point(2, 1);
                break;
        }
        while (tetris.touchesWall(this)) {
            doMove(Direction.LEFT, 1);
        }
        while (tetris.touchesFloor(this)) {
            doMove(Direction.UP, 1);
        }
    }

}
