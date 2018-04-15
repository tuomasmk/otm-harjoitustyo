package PieceTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import tetris.components.Direction;
import tetris.components.Piece;
import tetris.logics.GameLogic;
import tetris.components.SquarePiece;


public class PieceTest {
    
    Piece piece;
    GameLogic tetris;
    
    @Before
    public void setUp() {
        tetris = new GameLogic(1);
        piece = new SquarePiece(tetris, 5, 10);
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
        assertEquals(5, piece.getLocation().getX());
        assertEquals(10, piece.getLocation().getY());
    }
    
    @Test
    public void pieceCanBeMovedDown() {
        piece.move(Direction.DOWN, 1);
        assertEquals(5, piece.getLocation().getX());
        assertEquals(11, piece.getLocation().getY());
    }
    
    @Test
    public void pieceCanBeMovedLeft() {
        piece.move(Direction.LEFT, 1);
        assertEquals(4, piece.getLocation().getX());
        assertEquals(10, piece.getLocation().getY());
    }
    
    @Test
    public void pieceCanBeMovedRight() {
        piece.move(Direction.RIGHT, 1);
        assertEquals(6, piece.getLocation().getX());
        assertEquals(10, piece.getLocation().getY());
    }
            
}
