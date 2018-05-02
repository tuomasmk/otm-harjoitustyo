package tetris.components;

import java.sql.Timestamp;


public class Score implements Comparable<Score> {
    private int id;
    private String player;
    private int score;
    
    public Score(int id, String player, int score) {
        this.id = id;
        this.player = player;
        this.score = score;
    }
    
    public Score(String player, int score) {
        this.player = player;
        this.score = score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public String getPlayer() {
        return player;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Score o) {
        return o.score - this.score;
    }
}
