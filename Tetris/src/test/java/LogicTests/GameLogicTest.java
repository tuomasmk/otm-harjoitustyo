package LogicTests;

import DatabaseTests.DatabaseTestUtils;
import java.sql.SQLException;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import tetris.components.Piece;
import tetris.dao.Database;
import tetris.logics.GameLogic;


public class GameLogicTest {

    GameLogic tetris;
    DatabaseTestUtils utils;
    
    @Before
    public void setUp() throws SQLException {
        utils = new DatabaseTestUtils();
        utils.initializeTestDb(new Database("test.db"));
        tetris = new GameLogic("Pingu", "test.db", "highscores");
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
    public void advanceMovesPieceDownward() {
        int startY = tetris.getPiece().getLocation().getY();
        tetris.advance();
        assertEquals(startY + 1, tetris.getPiece().getLocation().getY());
    }
    
    @Test
    public void gameCreatesNewPieceWhenTheLastOneHitsFloor() {
        Piece piece = tetris.getPiece();
        piece.dropAllTheWay();
        tetris.advance();
        assertNotEquals(piece, tetris.getPiece());
    }
    
    @Test
    public void gameFetchesHighscoresFromTheDatabase() throws SQLException {
        assertEquals(10000, tetris.getHighscore());
        assertEquals(5000, tetris.getPersonalBest());
    }
}
