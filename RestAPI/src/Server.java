import com.codename1.io.ConnectionRequest;
import dbcon.Services;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
            while (true){
                try{
                    Socket socket = serverSocket.accept();
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

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                dos.flush();
                dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            } catch (Exception e) {
                e.printStackTrace();
            }

            start();
        }

        @Override
        public void run() {
            System.out.println("Connected blablabla");

            int choice;
            try {
                choice = dis.readInt();

               switch (choice){
                   case 0 -> login();
                   case 1 -> register();
               }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        private void register() throws Exception{
            String[] strings;

            String temp = dis.readUTF();
            strings = temp.split("\0");

            String email = strings[0];
            String username = strings[1];
            String password = strings[2];

            if (!services.checkIfEmailExists(email) && !services.checkIfUsernameExists(username)) {
                services.insertNewUser(email, username, password);
            }
        }


        /*private void register() throws Exception{
            String email = dis.readUTF();
            String username = dis.readUTF();
            String password = dis.readUTF();

            if (!services.checkIfEmailExists(email) && !services.checkIfUsernameExists(username)) {
                services.insertNewUser(email, username, password);
            }
        }*/
        private void login() throws Exception{

        }

    }

    public static void main(String[] args) {
        Server server = new Server(541);
    }
}
