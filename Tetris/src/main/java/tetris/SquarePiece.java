package tetris;


public class SquarePiece extends Piece{

    public SquarePiece(int x, int y, int size) {
        this.parts = new Square[4];
        parts[0] = new Square(x,y,size);
        parts[1] = new Square(x,y+size,size);
        parts[2] = new Square(x+size,y,y+size);
        parts[3] = new Square(x+size,y+size,size);
    }

    @Override
    public void moveTo(int x, int y) {
        int size = parts[0].getSize();
        parts[0].setX(x);
        parts[0].setY(y);
        parts[1].setX(x);
        parts[1].setY(y+size);
        parts[2].setX(x+size);
        parts[2].setY(y);
        parts[3].setX(x+size);
        parts[3].setY(y+size);
                
    }
    
    
    
}
