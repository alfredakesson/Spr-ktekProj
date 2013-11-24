package sprakproj;

import java.sql.*;


public class Database {
	private static Database instance = null;
	private Connection c; 
	
	private Database(){
		startDb();
		createTables();
		closeDb();
	}
	private void createTables() {
		createTable("bornDate");
		createTable("deathDate");
		createTable("dateNotInserted");
		createTable("dateNotInserted2");
		createTable("typeCounter");
		createTable("templates");
		createTable("pageError");
		createTable("kristus");
	}
	
	public static Database getInstance() {
	      if(instance == null) {
	         instance = new Database();
	      }
	      return instance;
	   }
	
	private void createTable(String tableName){
		startDb();
		Statement stmt = null;
	    try {
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
	      " (Subject TEXT, Predicate TEXT, Object TEXT);";
	      
	      String sql2 = "CREATE TABLE IF NOT EXISTS TYPE" +
	    	      "(Subject TEXT, Predicate TEXT, Object TEXT);";
	   	  stmt.executeUpdate(sql);
	   	  stmt.executeUpdate(sql2);


	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table with name " + tableName + " created/opened successfully");
	    closeDb();
	 }
	
	public void insertTriple(String subject, String predicate, String object, String TYPE){
		startDb();
		object = fixDate(object);
		PreparedStatement prepStmt = null;
	    try {
	    	prepStmt = c.prepareStatement("insert into " +TYPE + " values(?,?,?);");
	    	prepStmt.setString(1, subject);
	    	prepStmt.setString(2, predicate);
	    	prepStmt.setString(3, object);
	    	prepStmt.execute();

	    
	    } catch ( Exception e ) {
	      e.printStackTrace();	
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    //System.out.println("Insertion completed successfully");
	    closeDb();
	 }
	
	public void insertTTriple(String subject, String predicate, String object){
		startDb();
		PreparedStatement prepStmt = null;
	    try {
	    	prepStmt = c.prepareStatement("insert into TYPE values(?,?,?);");
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
	    //System.out.println("Insertion completed successfully");
	    closeDb();
	 }
	
	
	
	private String fixDate(String object) {
		String[] list = object.split("-");
		int toAdd = 4 -list[0].length();
		for(int i = 0;i<toAdd;i++){
			list[0] = "0"+list[0];
		}
		if(list.length == 1){
			String[] list2 = {list[0],"00","00"};
			list = list2;
		}
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
	
	private void closeDb(){
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String args[]) {
		new Database();
	}
}
/*
 * public void insertTriple(String subject, String predicate, String object, String TYPE){
		startDb();
		object = fixDate(object);
		PreparedStatement prepStmt = null;
	    try {
	    	prepStmt = c.prepareStatement("insert into " +TYPE + " values(?,?,?);");
	    	prepStmt.setString(1, subject);
	    	prepStmt.setString(2, predicate);
	    	prepStmt.setString(3, object);
	    	prepStmt.execute();

	    
	    } catch ( Exception e ) {
	      e.printStackTrace();	
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    //System.out.println("Insertion completed successfully");
	    closeDb();
	 }
 */

