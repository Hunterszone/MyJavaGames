package suite.tests;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
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
import org.mockito.MockitoAnnotations;

import dbconn.HighScoreToDb;

public class HighScoreToDbTest {

	@Mock
	private Connection connection;
	@Mock
	private java.sql.Driver driver;
	@Mock
	private ResultSet resultSet;
	@Mock
	private PreparedStatement statement;
	
    private static final String SELECT_QUERY = "SELECT * FROM highscores";
    private static final String INSERT_QUERY = "INSERT INTO highscores (ENEMYNAME, COUNTKILLED) " + "VALUES (?,?)";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // for H2 is "org.h2.Driver"
    
    @Before
    public void setUp() throws SQLException {
    	MockitoAnnotations.initMocks(this);
        when(driver.acceptsURL(HighScoreToDbTest.JDBC_DRIVER)).thenReturn(true);
        when(driver.connect(anyString(), Matchers.<Properties>any())).thenReturn(connection);
    }
    
    @Test
    public void updateTableTest() throws SQLException {
        when(connection.prepareStatement(eq(HighScoreToDbTest.INSERT_QUERY))).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        assertTrue(HighScoreToDb.insertTable());
    }
    
    @Test
    public void readFromTableTest() throws SQLException {
        when(connection.prepareStatement(eq(HighScoreToDbTest.SELECT_QUERY))).thenReturn(statement);
        when(statement.execute()).thenReturn(true);        
    }
    
    @Test
    public void shouldHandleSQLException() throws Exception {
        when(connection.prepareCall(anyString()))
        	.thenThrow(new SQLException("Prepare call"));
        when(connection.isClosed()).thenReturn(true);
    }
}
