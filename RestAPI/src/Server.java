import com.codename1.io.ConnectionRequest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import dbcon.Services;

import static com.codename1.ui.CN.*;

public class Server extends Thread {
    private Services services = new Services();

    public Server(int port){
        new Connection(port);
    }

    private class Connection extends Thread{
        private ServerSocket serverSocket;

        public Connection(int port){
            try{
                serverSocket = new ServerSocket(port);
                start();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            System.out.println("den startar!");
            while (true){
                try{
                    System.out.println("vafan!");
                    Socket socket = serverSocket.accept();
                    System.out.println("ny klient");
                    new ClientHandler(socket);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private class ClientHandler extends Thread{
        //User eller email variabel
        private Socket socket;
        private DataOutputStream dos;
        private DataInputStream dis;

        public ClientHandler(Socket socket){
            this.socket = socket;
            try{
                dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dos.flush();
                dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            } catch (Exception e){
                e.printStackTrace();
            }

            start();

        }

        @Override
        public void run() {
            System.out.println("Connected blablabla");
            try {
                String email = dis.readUTF();
                String username = dis.readUTF();
                String password = dis.readUTF();

                if (!services.checkIfEmailExists(email) && !services.checkIfUsernameExists(username)) {
                    services.insertNewUser(email, username, password);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(541);
    }

}

