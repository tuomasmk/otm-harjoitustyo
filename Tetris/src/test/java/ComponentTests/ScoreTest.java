package ComponentTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import tetris.components.Score;

public class ScoreTest {
    
    Score score;
    
    @Before
    public void setUp() {
        score = new Score("player", 0);
    }
    
    @Test
    public void ScoreReturnsCorrectPlayerName() {
        assertEquals("player", score.getPlayer());
    }
    
    @Test
    public void ScoreReturnsCorrectScore() {
        assertEquals(0, score.getScore());
    }
    
    @Test
    public void ScoreIsSettedCorrectly() {
        assertEquals(0, score.getScore());
        score.setScore(1000);
        assertEquals(1000, score.getScore());
    }
    
    @Test
    public void PlayerIsRenamedCorreclty() {
        assertEquals("player", score.getPlayer());
        score.setPlayer("winner");
        assertEquals("winner", score.getPlayer());
    }
    
    @Test
    public void ScoresAreOrderedCorrectly() {
        Score highScore = new Score(0, "winner", 10000);
        assertTrue(highScore.compareTo(score) < 0);
    }
}
