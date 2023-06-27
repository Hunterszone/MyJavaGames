package suite.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import chat_engine.Client;

public class ClientTest {

	private Socket socket;
	private Client client;
	private PrintStream output;

	@Before
	public void setup() {
		socket = mock(Socket.class);
		output = mock(PrintStream.class);

		client = new Client("localhost", 1234, socket, output);
	}

	@Test
	public void testRun() throws IOException {
		String expectedOutput = "Enter a nickname: ";
		when(socket.getOutputStream()).thenReturn(output);

		client.run();

		assertEquals(expectedOutput, "Enter a nickname: ");
	}

	@Test
	public void testStopClient() throws IOException {
		client.stopClient();

		verify(output).close();
		verify(socket).close();
	}

	@Test
    public void testStopClientThrowsIOException() {
        Client client = new Client("localhost", 1234, socket, output) {
            @Override
            public void stopClient() throws IOException {
                throw new IOException();
            }
        };

        assertThrows(IOException.class, () -> {
            client.stopClient();
        });
    }
}
