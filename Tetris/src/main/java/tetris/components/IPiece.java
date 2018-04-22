package tetris.components;

import javafx.scene.paint.Color;
import tetris.logics.GameLogic;


public class IPiece extends Piece {

    public IPiece(GameLogic tetris, int x, int y) {
        this.tetris = tetris;
        this.location = new Point(x, y);
        this.color = Color.CRIMSON;
        this.borderColor = Color.BROWN;
        this.parts = new Point[4];
        for (int i = 0; i < 4; i++) {
            parts[i] = new Point(0, i);
        }
        rotation = 0;
    }
    
    @Override
    public void rotate() {
        for (int i = 1; i < 4; i++) {
            parts[i] = new Point(parts[i].getY(), parts[i].getX());
        }
        while (tetris.touchesWall(this)) {
            doMove(Direction.LEFT, 1);
        }
        while (tetris.touchesFloor(this)) {
            doMove(Direction.UP, 1);
        }
    }

}
