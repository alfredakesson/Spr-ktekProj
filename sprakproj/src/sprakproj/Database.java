package sprakproj;

import java.sql.*;

public class Database {
	private Connection c; 
	
	
	public Database(){
		startDb();
		createDateTable();
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
		object = fixDate(object);
		PreparedStatement prepStmt = null;
	    try {
	    	prepStmt = c.prepareStatement("insert into DATE values(?,?,?);");
	    	prepStmt.setString(1, subject);
	    	prepStmt.setString(2, predicate);
	    	prepStmt.setString(3, object);
	    	prepStmt.execute();

	      /*stmt = c.createStatement();
	      String sql = "INSERT OR REPLACE INTO DATE(Subject, Predicate, Object) " + 
	      "VALUES(" + "\'" + subject + "\'," + "\'" + predicate + "\'," + "\'" + object + "\'" + ");";
	      System.out.println(sql);
	      stmt.executeUpdate(sql);
	      stmt.close();*/
	    
	    } catch ( Exception e ) {
	      e.printStackTrace();	
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Insertion completed successfully");
	 }
	
	
	
	
	private String fixDate(String object) {
		String[] list = object.split("-");
		if(list[1].length() == 1){
			list[1] = "0"+list[1]; 
		}
		if(list[2].length() == 1){
			list[2] = "0"+list[2]; 
		}
		return list[0]+"-"+list[1]+"-"+list[2];
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


