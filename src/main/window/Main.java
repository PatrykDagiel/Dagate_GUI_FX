package main.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import telnet.sockets.TelnetClient;

public class Main extends Application {

    private static TelnetClient telnet_Client;

    @Override
    public void init() throws Exception {
        super.init();
        System.out.printf("Inside init method");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label telnet_Output = new Label();
        telnet_Client = new TelnetClient();
        telnet_Client.execute(telnet_Output);

        FlowPane rootNode = new FlowPane();
        primaryStage.setTitle("Dagate_GUI");
        primaryStage.setScene(new Scene(rootNode, 500, 375));
        rootNode.getChildren().add(telnet_Output);

        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Inside stop method");
    }


    public static void main(String[] args) {
        launch(args);
    }
}

//public class main_form implements ActionListener {
//
//    private JLabel jlab;
//    public JTextArea testOutput;
//
//    public main_form() {
//
//        JFrame jfrm = new JFrame("Egate GUI");
//        jfrm.setSize(400,400);
//        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        jlab = new JLabel();
//        testOutput = new JTextArea(10,10);
//
//        JMenuBar jmb = new JMenuBar();
//
//        // Zakladka Plik w pasku menu
//        JMenu jmFile = new JMenu("File");
//        JMenuItem item_1 = new JMenuItem("Import LUA File");
//        JMenuItem item_2 = new JMenuItem("Close EGate GUI");
//        jmFile.add(item_1);
//        jmFile.add(item_2);
//        jmb.add(jmFile);
//
//        // Zakladka opcji w pasku menu
//        JMenu jmOptions = new JMenu("Options");
//        JMenuItem item_o_revive = new JMenuItem("Setup EGate connections");
//        JMenuItem item_o_2 = new JMenuItem("Close EGate connections");
//        JMenuItem item_o_3 = new JMenuItem("Start KPI gathering");
//        jmOptions.add(item_o_revive);
//        jmOptions.add(item_o_2);
//        jmOptions.add(item_o_3);
//        jmb.add(jmOptions);
//
//        jfrm.add(jlab);
//        jfrm.add(testOutput);
//
//        item_1.addActionListener(this);
//        item_2.addActionListener(this);
//        item_o_revive.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String[] commands = {"/bin/bash", "-c", "sudo ./edaemon --port 10000 && sudo ./edaemon --port 30000 && sleep 1"};
//                String[] command_2 = {"/bin/bash", "-c", "sudo ./edaemon --port 30000"};
//                String[] command_3 = {"/bin/bash", "-c", "sudo sleep 1"};
//                Process p, p2;
////                try {
////                    // Proba wykonania komendy revive_all [postawienie daemonow egate, portów itd]
//////                    p = Runtime.getRuntime().exec("sudo ./edaemon --port 10000 && sudo ./edaemon --port 30000 && ./egate --port 20000 && sleep 1 && telnet 0 20000");
////
////                    p = Runtime.getRuntime().exec(commands);
////                    p2 = Runtime.getRuntime().exec(command_2);
////                    p = Runtime.getRuntime().exec(command_3);
////                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p2.getInputStream()));
////                    p.waitFor();
////                    p2.waitFor();
////                    String line = null;
////                    System.out.println("Executing command : ");
////                    while((line = stdInput.readLine()) != null) {
////                        System.out.println(line);
////                    }
////                    p.destroy();
////                    p2.destroy();
////
//////                    TelnetClient polaczenie_testowe = new TelnetClient();
//////                    polaczenie_testowe.execute(testOutput);
////
////
////                } catch (Exception x) {
////                    System.out.println("To jest badziewie " + x);
////                }
//
//                try {
//                    TelnetClient polaczenie_testowe = new TelnetClient();
//                    polaczenie_testowe.execute(testOutput);
//                } catch (Exception x) {
//                    System.out.println("Blad bloku try dla Socketa " + x);
//                }
//
//
//            }
//        });
//        item_o_2.addActionListener(this);
//        item_o_3.addActionListener(this);
//
//        jfrm.setJMenuBar(jmb);jfrm.setVisible(true);
//
//    }
//
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new main_form();
//            }
//        });
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String option = e.getActionCommand();
//        if(option.equals("Close EGate GUI")){
//            System.exit(0);
//        }
//        jlab.setText("The option chosen is " + option);
//
//    }
//
//
//
//}
