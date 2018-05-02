package tetris.dao;

import java.sql.*;

public class Database {
    
    public Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        
        PreparedStatement statement = connection.prepareStatement("SELECT 1");
        
        PreparedStatement createTable = connection.prepareStatement(""
                + "CREATE TABLE IF NOT EXISTS highscores \n"
                + "(id integer PRIMARY KEY, \n"
                + "name varchar(30) not null, \n"
                + "score integer not null); \n");
        
        Boolean rs = createTable.execute();
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            System.out.println("Toimii");
        } else  {
            System.out.println("Ei toimi");
        }
        return connection;
    }
}
