import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Sender {
  public static void main(String[] args) throws Exception {
    ObjectCreator objectCreator = new ObjectCreator();
    Serializer serializer = new Serializer();
    Scanner input = new Scanner(System.in);

//    System.out.println("Enter host address (localhost/127.0.0.1/10.13.155.41): ");
//    String hostAddress = input.nextLine();
//    System.out.println("Enter port(6666): ");
//    int port = input.nextInt();
    String hostAddress = "10.13.155.41";
    int port = 6666;

    Object object = objectCreator.runObjectCreator();

    Socket socket = new Socket(hostAddress, port);
    Document document = null;
    document = serializer.serialize(object);


    new XMLOutputter().output(document, System.out);
    XMLOutputter xml = new XMLOutputter();
    xml.setFormat(Format.getPrettyFormat());
    xml.output(document, new FileWriter("serialized.xml"));
    System.out.println("Serialized to serialized.xml");

    ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
    outStream.writeObject(document);
    outStream.flush();
    System.out.println("Document sent");

    socket.close();

  }
}
