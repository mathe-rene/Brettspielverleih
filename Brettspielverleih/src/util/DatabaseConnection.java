package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:Brettspielverleih.db";
    
  //  private static  DatabaseConnection instance;
    private static  Connection connection;
    

    
    public static Connection getConnection() throws SQLException {
    	try {
            Class.forName("org.sqlite.JDBC");
	    	if(DatabaseConnection.connection == null || DatabaseConnection.connection.isClosed()) {
	    		DatabaseConnection.connection = DriverManager.getConnection(URL);
	    	}
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    	 return DatabaseConnection.connection;
    }
    
    
    
    
    
//    public static Connection getConnection() throws SQLException {
//        if( instance == null) {
//        	new DatabaseConnection();
//        }
//        return DatabaseConnection.connection;
//    }
}
