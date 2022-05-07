import HelperClasses.WorkoutInfo;
import com.codename1.io.ConnectionRequest;
import dbcon.Services;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;

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
                   case 2 -> insertNewWorkoutInfo();
               }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //services.login() retunerar "" om de inte lyckades logga in
        //Om man lyckas retuneras en String som är emailen på inloggningen
        // TODO: 2022-05-05 Hantera login på klient sidan
        private void login() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            System.out.println(temp);
            strings = temp.split("\0");

            String email = strings[0];
            String password = strings[1];

            System.out.println(email);
            System.out.println(password);

            String response = services.login(email, password);

            dos.writeUTF(response);
            dos.flush();
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
            // TODO: 2022-05-05 Lägg till så att ifall ifsatsen failas så ska den svara till klienten vad som failar.
        }

        private void insertNewWorkoutInfo() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            strings = temp.split("\0");

            String workoutName = strings[0];
            String creatorEmail = strings[1];
            String description = strings[2];
            String tag1 = strings[3];
            String tag2 = strings[4];
            String tag3 = strings[5];

            WorkoutInfo workoutInfo = services.insertNewWorkout(workoutName, creatorEmail, description, tag1, tag2, tag3);
            int id = workoutInfo.getId();

            dos.writeInt(id);
            dos.flush();
        }

        private void insertExerciseIntoWorkout() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            strings = temp.split("\0");

            String loggedInMail = strings[0];
            int exerciseId = Integer.parseInt(strings[1]);
            int workoutId = Integer.parseInt(strings[2]);

            String response = services.insertExerciseInToWorkout(loggedInMail, exerciseId, workoutId);

            dos.writeUTF(response);
            dos.flush();
        }

        private void insertLogWorkout() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            strings = temp.split("\0");

            String email = strings[0];
            int workoutId = Integer.parseInt(strings[1]);
            Date date = Date.valueOf(strings[2]);
            String evaluation = strings[3];

            services.insertNewLogworkout(email, workoutId, date, evaluation);

        }

        private void insertProgramInfo() throws Exception{

        }


        /*private void register() throws Exception{
            String email = dis.readUTF();
            String username = dis.readUTF();
            String password = dis.readUTF();

            if (!services.checkIfEmailExists(email) && !services.checkIfUsernameExists(username)) {
                services.insertNewUser(email, username, password);
            }
        }*/


    }

    public static void main(String[] args) {
        Server server = new Server(541);
    }
}
