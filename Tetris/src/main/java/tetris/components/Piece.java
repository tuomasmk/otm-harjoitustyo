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
    
    abstract public void moveTo(int x, int y, int size);
    
    public boolean touches(int x, int y, int size) {
        for (Point point : parts) {
            if (point.getY() + size >= y) {
                return true;
            } else if (point.getX() + size >= x) {
                return true;
            }
        }
        return false;
    }
    
    public void move(Direction direction, int shift) {
        int shiftX = 0;
        switch (direction) {
            case LEFT: shiftX = -1;
                break;
            case RIGHT: shiftX = 1;
                break;
            case DOWN: this.drop(shift);
                return;
        }
        doMove(shiftX, 0, shift);
        if (tetris.touchesWall(this)) {
            doMove((-1 * shiftX), 0, shift);
        }
    }
    
    public void doMove(Direction direction, int shift) {
        int shiftX = 0;
        int shiftY = 0;
        switch (direction) {
            case LEFT: 
                shiftX = -1;
                break;
            case RIGHT: shiftX = 1;
                break;
            case DOWN: shiftY = 1;
                break;
        }
        for (Point point : parts) {
            point.setX(point.getX() + shiftX * shift);
            point.setY(point.getY() + shiftY * shift);
        }    
    }
    
    public void doMove(int shiftX, int shiftY, int shift) {
        location.setX(location.getX() + shiftX * shift);
        location.setY(location.getY() + shiftY * shift);
    }
    
    /**
     * Method moves piece downwards with the amount stated in shift. 
     * 
     * @param shift
     * @return  true if piece touches floor or another piece.
     */
    public boolean drop(int shift) {
        doMove(0, 1, shift);
        if (tetris.touchesFloor(this)) {
            doMove(0, -1, shift);
            return true;
        }
        return false;
    }
    
    public void dropAllTheWay() {
        while (!drop(1));
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Color getBorderColor() {
        return borderColor;
    }    

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    public Point[] getParts() {
        return parts;
    }

    public int getRotation() {
        return rotation;
    }

    public void setParts(Point[] parts) {
        this.parts = parts;
    }
    
    
    
}
