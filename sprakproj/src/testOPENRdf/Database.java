package testOPENRdf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private static Database instance = null;
	private Connection c;

	private Database() {
		startDb();
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

	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
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
		String tableName = "type_tabel_3";
		Statement stmt = null;
		try {
			
			stmt = c.createStatement();
			String sql = "SELECT * FROM " + tableName + ";";
		    ResultSet rs = stmt.executeQuery(sql);
		    
		    return rs;
		    
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return null;
	}
}
