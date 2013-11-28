package ontologyMatching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSQLite {
	private static DatabaseSQLite instance = null;
	private Connection c;

	private DatabaseSQLite() {
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

	public static DatabaseSQLite getInstance() {
		if (instance == null) {
			instance = new DatabaseSQLite();
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
}
