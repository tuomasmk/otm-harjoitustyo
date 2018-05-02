package ComponentTests;

import javafx.scene.paint.Color;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import tetris.components.Direction;
import tetris.components.LPiece;
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
        assertEquals(Color.DODGERBLUE, piece.getColor());
    }
    
    @Test
    public void rightBorderColorIsReturned() {
        assertEquals(Color.BLUE, piece.getBorderColor());
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
    public void rotatingPiece() {
        piece.rotate();
        assertEquals(5, piece.getLocation().getX());
        assertEquals(10, piece.getLocation().getY());
    }
    
    @Test
    public void createLPiece() {
        piece = new LPiece(tetris, 1, 2);
        assertEquals(1, piece.getLocation().getX());
        assertEquals(2, piece.getLocation().getY());
    }
    
    @Test
    public void createSPiece() {
        piece = new LPiece(tetris, 3, 4);
        assertEquals(3, piece.getLocation().getX());
        assertEquals(4, piece.getLocation().getY());
    }
    
    @Test
    public void createTPiece() {
        piece = new LPiece(tetris, 5, 6);
        assertEquals(5, piece.getLocation().getX());
        assertEquals(6, piece.getLocation().getY());
    }
    
    @Test
    public void createJPiece() {
        piece = new LPiece(tetris, 7, 8);
        assertEquals(7, piece.getLocation().getX());
        assertEquals(8, piece.getLocation().getY());
    }
    
    @Test
    public void createIPiece() {
        piece = new LPiece(tetris, 5, 5);
        assertEquals(5, piece.getLocation().getX());
        assertEquals(5, piece.getLocation().getY());
    }
    
    @Test
    public void createReverseSPiece() {
        piece = new LPiece(tetris, 6, 6);
        assertEquals(6, piece.getLocation().getX());
        assertEquals(6, piece.getLocation().getY());
    }
}
