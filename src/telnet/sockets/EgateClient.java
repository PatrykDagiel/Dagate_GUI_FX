package telnet.sockets;

import javafx.scene.control.TextArea;

import java.io.*;
import org.apache.commons.net.telnet.TelnetClient;



public class EgateClient implements Runnable {

    TelnetClient tc = null;

    public EgateClient() {
        tc = new TelnetClient();
    }

    public void connect_to_egate() throws IOException {
        try {
            tc.connect("127.0.0.1", 20000);
        } catch (IOException e) {
            System.out.println("Issues with telnet connection to -p 20000 : " + e.toString());
        }
    }

    public void close_connection_to_egate() {
        try {
            tc.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write_message_to_egate(String message) {
        
    }

    public static void read_message_from_egate(InputStream input) throws IOException {

    }


    @Override
    public void run() {
        System.out.println("Inside Egate_Client run method");
    }
}

