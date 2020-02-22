package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import game_engine.Collisions;
import game_engine.InitObjects;

public class HighScoreToDb {

	private static String[] enemyAndCount;
	private static List<String> enemyNames = InitObjects.getEnemyNames();

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // for H2 is "org.h2.Driver"
	private static final String DB_URL = "jdbc:mysql://localhost:3306/evilgalaxy"; // for H2 is "jdbc:h2:file:" +
																					// user.home +
																					// "/IdeaProjects/demo-rest-api/DB_OUT";
																					// //change to your DB_OUT path

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "root";

	public static void initDbConn() throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		int counter = 0;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// Empty DB before operations
			preparedStatement = conn.prepareStatement("DELETE FROM highscores WHERE 1");
			preparedStatement.executeUpdate();

			while (counter < enemyNames.size()) {
				// STEP 3: Execute a query
				enemyAndCount = Collisions.getNameAndKilledCount(enemyNames.get(counter));
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

			// STEP 4: Clean-up environment
			preparedStatement.close();
			conn.close();
		} catch (Exception se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			// finally block used to close resources
			if (preparedStatement != null)
				preparedStatement.close();
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}
}
