package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.Game;

public class HighScoreToDb {

	private static String[] getTreasuresAndKilledCount;

	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // for H2 is "org.h2.Driver"
	private static final String DB_URL = "jdbc:mysql://localhost:3306/gunman"; // for H2 is "jdbc:h2:file:" +
																				// user.home +
																				// "/IdeaProjects/demo-rest-api/DB_OUT";
																				// //change to your DB_OUT path

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "root";

	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			// STEP 1: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 2: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// Empty DB table before operations
			preparedStatement = conn.prepareStatement("DELETE FROM highscores WHERE 1");
			preparedStatement.executeUpdate();
			preparedStatement.close();

			// STEP 3: Execute a query
			getTreasuresAndKilledCount = Game.getTreasuresAndKilledCount();
			preparedStatement = conn.prepareStatement("INSERT INTO highscores (TREASURES, ENEMIES) " + "VALUES (?,?)");
			preparedStatement.setString(1, getTreasuresAndKilledCount[0]);
			System.out.println("TREASURES added: " + getTreasuresAndKilledCount[0]);
			preparedStatement.setString(2, getTreasuresAndKilledCount[1]);
			System.out.println("ENEMIES added: " + getTreasuresAndKilledCount[1]);
			preparedStatement.executeUpdate();
			System.out.println("Records inserted into table!");

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
