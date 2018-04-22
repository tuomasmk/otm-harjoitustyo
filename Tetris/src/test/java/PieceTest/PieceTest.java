package PieceTest;

import javafx.scene.paint.Color;
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
        tetris = new GameLogic();
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
    public void firstSquareIsAtGivenCoordinates() {
        assertEquals(5, piece.getLocation().getX());
        assertEquals(10, piece.getLocation().getY());
    }
    
    @Test
    public void rightColorIsReturned() {
        assertEquals(Color.DEEPSKYBLUE, piece.getColor());
    }
    
    @Test
    public void rightBorderColorIsReturned() {
        assertEquals(Color.DARKBLUE, piece.getBorderColor());
    }
    
    @Test
    public void pieceCanBeMovedDown() {
        piece.move(Direction.DOWN);
        assertEquals(5, piece.getLocation().getX());
        assertEquals(11, piece.getLocation().getY());
    }
    
    @Test
    public void pieceCanBeMovedLeft() {
        piece.move(Direction.LEFT);
        assertEquals(4, piece.getLocation().getX());
        assertEquals(10, piece.getLocation().getY());
    }
    
    @Test
    public void pieceCanBeMovedRight() {
        piece.move(Direction.RIGHT);
        assertEquals(6, piece.getLocation().getX());
        assertEquals(10, piece.getLocation().getY());
    }
    
    @Test
    public void collidesWithWall() {
        for (int i = 0; i < 6; i++) {
            piece.move(Direction.LEFT);
        }
        assertEquals(0, piece.getLocation().getX());
        assertEquals(10, piece.getLocation().getY());
    }
    
    @Test
    public void dropsAllTheWay() {
        piece.dropAllTheWay();
        assertEquals(tetris.getGameHeight() - 2, piece.getLocation().getY());
    }
    
    @Test
    public void collidesWithFloor() {
        piece.dropAllTheWay();
        piece.drop();
        assertEquals(tetris.getGameHeight() - 2, piece.getLocation().getY());
    }
    
    @Test
    public void movingDownWithDoMove() {
        piece.doMove(Direction.DOWN, 1);
        assertEquals(11, piece.getLocation().getY());
    }
    
    @Test
    public void movingRightWithDoMove() {
        piece.doMove(Direction.RIGHT, 1);
        assertEquals(6, piece.getLocation().getX());
    }
    
    @Test
    public void movingLeftWithDoMove() {
        piece.doMove(Direction.LEFT, 1);
        assertEquals(4, piece.getLocation().getX());
    }
            
}
