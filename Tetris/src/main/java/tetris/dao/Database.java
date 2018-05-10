package tetris.dao;

import java.sql.*;

public class Database {
    private String name;

    public Database(String name) {
        if (name.isEmpty()) {
            name = "database.db";
        }
        this.name = name;
    }
    
    public Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + name);
        
        connection.prepareStatement(""
            + "CREATE TABLE IF NOT EXISTS highscores \n"
            + "(id integer PRIMARY KEY, \n"
            + "name varchar(30) not null, \n"
            + "score integer not null); \n")
            .execute();
        
        return connection;
    }
}
