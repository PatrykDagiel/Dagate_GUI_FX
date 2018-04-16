package telnet.sockets;

import javafx.scene.control.TextArea;

import java.io.*;
import org.apache.commons.net.telnet.TelnetClient;



public class EgateClient implements Runnable {

    TelnetClient tc = null;
    TextArea testOutput = null;

    public EgateClient(TextArea x) {
        this.testOutput = x;
    }

    public void connect_to_egate() throws IOException {
        try {
            tc = new TelnetClient();
            tc.connect("0.0.0.0", 20000);
        } catch (IOException e) {
            System.out.println("Issues with telnet connection to -p 20000 : " + e.toString());
        }
    }

    @Override
    public void run() {
        System.out.println("Inside Egate_Client run method");
    }
}

