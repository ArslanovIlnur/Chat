package org.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 1234;
    private List<ClientHandler> clients = new ArrayList<>();

    public Server() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is run...");

            while (true){
                clientSocket = serverSocket.accept();

                ClientHandler client = new ClientHandler(this, clientSocket);
                clients.add(client);

                new Thread(client).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMsgToAllClients(String msg){
        for (ClientHandler c : clients){
            c.sendMsg(msg);
        }
    }
}
