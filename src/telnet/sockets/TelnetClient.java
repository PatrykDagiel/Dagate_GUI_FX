package telnet.sockets;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

public class TelnetClient {
    private Socket pingSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public void send_egate_message(String cmd) throws IOException {
        DataOutputStream dOut = new DataOutputStream(pingSocket.getOutputStream());
        dOut.writeUTF(cmd);
        dOut.flush();
//        dOut.close();
    }

    public void execute(TextArea testOutput) throws IOException {
        try {
            System.out.println("Checkpoint_1, localhost is " + InetAddress.getLocalHost());
            pingSocket = new Socket("localhost", 20000);
            System.out.println("Adres INET: " + pingSocket.getInetAddress() + " Port: " + pingSocket.getPort() + " local Port" + pingSocket.getLocalAddress() );
            out = new PrintWriter(pingSocket.getOutputStream(), false);
            in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            String linia = null;
            System.out.println("Checkpoint_2");

            while ((linia = in.readLine()) != null) {
                System.out.println("Dzieje sie badziewie " + linia);
            }
            System.out.println("Checkpoint_3");
        } catch (Exception dramat) {
            testOutput.appendText("There is no connection as we have " + dramat);
        }

            System.out.println("Closing connection");
            out.close();
            in.close();
            pingSocket.close();

    }
}

