package chat_engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private static volatile boolean running = true;
    public static List<ClientHandler> clients = new ArrayList<>();
    public static ServerSocket serverSocket;
    public static Client client = new Client("127.0.0.1", 1234);

    public synchronized void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Chat room started on port " + port);
            Socket clientSocket = null;
            
            try {
                while (isRunning()) {
                    clientSocket = serverSocket.accept();

                    System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    clients.add(clientHandler);
                    clientHandler.start();
                }
            } catch (SocketException e) {
                e.printStackTrace();
            } finally {
                ChatRoom.stopServer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	ChatRoom.stopServer();
        }
    }

    public static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                if (clientSocket == null) {
                    clientSocket = new Socket("127.0.0.1", 1234);
                }
                           
                if (clientSocket.isConnected()) {
                	reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    writer = new PrintWriter(clientSocket.getOutputStream(), true);

                    String message;
                    while ((message = reader.readLine()) != null) {
                        broadcast(message);
                        if (message.equalsIgnoreCase("exit")) {
                            client.stopClient();
                            break;
                        }
                    }
                } else {
                    System.err.println("Client socket is not connected to the server.");
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            writer.println(message);
        }
    }

    private static void broadcast(String message) {
        System.out.println("Broadcasting message: " + message);
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
    
    public static synchronized void stopServer() {
        try {
            // Notify connected clients about server shutdown
            String terminationMessage = "Server is shutting down. Goodbye.";
            for (ClientHandler client : clients) {
                client.sendMessage(terminationMessage);
                client.interrupt(); // Interrupt the ClientHandler thread
            }

            // Clear the clients list
            clients.clear();

            // Close the serverSocket to stop accepting new connections
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

            // Set running flag to false to stop the server's main loop
            running = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isRunning() {
        return running;
    }
}