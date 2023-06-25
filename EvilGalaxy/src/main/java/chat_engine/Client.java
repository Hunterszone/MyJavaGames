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

  public Client(String host, int port) {
    this.host = host;
    this.port = port;
    this.clientThread = null;
  }

  public void run() {
    clientThread = new Thread(() -> {
      try {
        // connect client to server
        Socket client = new Socket(host, port);
        System.out.println("Client successfully connected to server!");

        // Get Socket output stream (where the client sends her messages)
        PrintStream output = new PrintStream(client.getOutputStream());

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
//        sc.close();
        client.close();
      } catch (UnknownHostException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    clientThread.start();
  }

  public void stopClient() {
    if (clientThread != null) {
      clientThread.interrupt();
    }
  }
}
