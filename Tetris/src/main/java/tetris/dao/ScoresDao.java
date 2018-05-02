package tetris.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import tetris.components.Score;


public class ScoresDao implements Dao<Score, Integer> {
    protected Database database;
    protected String tableName;

    public ScoresDao(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }

    @Override
    public Score findOne(Integer key) throws SQLException {
        try (Connection conn = database.connect()) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM " + tableName + " WHERE id = ?");
            stmt.setInt(1, key);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return createFromRow(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error when looking for a row in " 
                + tableName + " with id " + key);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Score> findAll() throws SQLException {
        List<Score> tasks = new ArrayList<>();

        try (Connection conn = database.connect();
            ResultSet result = conn.prepareStatement(
                "SELECT * FROM " + tableName).executeQuery()) {

            while (result.next()) {
                tasks.add(createFromRow(result));
            }
        }

        return tasks;
    }

    @Override
    public Score saveOrUpdate(Score score) throws SQLException {
        try (Connection conn = database.connect()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO " + tableName 
                + " (name, score) VALUES (?, ?)");
            stmt.setString(1, score.getPlayer());
            stmt.setInt(2, score.getScore());
            stmt.executeUpdate();
        }

        return findByName(score.getPlayer());

    }

    private Score findByName(String name) throws SQLException {
        try (Connection conn = database.connect()) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT id, name, MAX(score) as score FROM " 
                + tableName + " WHERE name = ?");
            stmt.setString(1, name);

            try (ResultSet result = stmt.executeQuery()) {
                if (!result.next()) {
                    return null;
                }

                return createFromRow(result);
            }
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Score createFromRow(ResultSet resultSet) throws SQLException {
        return new Score(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("score"));
    }

}
