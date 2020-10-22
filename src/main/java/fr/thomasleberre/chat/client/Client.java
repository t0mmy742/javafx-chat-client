package fr.thomasleberre.chat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client extends Application {
    private Socket socket;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/fxml/root.fxml"));

        Scene scene = new Scene(root, 1280, 720);

        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        startClient();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (socket != null) {
            OutputStream out = socket.getOutputStream();
            out.write(4);
            out.flush();
            socket.close();
        }
    }

    public static void main(String[] args) {
        System.out.println("Started");
        launch(args);
    }

    private void startClient() {
        try {
            socket = new Socket("127.0.0.1", 45678);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
