package com.socketprogramming.chattingapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class MainClientController implements Initializable {

    @FXML
    Button sendButton;
    @FXML
    TextField clientMessage;
    @FXML
    TextArea messagesField;


    private Socket socket;
    private String message;
    PrintWriter printWriter;
    BufferedReader bufferedReader;
    String receivedMessage;

    @FXML
    public void pressedButton() throws IOException {
        message = clientMessage.getText();
        clientMessage.clear();
        sendMessage();
    }

    public void sendMessage() throws IOException {
        printWriter.println(message);
    }

    public void receiveMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                messagesField.setText(messagesField.getText());
                while (true) {
                    try {
                        if ((receivedMessage = bufferedReader.readLine()) != null) {
                            System.out.println(receivedMessage);
                            messagesField.appendText("Client said: " + receivedMessage + "\n");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            socket = new Socket(InetAddress.getLocalHost(), 5000);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receiveMessages();
            messagesField.setWrapText(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}