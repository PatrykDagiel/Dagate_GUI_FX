package main.window;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import telnet.sockets.TelnetClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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

        BorderPane rootNode = new BorderPane();
        primaryStage.setTitle("Dagate_GUI");
        primaryStage.setScene(new Scene(rootNode, 500, 375));
        rootNode.getChildren().add(telnet_Output);

        //Menu Bar addition
        MenuBar menuBar = new MenuBar();

        //Menu Bar "File" tab creation
        Menu fileMenu = new Menu("File");
        MenuItem import_lua = new MenuItem("Import LUA Configuration");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(import_lua, new SeparatorMenuItem(), exit);     // Separator - increases readability
        menuBar.getMenus().add(fileMenu);   // Inject fileMenu into MenuBar

        //Menu Bar "Options" tab creation
        Menu optionsMenu = new Menu("Options");
        MenuItem option_setup_egate = new MenuItem("Setup EGate connections");
        MenuItem option_close_egate = new MenuItem("Close egate and edaemon");
        MenuItem option_start_KPI = new MenuItem("Start KPI listeners");
        optionsMenu.getItems().addAll(option_setup_egate, option_close_egate, option_start_KPI);
        menuBar.getMenus().add(optionsMenu);   // Inject optionsMenu into MenuBar


        javafx.event.EventHandler<ActionEvent> MenuEventHandler = new javafx.event.EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                String chosenOption = ((MenuItem) ae.getTarget()).getText();
                if (chosenOption.equals("Exit")) {
                    Platform.exit();
                } else if (chosenOption.equals("Setup Egate connections")) {
                    String[] commands = {"/bin/bash", "-c", "sudo ./edaemon --port 10000 && sudo ./edaemon --port 30000 && sleep 1"};
                    String[] command_2 = {"/bin/bash", "-c", "sudo ./edaemon --port 30000"};
                    String[] command_3 = {"/bin/bash", "-c", "sudo sleep 1"};
                    Process p, p2;
                    try {
                        p = Runtime.getRuntime().exec(commands);
                        p2 = Runtime.getRuntime().exec(command_2);
                        p = Runtime.getRuntime().exec(command_3);
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p2.getInputStream()));
                        p.waitFor();
                        p2.waitFor();
                        String line = null;
                        System.out.println("Executing command : ");
                        while ((line = stdInput.readLine()) != null) {
                            System.out.println(line);
                        }
                        p.destroy();
                        p2.destroy();
                    } catch (Exception e) {
                        System.out.println("Badziewie IO Exception " + e);
                    }
                } else {
                    System.out.printf("Dummy endpoint\n");
                }
            }
        };

        // Setup listeners action properly for each option
        import_lua.setOnAction(MenuEventHandler);
        exit.setOnAction(MenuEventHandler);
        option_setup_egate.setOnAction(MenuEventHandler);
        option_close_egate.setOnAction(MenuEventHandler);
        option_start_KPI.setOnAction(MenuEventHandler);

        rootNode.setTop(menuBar);

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

