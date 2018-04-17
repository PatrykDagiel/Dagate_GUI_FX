package main.window;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import telnet.sockets.EgateClient;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private EgateClient egate_Client = null;

    @Override
    public void init() throws Exception {
        super.init();
        System.out.printf("Inside init method");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        TextArea telnet_Output = new TextArea();

        BorderPane rootNode = new BorderPane();
        primaryStage.setTitle("Dagate_GUI");
        primaryStage.setScene(new Scene(rootNode, 500, 375));
//        rootNode.getChildren().add(telnet_Output);

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


        EventHandler<ActionEvent> MenuEventHandler = ae -> {
            String chosenOption = ((MenuItem) ae.getTarget()).getText();
            if (chosenOption.equals("Exit")) {
                Platform.exit();
            } else if (chosenOption.equals("Setup EGate connections")) {
                egate_Client = new EgateClient();
                String[][] commands = {{"/bin/bash", "-c", "sudo ./edaemon --port 10000"},
                        {"/bin/bash", "-c", "sudo ./edaemon --port 30000"},
                        {"/bin/bash", "-c", "./egate --port 20000"}};
                Process process_Handler;
                try {
                    for (String[] x : commands) {
                        process_Handler = Runtime.getRuntime().exec(x);
                        TimeUnit.SECONDS.sleep(3);
                        if (process_Handler.isAlive()) {
                            System.out.println("Process is alive");
                        }
                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process_Handler.getInputStream()));
                        String line = null;
                        System.out.println("Executing command : ");
                        while ((line = stdInput.readLine()) != null) {
                            System.out.println(line);
                        }
                        process_Handler.waitFor();
                        System.out.println("Logging process closure");
                        process_Handler.destroy();
                    }
                } catch (Exception e) {
                    System.out.println("Badziewie IO Exception " + e);
                } try {
                    System.out.println("Inside Telnet connection");
                    egate_Client.connect_to_egate();
                    egate_Client.write_message_to_egate("TOMASZ HAJTO TEST 01010101010101");
                    egate_Client.close_connection_to_egate();
                } catch (Exception e) {
                    System.out.println("IO Exception with output Text Area");
                }
            } else {
                System.out.printf("Dummy endpoint\n");
            }
        };

        // Setup listeners action properly for each option
        import_lua.setOnAction(MenuEventHandler);
        exit.setOnAction(MenuEventHandler);
        option_setup_egate.setOnAction(MenuEventHandler);
        option_close_egate.setOnAction(MenuEventHandler);
        option_start_KPI.setOnAction(MenuEventHandler);

        rootNode.setTop(menuBar);
        rootNode.setCenter(telnet_Output);

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

