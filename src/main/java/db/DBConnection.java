package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Singleton Design pattern
//The class has only one instance and provides a global access point to it.

public class DBConnection {

    private static DBConnection instance;
    private final Connection connection;

    private DBConnection() throws SQLException {
       connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade_db","root","199161500318");
    }

    public Connection getConnection(){
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        return instance==null?instance = new DBConnection():instance;
    }

}
