package ComponentTests;

import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import tetris.components.IPiece;
import tetris.components.JPiece;
import tetris.components.LPiece;
import tetris.components.Piece;
import tetris.components.Point;
import tetris.components.ReverseSPiece;
import tetris.components.SPiece;
import tetris.components.SquarePiece;
import tetris.components.TPiece;
import tetris.logics.GameLogic;


public class PieceRotationTest {
    
    Piece piece;
    GameLogic tetris;
    Point[] expected;
    
    @Before
    public void setUp() {
        tetris = new GameLogic();
    }

    @Test
    public void IPieceIsRotatedCorrectly() {
        piece = new IPiece(tetris, 0, 0);
        rotate(5, piece);
        expected = new Point[]{
            new Point(0, 0),
            new Point(1, 0),
            new Point(2, 0),
            new Point(3, 0)
        };
        assertEquals(Arrays.toString(expected), Arrays.toString(piece.getParts()));
    }
    @Test
    
    public void JPieceIsRotatedCorrectly() {
        piece = new JPiece(tetris, 0, 0);
        rotate(6, piece);
        expected = new Point[]{
            new Point(0, 0),
            new Point(1, 0),
            new Point(0, 1),
            new Point(0, 2)
        };
        assertEquals(Arrays.toString(expected), Arrays.toString(piece.getParts()));
    }
    
    @Test
    public void LPieceIsRotatedCorrectly() {
        piece = new LPiece(tetris, 0, 0);
        rotate(7, piece);
        expected = new Point[]{
            new Point(0, 1),
            new Point(1, 1),
            new Point(2, 1),
            new Point(2, 0)
        };
        assertEquals(Arrays.toString(expected), Arrays.toString(piece.getParts()));
    }
    
    @Test
    public void ReverseSPieceIsRotatedCorrectly() {
        piece = new ReverseSPiece(tetris, 0, 0);
        rotate(8, piece);
        expected = new Point[]{
            new Point(0, 0),
            new Point(1, 0),
            new Point(1, 1),
            new Point(2, 1)
        };
        assertEquals(Arrays.toString(expected), Arrays.toString(piece.getParts()));
    }
    
    @Test
    public void SPieceIsRotatedCorrectly() {
        piece = new SPiece(tetris, 0, 0);
        rotate(9, piece);
        expected = new Point[]{
            new Point(0, 0),
            new Point(0, 1),
            new Point(1, 1),
            new Point(1, 2)
        };
        assertEquals(Arrays.toString(expected), Arrays.toString(piece.getParts()));
    }
    
    @Test
    public void TPieceIsRotatedCorrectly() {
        piece = new TPiece(tetris, 0, 0);
        rotate(10, piece);
        expected = new Point[]{
            new Point(0, 0),
            new Point(1, 0),
            new Point(2, 0),
            new Point(1, 1)
        };
        assertEquals(Arrays.toString(expected), Arrays.toString(piece.getParts()));
    }
    
    @Test
    public void SquarePieceIsRotatedCorrectly() {
        piece = new SquarePiece(tetris, 0, 0);
        rotate(11, piece);
        expected = new Point[]{
            new Point(0, 0),
            new Point(0, 1),
            new Point(1, 0),
            new Point(1, 1)
        };
        assertEquals(Arrays.toString(expected), Arrays.toString(piece.getParts()));
    }
    
    private void rotate(int times, Piece piece) {
        for (int i = 0; i < times; i++) {
            piece.rotate();
        }
    }
}
