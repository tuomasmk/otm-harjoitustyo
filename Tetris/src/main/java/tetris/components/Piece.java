package tetris.components;

import tetris.logics.GameLogic;
import javafx.scene.paint.Color;

abstract public class Piece {
    protected GameLogic tetris;
    protected Point location;
    protected Color color;
    protected Color borderColor;
    protected int rotation;
    protected Point[] parts;
    
    abstract protected void undoRotate();
    
    public void rotate() {
        int x = this.location.getX();
        int y = this.location.getY();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (tetris.touchesWall(this)) {
                    //doMove(Direction.LEFT, 1);
                    doMove(-1, 0, 1);
                }
            }
            if (!tetris.touchesWall(this) && !tetris.touchesFloor(this)) {
                break;
            }
            this.location.setX(x);
            //doMove(Direction.UP, 1);
            doMove(0, -1, 1);
        }
        if (tetris.touchesWall(this) || tetris.touchesFloor(this)) {
            this.location.setY(y);
            undoRotate();
        }
    };
    
    /**
     * Moves the piece to the specified direction
     * if it does not collide with anything.
     * 
     * @param direction 
     */
    public void move(Direction direction) {
        int shiftX = 0;
        switch (direction) {
            case LEFT: shiftX = -1;
                break;
            case RIGHT: shiftX = 1;
                break;
            case DOWN: this.drop();
                return;
        }
        doMove(shiftX, 0, 1);
        if (tetris.touchesWall(this)) {
            doMove((-1 * shiftX), 0, 1);
        }
    }
    
//    private void doMove(Direction direction, int shift) {
//        int shiftX = 0;
//        int shiftY = 0;
//        switch (direction) {
//            case LEFT: 
//                shiftX = -1;
//                break;
//            case RIGHT: 
//                shiftX = 1;
//                break;
//            case DOWN: 
//                shiftY = 1;
//                break;
//            case UP: 
//                shiftY = -1;
//                break;
//        }
//        location.setX(location.getX() + shiftX * shift);
//        location.setY(location.getY() + shiftY * shift);    
//    }
    
    private void doMove(int shiftX, int shiftY, int shift) {
        location.setX(location.getX() + shiftX * shift);
        location.setY(location.getY() + shiftY * shift);
    }
    
    /**
     * Method moves piece downwards. 
     * 
     * @return  true if piece touches floor or another piece.
     */
    public boolean drop() {
        doMove(0, 1, 1);
        if (tetris.touchesFloor(this)) {
            doMove(0, -1, 1);
            return true;
        }
        return false;
    }
    
    /**
     * Drops the piece until it collides with the floor or another piece.
     */
    public void dropAllTheWay() {
        while (!drop());
    }

    public Color getColor() {
        return color;
    }

    public Color getBorderColor() {
        return borderColor;
    }    

    public Point getLocation() {
        return location;
    }

    public Point[] getParts() {
        return parts;
    }
}
