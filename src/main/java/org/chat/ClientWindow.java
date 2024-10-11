package org.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWindow extends JFrame {
    private Scanner in;
    private PrintWriter out;
    private Socket clientSocket;
    private JTextField nameField;
    private JTextField msgField;

    public ClientWindow() {
        try {
            this.clientSocket = new Socket("localhost", 1234);
            this.in = new Scanner(clientSocket.getInputStream());
            this.out = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Font font = new Font("Times new Roman", Font.PLAIN, 16);

        JTextArea textArea = new JTextArea();
        JScrollPane pane = new JScrollPane(textArea);

        add(pane, BorderLayout.CENTER);

        nameField = new JTextField(10);
        msgField = new JTextField(35);
        JButton sendBtn = new JButton("Отправить");

        nameField.setFont(font);
        msgField.setFont(font);
        sendBtn.setFont(font);

        JPanel panel = new JPanel();
        panel.add(nameField, BorderLayout.WEST);
        panel.add(msgField, BorderLayout.CENTER);
        panel.add(sendBtn, BorderLayout.EAST);

        add(panel, BorderLayout.SOUTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 800);

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (in.hasNext()){
                        textArea.append(in.nextLine() + "\n");
                    }
                }
            }
        }).start();

        setVisible(true);
    }

    public void sendMsg(){
        String msg = nameField.getText() + ": " + msgField.getText();
        System.out.println(msg);
        out.println(msg);
        out.flush();
        msgField.setText("");
    }
}
