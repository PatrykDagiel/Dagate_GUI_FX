package telnet.sockets;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class TelnetClient {
    private Socket pingSocket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public void execute(TextArea testOutput) throws IOException {
        try {
            testOutput.appendText("Initial text inside area");
            System.out.println("Checkpoint_1");
            pingSocket = new Socket("0.0.0.0", 20000);
            out = new PrintWriter(pingSocket.getOutputStream(), false);
            in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            String linia = null;
            System.out.println("Checkpoint_2");
            while((linia = in.readLine()) != null) {
                testOutput.appendText(linia);
            }
            System.out.println("Checkpoint_3");
        } catch(Exception dramat) {
            testOutput.appendText("There is no connection as we have " + dramat);
        }

        out.println("Closing connection");
        out.close();
        in.close();
        pingSocket.close();
    }


}
