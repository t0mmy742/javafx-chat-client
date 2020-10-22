package fr.thomasleberre.chat.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML
    private TextField message;

    @FXML
    private Button sendButton;

    private Socket socket;

    private static final byte ETX = 3;
    private static final byte EOT = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket("127.0.0.1", 45678);
        } catch (IOException e) {
            System.out.println("Error while connecting to server socket");
        }

        new Thread(new Reading(socket)).start();

        sendButton.setOnAction(event -> {
            try {
                if (message.getText().equals("")) {
                    OutputStream out = socket.getOutputStream();
                    out.write(EOT);
                    out.flush();
                } else {
                    OutputStream out = socket.getOutputStream();
                    out.write(message.getText().getBytes());
                    out.write(ETX);
                    out.flush();
                }
            } catch (IOException e) {
                System.out.println("Error while writing to server socket");
            }
        });
    }
}
