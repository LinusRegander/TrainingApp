package com.example.trainingapp.Model;

import com.codename1.io.Socket;
import com.codename1.io.SocketConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Client {
    private String ip;
    private int port;
    private Socket socket;

    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        connect();
    }

    public void connect() {
        Socket.connect(ip, port, new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                while (isConnected()) {

                }
            }
        });
    }
}
