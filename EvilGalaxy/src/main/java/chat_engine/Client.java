package chat_engine;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private String host;
    private int port;
    private Thread clientThread;
    private PrintStream output;
    public Socket socket;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.clientThread = null;
        this.socket = null;
        this.output = null;
    }

    // Constructor for testing with injected Socket and PrintStream
    public Client(String host, int port, Socket socket, PrintStream output) {
        this.host = host;
        this.port = port;
        this.clientThread = null;
        this.socket = socket;
        this.output = output;
    }

    public void run() {
        clientThread = new Thread(() -> {
            try {
                if (socket == null) {
                    // connect client to server
                    socket = new Socket(host, port);
                }
                System.out.println("Client successfully connected to server!");

                // Get Socket output stream (where the client sends her messages)
                output = new PrintStream(socket.getOutputStream());

                // ask for a nickname
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter a nickname: ");
                String nickname = sc.nextLine();

                // send nickname to server
                output.println(nickname);

                // read messages from the keyboard and send to the server
                System.out.println("Messages: \n");

                // while new messages
                while (sc.hasNextLine() && !Thread.currentThread().isInterrupted()) {
                    output.println(sc.nextLine());
                }

                // end Ctrl+D or thread interrupted
                output.close();
                sc.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        clientThread.start();
    }

    public void stopClient() throws IOException {
        if (clientThread != null) {
            clientThread.interrupt();
        }
        if (output != null) {
            output.close();
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
