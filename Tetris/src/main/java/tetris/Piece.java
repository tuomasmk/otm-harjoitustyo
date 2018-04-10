package tetris;

abstract public class Piece {
    protected int rotation;
    protected Square[] parts;
    
    abstract public void moveTo(int x, int y);
    
    public boolean touches(int x, int y) {
        for (Square square : parts) {
            if (square.getY() + square.getSize() >= y) {
                return true;
            } else if (square.getX() + square.getSize() >= x) {
                return true;
            }
        }
        return false;
    }
    
    public void move(Direction direction, int shift) {
        int shiftX = 0;
        int shiftY = 0;
        switch (direction) {
            case LEFT: 
                shiftX = -1;
//                System.out.println("LEFT: " + parts[0].getX()
//                    + " newX: " + (parts[0].getX() + shiftX * shift));
                break;
            case RIGHT: shiftX = 1;
                break;
            case DOWN: shiftY = 1;
                break;
        }
        for (Square square : parts) {
            square.setX(square.getX() + shiftX * shift);
            square.setY(square.getY() + shiftY * shift);
        }    
    }
    
    public void move(Direction direction) {
        move(direction, parts[0].getSize());
    }

    public Square[] getParts() {
        return parts;
    }

    public int getRotation() {
        return rotation;
    }

    public void setParts(Square[] parts) {
        this.parts = parts;
    }
    
    
    
}
