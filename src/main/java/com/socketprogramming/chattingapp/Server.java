package com.socketprogramming.chattingapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ServerSocket serverSocket;
    static ArrayList<Socket> users = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        serverSocket = new ServerSocket(5000);

        while(true){
            Socket user = serverSocket.accept();
            new ClientHandler(user).start();
            users.add(user);
        }
    }

    public static class ClientHandler extends Thread{
        Socket socket;
        ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            System.out.println("Someone joined");
        }

        @Override
        public void run(){
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter;
                String message;
                while((message = bufferedReader.readLine()) != null){
                    if(message.equalsIgnoreCase("exit")){
                        System.out.println("Client has left.");
                        for(Socket user: users){
                            if(user.equals(socket)){
                                users.remove(socket);
                            }
                        }
                        socket.close();
                    }
                    System.out.println("Client said: " + message);
                    for(Socket client: users){
                        printWriter = new PrintWriter(client.getOutputStream(), true);
                        printWriter.println(message);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }



    }


}
