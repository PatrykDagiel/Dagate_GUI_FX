package telnet.sockets;

import javafx.scene.control.TextArea;

import java.io.*;
import org.apache.commons.net.telnet.TelnetClient;


public class EgateClient implements Runnable {

    TelnetClient tc = null;

    public void execute(TextArea testOutput) throws IOException {

        try {
            tc = new TelnetClient();
            tc.connect("0.0.0.0", 20000);
        } catch (IOException e) {
            System.out.println("Badziweny stos: " + e.toString());
        }
        IOUtil.readWrite(tc.getInputStream(), tc.getOutputStream(), System.in, System.out);
        try {
            tc.disconnect();
        } catch (IOException e) {
            System.out.println("Badziwie: " + e.toString());
        }
    }

    @Override
    public void run() {
        InputStream instr = tc.getInputStream();
        try {
            byte[] buffer = new byte[1024];
            int ret_read = 0;
            do {
                ret_read = instr.read(buffer);
                if (ret_read > 0) {
                    System.out.println(new String(buffer, 0, ret_read));
                }
            } while (ret_read >= 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            tc.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

