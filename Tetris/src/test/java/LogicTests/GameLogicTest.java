package LogicTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import tetris.components.Piece;
import tetris.logics.GameLogic;


public class GameLogicTest {

    GameLogic tetris;
    
    @Before
    public void setUp() {
        tetris = new GameLogic();
    }
    
    @Test
    public void getGameHeight() {
        assertEquals(20, tetris.getGameHeight());
    }
    
    @Test
    public void getGameWidth() {
        assertEquals(10, tetris.getGameWidth());
    }
    
    @Test
    public void gameHasInitializedPiece() {
        assertTrue(tetris.getPiece() != null);
    }
    
    @Test
    public void advanceMovesPieceDown() {
        int startY = tetris.getPiece().getLocation().getY();
        tetris.advance();
        assertEquals(startY + 1, tetris.getPiece().getLocation().getY());
    }
    
    @Test
    public void gameCreatesNewPieceWhenTheLastOneHitsFloorOrAnotherPiece() {
        Piece piece = tetris.getPiece();
        piece.dropAllTheWay();
        tetris.advance();
        assertNotEquals(piece, tetris.getPiece());
    }
}
