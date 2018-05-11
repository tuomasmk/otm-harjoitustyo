package tetris.components;

import javafx.scene.paint.Color;
import tetris.logics.GameLogic;


public class IPiece extends Piece {

    public IPiece(GameLogic tetris, int x, int y) {
        this.tetris = tetris;
        this.location = new Point(x, y);
        this.color = Color.web("e87b70"); //Color.CRIMSON;
        this.borderColor = Color.web("af2b1d"); //9a2619 //Color.BROWN;
        this.parts = new Point[4];
        for (int i = 0; i < 4; i++) {
            parts[i] = new Point(0, i);
        }
        rotation = 0;
        length = 4;
    }
    
    @Override
    public void rotate() {
        for (int i = 1; i < 4; i++) {
            parts[i] = new Point(parts[i].getY(), parts[i].getX());
        }
        super.rotate();
    }
    
    protected void undoRotate() {
        for (int i = 1; i < 4; i++) {
            parts[i] = new Point(parts[i].getY(), parts[i].getX());
        }
    }

}
