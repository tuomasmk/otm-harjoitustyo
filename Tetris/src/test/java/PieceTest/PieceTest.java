package PieceTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import tetris.Direction;
import tetris.Piece;
import tetris.SquarePiece;


public class PieceTest {
    
    Piece piece;
    
    @Before
    public void setUp() {
        piece = new SquarePiece(20, 20, 20);
    }
    
    @Test
    public void pieceExists() {
        assertTrue(piece!=null);
    }
    
    @Test
    public void pieceContainsFourSquares() {
        assertEquals(4, piece.getParts().length);
    }
    
    @Test
    public void firstSquareIsInGivenCoordinates() {
        assertEquals(20, piece.getParts()[0].getX());
        assertEquals(20, piece.getParts()[0].getY());
    }
    
    @Test
    public void pieceCanBeMovedDown() {
        piece.move(Direction.DOWN);
        assertEquals(20, piece.getParts()[0].getX());
        assertEquals(40, piece.getParts()[0].getY());
    }
    
    @Test
    public void pieceCanBeMovedLeft() {
        piece.move(Direction.LEFT);
        assertEquals(0, piece.getParts()[0].getX());
        assertEquals(20, piece.getParts()[0].getY());
    }
    
    @Test
    public void pieceCanBeMovedRight() {
        piece.move(Direction.RIGHT);
        assertEquals(40, piece.getParts()[0].getX());
        assertEquals(20, piece.getParts()[0].getY());
    }
            
}
