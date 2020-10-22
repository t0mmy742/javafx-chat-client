package fr.thomasleberre.chat.client.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

public class Reading implements Runnable {
    private final Socket socket;

    private static final byte ETX = 3;
    private static final byte EOT = 4;

    public Reading(Socket socket) {

        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedInputStream reader;

        while (!socket.isClosed()) {
            try {
                reader = new BufferedInputStream(socket.getInputStream());

                int stream;
                StringBuilder message = new StringBuilder();
                byte[] bufferRead;
                while (true) {
                    bufferRead = new byte[4096];
                    stream = reader.read(bufferRead);
                    message.append(new String(bufferRead, 0, stream));
                    if (bufferRead[stream - 1] == ETX) {
                        message.deleteCharAt(message.length() - 1);
                        break;
                    } else if (bufferRead[0] == EOT) {
                        socket.close();
                        break;
                    }
                }
                if (message.toString().equals("\4")) {
                    break;
                }

                System.out.println(message);
            } catch (IOException e) {
                System.out.println("Error while reading message");
                break;
            }
        }
    }
}
