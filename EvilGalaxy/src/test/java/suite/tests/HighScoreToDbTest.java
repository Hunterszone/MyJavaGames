package suite.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dbconn.HighScoreToDb;

public class HighScoreToDbTest {

	@Mock
	private Connection mockConnection;

	@Mock
	private java.sql.Driver driver;

	@Mock
	private ResultSet resultSet;

	@Mock
	private PreparedStatement mockStatement;

	private static final String SELECT_QUERY = "SELECT * FROM highscores";
	private static final String INSERT_QUERY = "INSERT INTO highscores (ENEMYNAME, COUNTKILLED) " + "VALUES (?,?)";
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // for H2 is "org.h2.Driver"

	@Before
	public void setUp() throws SQLException {
		MockitoAnnotations.initMocks(this);
		when(driver.acceptsURL(HighScoreToDbTest.JDBC_DRIVER)).thenReturn(true);
		when(driver.connect(anyString(), Matchers.<Properties>any())).thenReturn(mockConnection);
		when(resultSet.next()).thenReturn(true);
	}

	@Test
	public void testMockDBConnection() throws SQLException {
		when(mockConnection.createStatement()).thenReturn(mockStatement);
		when(mockConnection.createStatement().executeUpdate(Mockito.any())).thenReturn(1);
		
		boolean value = HighScoreToDb.doInsert();
	    assertEquals(value, true);
	    
		verify(mockConnection, Mockito.times(1)).createStatement();
	}

	@Test
	public void updateTableTest() throws SQLException {
		when(mockConnection.prepareStatement(eq(HighScoreToDbTest.INSERT_QUERY))).thenReturn(mockStatement);
		verify(mockConnection, Mockito.times(0)).prepareStatement(anyString());
	}

	@Test
	public void readFromTableTest() throws SQLException {
		when(mockConnection.prepareStatement(eq(HighScoreToDbTest.SELECT_QUERY))).thenReturn(mockStatement);
		when(mockStatement.execute()).thenReturn(true);
		verify(mockConnection, Mockito.times(0)).prepareStatement(anyString());
	}

	@Test
	public void shouldHandleSQLException() throws SQLException {
		when(mockConnection.prepareCall(anyString())).thenThrow(new SQLException("Prepare call"));
		verify(mockConnection, Mockito.times(0)).isClosed();
		verify(mockConnection, Mockito.times(0)).prepareCall(anyString());
	}
}
