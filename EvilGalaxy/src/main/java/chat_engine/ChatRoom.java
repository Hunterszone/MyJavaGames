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
    private static List<ClientHandler> clients = new ArrayList<>();
    private static ServerSocket serverSocket;
    private static volatile boolean running = true;

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
                serverSocket.close();
                setRunning(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            setRunning(false);
        }
    }

    class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);

                String message;
                while ((message = reader.readLine()) != null) {
                    broadcast(message);
                    if (message.equalsIgnoreCase("exit")) {
                    	Client.stopClient();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                    reader.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendMessage(String message) {
            writer.println(message);
        }
    }

    private void broadcast(String message) {
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
            setRunning(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isRunning() {
        return running;
    }

    private synchronized static void setRunning(boolean running) {
    	running = running;
    }
}