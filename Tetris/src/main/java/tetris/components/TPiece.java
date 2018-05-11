package tetris.components;

import javafx.scene.paint.Color;
import tetris.logics.GameLogic;


public class TPiece extends Piece {
    
    public TPiece(GameLogic tetris, int x, int y) {
        this.tetris = tetris;
        this.location = new Point(x, y);
        this.color = Color.web("bebebe"); //Color.DARKGREY;
        this.borderColor = Color.web("8c8c8c"); //Color.DIMGREY;
        this.parts = new Point[4];
        parts[0] = new Point(0, 1);
        parts[1] = new Point(1, 1);
        parts[2] = new Point(2, 1);
        parts[3] = new Point(1, 0);
        rotation = 0;
        length = 3;
    }
    
    public void setRotation(int rotation) {
        switch (rotation) {
            case 0:
                parts[0] = new Point(1, 0);
                parts[1] = new Point(1, 1);
                parts[2] = new Point(1, 2);
                parts[3] = new Point(0, 1);
                break;
            case 1:
                parts[0] = new Point(0, 0);
                parts[1] = new Point(1, 0);
                parts[2] = new Point(2, 0);
                parts[3] = new Point(1, 1);
                break;
            case 2:
                parts[0] = new Point(0, 0);
                parts[1] = new Point(0, 1);
                parts[2] = new Point(0, 2);
                parts[3] = new Point(1, 1);
                break;
            case 3:
                parts[0] = new Point(1, 0);
                parts[1] = new Point(0, 1);
                parts[2] = new Point(1, 1);
                parts[3] = new Point(2, 1);
                break;
        }
        super.rotate();
    }
    
    @Override
    public void rotate() {
        setRotation(rotation++ % 4);
        super.rotate();
    }
   
    @Override
    protected void undoRotate() {
        rotation += 2;
        setRotation(rotation++ % 4);
    }

}
