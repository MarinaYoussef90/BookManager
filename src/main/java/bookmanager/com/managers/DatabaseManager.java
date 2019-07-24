package bookmanager.com.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test";

	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";

	static Connection conn = null; 
	
	public void initializeDB() {
		 Statement stmt = null; 
	      try { 
	         // STEP 1: Register JDBC driver 
	         Class.forName(JDBC_DRIVER); 
	             
	         //STEP 2: Open a connection 
	         System.out.println("Connecting to database..."); 
	         conn = DriverManager.getConnection(DB_URL,USER,PASS);  
	         
	         //STEP 3: Execute a query 
	         stmt = conn.createStatement(); 
	        //String sql = "drop table BOOKS ";
	         String sql =  "CREATE TABLE IF NOT EXISTS BOOKS " + 
	            "(id INTEGER not NULL auto_increment, " + 
	            " title  VARCHAR(255), " +  
	            " author  VARCHAR(255), " +  
	            " description  VARCHAR(255)," +  
	            " PRIMARY KEY ( id ))";  
	         stmt.executeUpdate(sql);
	         // STEP 4: Clean-up environment 
	         stmt.close(); 
	      //   conn.close(); 
	      } catch(SQLException se) { 
	         //Handle errors for JDBC 
	         se.printStackTrace(); 
	      } catch(Exception e) { 
	         //Handle errors for Class.forName 
	         e.printStackTrace(); 
	      } 
		finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		} 
	}

	public void finalizeDB() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	} 

	public Connection getConnection() {
		if(conn == null) {
			 try {
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				 return conn;
			} catch (SQLException e) {
				e.printStackTrace();
			}  
		}
		return conn;
	}
	
}
