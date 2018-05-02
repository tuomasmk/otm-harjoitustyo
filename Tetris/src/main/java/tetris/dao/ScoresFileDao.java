package tetris.dao;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import tetris.components.Score;


public class ScoresFileDao implements Dao<Score, Integer> {
    private String filename;
    private Map<String, Integer> topScores;

    public ScoresFileDao(String filename) {
        this.filename = filename;
        topScores = new HashMap();
        try {
            Stream<String> stream = Files.lines(Paths.get(filename));
            stream.forEachOrdered(x ->  {
                String[] score = x.split(";");
                if (score.length >= 2) {
                    if (topScores.containsKey(score[0])) {
                        if (topScores.get(score[0]) < Integer.parseInt(score[1])) {
                            topScores.put(score[0], Integer.parseInt(score[1]));
                        }
                    } else {
                        topScores.put(score[0], Integer.parseInt(score[1]));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Score findOne(Integer key) throws SQLException {
        return new Score("Pingu", 99);
    }

    @Override
    public List<Score> findAll() throws SQLException {
        List scores = new ArrayList();
        for (String key : topScores.keySet()) {
            scores.add(new Score(key, topScores.get(key)));
        }
        return scores;
    }

    @Override
    public Score saveOrUpdate(Score score) {
        if (topScores.containsKey(score.getPlayer())) {
            if (topScores.get(score.getPlayer()) < score.getScore()) {
                topScores.put(score.getPlayer(), score.getScore());
            }
        } else {
            topScores.put(score.getPlayer(), score.getScore());
        }
        saveToFile();
        return score;
    }
    
    private void saveToFile() {
        Path path = Paths.get(filename);
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                for (String key : topScores.keySet()) {
                    writer.write(key + ";" + topScores.get(key) + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
