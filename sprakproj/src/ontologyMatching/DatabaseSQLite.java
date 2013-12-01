package ontologyMatching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSQLite {
	private static DatabaseSQLite instance = null;
	private Connection c;

	public static DatabaseSQLite getInstance() {
		if (instance == null) {
			instance = new DatabaseSQLite();
		}
		return instance;
	}
	
	private DatabaseSQLite() {
		startDb();
		createTables();
		startDb();
	}
	
	private void createTables() {
		createTable("props_A");
		createTable("props_B");
		createTable("props_C");
		createTable("props_D");
		createTable("props_E");
	}
	
	
	private void createTable(String tableName){
		startDb();
		Statement stmt = null;
	    try {
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
	      " (art TEXT, prop TEXT, val TEXT);";
	      
	      String sql2 = "CREATE TABLE IF NOT EXISTS TYPE" +
	    	      "(art TEXT, prop TEXT, val TEXT);";
	   	  stmt.executeUpdate(sql);
	   	  stmt.executeUpdate(sql2);


	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table with name " + tableName + " created/opened successfully");
	    closeDb();
	 }
	

	private void startDb() {
		c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:database_type_count_2.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	public void closeDb() {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public ResultSet getTable() {
		String tableName = "props";
		try {

			PreparedStatement prepStmt = c.prepareStatement("SELECT * FROM " +tableName + ";");
		    ResultSet rs = prepStmt.executeQuery();
		    
		    return rs;
		    
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}

	public void insertToNewTable(String table, String article,
			String property, String value) {
		insertTriple(article, property, value, table);
		
	}
	public void insertTriple(String art, String prop, String val, String table){
		PreparedStatement prepStmt = null;
	    try {
	    	prepStmt = c.prepareStatement("insert into " +table + " values(?,?,?);");
	    	prepStmt.setString(1, art);
	    	prepStmt.setString(2, prop);
	    	prepStmt.setString(3, val);
	    	prepStmt.execute();

	    
	    } catch ( Exception e ) {
	      e.printStackTrace();	
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    //System.out.println("Insertion completed successfully");
	 }
	
}
