import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Receiver {
  public static void main(String[] args) throws Exception {
    Scanner input = new Scanner(System.in);
    Deserializer deserializer = new Deserializer();
    Visualizer visualizer = new Visualizer();

    System.out.println("Enter port number");
    int port = input.nextInt();

    InetAddress inetAddress = InetAddress.getLocalHost();
    System.out.println("Starting server at " + inetAddress.getHostAddress());

    ServerSocket serverSocket = new ServerSocket(port, 0, inetAddress);

    while (true) {
      Socket socket = serverSocket.accept();
      System.out.print("Received document");
      ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
      Document doc = (Document) inputStream.readObject();
      Object obj = deserializer.deserialize(doc);

      visualizer.inspect(obj, true);

      socket.close();
    }


  }

}
