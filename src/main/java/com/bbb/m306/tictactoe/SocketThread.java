package com.bbb.m306.tictactoe;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketThread implements Runnable{

    private ServerSocket serverSocket;

    public SocketThread(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override public void run() {
        while (true) {
            try {
                serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
