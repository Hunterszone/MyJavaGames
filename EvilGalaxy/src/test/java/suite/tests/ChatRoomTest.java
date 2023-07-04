package suite.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import chat_engine.ChatRoom;
import chat_engine.Client;

public class ChatRoomTest {
    private ChatRoom chatRoom;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    @Before
    public void setup() throws IOException {
        chatRoom = new ChatRoom();
        serverSocket = mock(ServerSocket.class);
        clientSocket = mock(Socket.class);
        writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);
        
        // Inject mocked objects into the ChatRoom
        ChatRoom.serverSocket = serverSocket;
        ChatRoom.client = Mockito.mock(Client.class);
    }

    @After
    public void cleanup() throws IOException {
        // Close resources
        writer.close();
        reader.close();
        clientSocket.close();
        serverSocket.close();
    }

    @Test
    public void testStart() throws IOException {
        // Configure mocks
        when(serverSocket.accept()).thenReturn(clientSocket);
        when(clientSocket.getInetAddress()).thenReturn(Mockito.mock(InetAddress.class));
        when(clientSocket.getOutputStream()).thenReturn(Mockito.mock(OutputStream.class));
        when(clientSocket.getInputStream()).thenReturn(Mockito.mock(InputStream.class));

        // Invoke the method under test
        chatRoom.start(1234);

        // Perform assertions
        assertTrue("ChatRoom is not running!", ChatRoom.serverSocket.isBound());
    }

    @Test
    public void testStopServer() throws IOException {
        // Configure mocks
        List<ChatRoom.ClientHandler> clients = new ArrayList<>();
        ChatRoom.ClientHandler clientHandler1 = mock(ChatRoom.ClientHandler.class);
        ChatRoom.ClientHandler clientHandler2 = mock(ChatRoom.ClientHandler.class);
        clients.add(clientHandler1);
        clients.add(clientHandler2);
        ChatRoom.clients = clients;

        // Invoke the method under test
        ChatRoom.stopServer();

        // Perform assertions
        assertFalse("ChatRoom is running!", chatRoom.isRunning());
        verify(clientHandler1).sendMessage(anyString());
        verify(clientHandler1).interrupt();
        verify(clientHandler2).sendMessage(anyString());
        verify(clientHandler2).interrupt();
        verify(serverSocket).close();
        assertTrue(ChatRoom.clients.isEmpty());
    }
    
    @Test
    public void testServerBroadcast() throws IOException {
    	when(serverSocket.accept()).thenReturn(clientSocket);
    	ChatRoom.ClientHandler clientHandler = new ChatRoom.ClientHandler(clientSocket);
        
    	clientHandler.run();

    	verify(clientSocket).close();
    }
}