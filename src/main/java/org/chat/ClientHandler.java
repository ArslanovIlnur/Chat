package org.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private Server server;
    private Socket clientSocket;
    private Scanner in;
    private PrintWriter out;

    public ClientHandler(Server server, Socket clientSocket) {
        try {
            this.server = server;
            this.clientSocket = clientSocket;
            this.in = new Scanner(clientSocket.getInputStream());
            this.out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true){
            server.sendMsgToAllClients("New user is connected...\n");
            break;
        }
        while (true){
            if (in.hasNext()){
                String msg = in.nextLine();
                server.sendMsgToAllClients(msg);
            }
        }
    }

    public void sendMsg(String msg){
        out.println(msg);
        out.flush();
    }
}
