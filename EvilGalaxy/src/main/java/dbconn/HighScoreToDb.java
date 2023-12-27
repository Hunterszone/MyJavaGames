package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import game_engine.InitObjects;
import util.KilledEnemies;

public class HighScoreToDb {

	private static final int LOGIN_TIMEOUT = 2;
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // for H2 is "org.h2.Driver"
	private static final String DB_URL = "jdbc:mysql://localhost:3306/evilgalaxy"; // for H2 is "jdbc:h2:file:" +
	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "root";
	
	private static String[] enemyAndCount;
	private static Connection conn;	
	private static PreparedStatement preparedStatement;

	// Execute insert query
	public static boolean isInserted() throws SQLException {

		// STEP 1: Register JDBC driver
		registerDriver();
		
		// STEP 2: Open a connection
		openConnection();

		// STEP 3: Empty DB table before operations
		emptyTable();
		
		int counter = 0;
		List<String> enemyNames = InitObjects.initEnemyNames();
		try {
			while (counter < enemyNames.size()) {
				enemyAndCount = KilledEnemies.getEnemiesByTypeAndCount(enemyNames.get(counter));
				preparedStatement = conn
						.prepareStatement("INSERT INTO highscores (ENEMYNAME, COUNTKILLED) " + "VALUES (?,?)");
				preparedStatement.setString(1, enemyAndCount[0]);
				System.out.println("ENEMYNAME added: " + enemyAndCount[0]);
				preparedStatement.setString(2, enemyAndCount[1]);
				System.out.println("COUNTKILLED added: " + enemyAndCount[1]);
				preparedStatement.executeUpdate();
				System.out.println("Records inserted into table!");
				counter++;				
			}
			if (counter <= 2) {
				System.out.println("Goodbye!");
				return true;
			}
            return false;
		} catch (final SQLException se) {
            System.out.println(se.getMessage());
            return false;	
		} finally {
			preparedStatement.close();
			conn.close();
		}
	}

	private static void registerDriver() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		}
	}

	private static void openConnection() {
		System.out.println("Connecting to a selected database...");
		try {
			DriverManager.setLoginTimeout(LOGIN_TIMEOUT);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}
	
	private static void emptyTable() {
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement("DELETE FROM highscores WHERE 1");
			statement.executeUpdate();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} finally {
			try {
				if (statement != null) statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
