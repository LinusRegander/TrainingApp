import com.codename1.io.ConnectionRequest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static com.codename1.ui.CN.*;

public class Server extends Thread {

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
        private ObjectOutputStream oos;
        private ObjectInputStream ois;

        public ClientHandler(Socket socket){
            this.socket = socket;
            try{
                oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                oos.flush();
                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            } catch (Exception e){
                e.printStackTrace();
            }

            start();
        }

        @Override
        public void run() {
            System.out.println("Connected blablabla");
<<<<<<< Updated upstream
=======
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
>>>>>>> Stashed changes
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
