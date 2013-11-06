package sprakproj;

import java.sql.*;

public class Database {
	private Connection c; 
	
	
	public Database(){
		startDb();
		createDateTable();
		insertTriple("Jens", "fšdd", "1989-03-03");
	}

	private void createDateTable(){
		
		
		Statement stmt = null;
	    try {
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE IF NOT EXISTS DATE" +
	      "(Subject TEXT, Predicate TEXT, Object TEXT);";
	    		  
	   	  stmt.executeUpdate(sql);


	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table with name DATE created/opened successfully");
	 }
	
	public void insertTriple(String subject, String predicate, String object){
		Statement stmt = null;
	    try {
	     

	      stmt = c.createStatement();
	      String sql = "INSERT OR REPLACE INTO DATE(Subject, Predicate, Object) " + 
	      "VALUES(" + "\'" + subject + "\'," + "\'" + predicate + "\'," + "\'" + object + "\'" + ");";
	      System.out.println(sql);
	      stmt.executeUpdate(sql);
	      stmt.close();
	    
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Insertion completed successfully");
	 }
	
	
	
	
	private void startDb(){
		c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}
	
	public static void main(String args[]) {
		new Database();
	}
}


