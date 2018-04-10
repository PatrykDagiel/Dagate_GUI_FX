package telnet.sockets;

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

    public void execute(JTextArea testOutput) throws IOException {
        try {
            pingSocket = new Socket("0.0.0.0", 20000);
            out = new PrintWriter(pingSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            String linia = null;
            while((linia = in.readLine()) != null) {
                testOutput.setText(linia);
            }
        } catch(ConnectException dramat) {
            testOutput.setText("There is no connection as we have " + dramat);
            return;
        }

        out.println("Closing connection");
        out.close();
        in.close();
        pingSocket.close();
    }


}
