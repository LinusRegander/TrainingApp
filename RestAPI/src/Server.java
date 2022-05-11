import HelperClasses.*;
import com.codename1.io.ConnectionRequest;
import dbcon.Services;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;

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
                   case 3 -> getLogWorkoutList();

                   case 5 -> insertNewWorkoutInfo();
                   case 6 -> insertLogWorkout();
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
            strings = temp.split("\0");

            String email = strings[0];
            String password = strings[1];

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

        private void insertWorkoutIntoProgram() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            strings = temp.split("\0");

            String loggedInMail = strings[0];
            int programInfoId = Integer.parseInt(strings[1]);
            int workoutId = Integer.parseInt(strings[2]);

            String response = services.insertWorkoutInToProgram(loggedInMail, programInfoId, workoutId);
            dos.writeUTF(response);
            dos.flush();
        }

        private void insertLogExerciseSet() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            strings = temp.split("\0");

            String loggedInMail = strings[0];
            int exerciseId = Integer.parseInt(strings[1]);
            int set = Integer.parseInt(strings[2]);
            int reps = Integer.parseInt(strings[3]);
            double weight = Double.parseDouble(strings[4]);
            int logWorkoutId = Integer.parseInt(strings[5]);

            services.insertLogExerciseSet(exerciseId, set, reps, weight, loggedInMail, logWorkoutId);
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

        private void insertLogProgram() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            strings = temp.split("\0");

            String loggedInMail = strings[0];
            int programId = Integer.parseInt(strings[1]);
            Date date = Date.valueOf(strings[3]);
            String evaluation = strings[4];

            services.insertNewLogProgram(loggedInMail, programId, date, evaluation);
        }

        // TODO: 2022-05-08 Uppdatera indexen för att matcha outputen från controller
        private void insertPlanWorkout() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            strings = temp.split("\0");

            int workoutId = Integer.parseInt(strings[0]);
            String email = strings[1];
            Date date = Date.valueOf(strings[2]);

            services.insertNewPLanWorkout(email, workoutId, date);
        }

        // TODO: 2022-05-08 Uppdatera indexen för att matcha output från controller.
        private void insertPlanExerciseSet() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            strings = temp.split("\0");

            int planExerciseId = Integer.parseInt(strings[0]);
            int exerciseId = Integer.parseInt(strings[1]);
            int set = Integer.parseInt(strings[2]);
            int reps = Integer.parseInt(strings[3]);
            double weight = Double.parseDouble(strings[4]);
            int planWorkoutId = Integer.parseInt(strings[5]);

            services.insertPlanExerciseSetIntoPlanWorkout(planWorkoutId, exerciseId, set, reps, weight);

        }

        private void insertProgramInfo() throws Exception{
            String[] strings;
            String temp = dis.readUTF();
            strings = temp.split("\0");

            String name = strings[0];
            String creatorEmail = strings[1];
            String description = strings[2];
            String tag1 = strings[3];
            String tag2 = strings[4];
            String tag3 = strings[5];

            services.insertNewProgram(name, creatorEmail, description, tag1, tag2, tag3);
        }

        // TODO: 2022-05-08 Kolla upp om sista "\0" ska vara med eller inte, funderar på om man ska ha en vanlig
        //  for loop så att på sista index inte inkludera "\0".
        private void getExerciseList() throws Exception{
            ArrayList<ExerciseInfo> exerciseInfoList = services.selectExercises();

            StringBuilder temp = new StringBuilder();
            for(ExerciseInfo exercise : exerciseInfoList){
                temp.append(exercise.getId() + "\0" + exercise.getName() + "\0" + exercise.getDescription() + "\0"
                        + exercise.getPrimary() + "\0" + exercise.getSecondary() + "\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getWorkoutList() throws Exception{
            ArrayList<WorkoutInfo> workoutInfoList = services.selectWorkoutInfo();
            StringBuilder temp = new StringBuilder();

            for (WorkoutInfo workoutInfo : workoutInfoList){
                temp.append(workoutInfo.getId() + "\0" + workoutInfo.getName() + "\0" + workoutInfo.getCreatorEmail()
                        + "\0" + workoutInfo.getDescription() + "\0" + workoutInfo.getTag1() + "\0" + workoutInfo.getTag2()
                        + "\0" + workoutInfo.getTag3() + "\0" + workoutInfo.getCreatorUsername() + "\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getProgramList() throws Exception{
            ArrayList<ProgramInfo> programInfoList = services.selectProgramInfo();
            StringBuilder temp = new StringBuilder();

            for (ProgramInfo programInfo : programInfoList){
                temp.append(programInfo.getId() + "\0" + programInfo.getName() + "\0" + programInfo.getCreatorEmail()
                        + "\0" + programInfo.getDescription() + "\0" + programInfo.getTag1() + "\0" + programInfo.getTag2()
                        + "\0" + programInfo.getTag3() + "\0" + programInfo.getCreatorUsername() + "\0");
            }
            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getLogExerciseSetList() throws Exception{
            String loggedInMail = dis.readUTF();
            ArrayList<LogExerciseSet> logExerciseSetList = services.selectLogExerciseSet(loggedInMail);
            StringBuilder temp = new StringBuilder();

            for(LogExerciseSet logExerciseSet : logExerciseSetList){
                temp.append(logExerciseSet.getLogExerciseId() + "\0" + logExerciseSet.getExerciseId() + "\0" +
                        logExerciseSet.getLogWorkoutId() + "\0" + logExerciseSet.getEmail() + "\0" +
                        logExerciseSet.getSet() + "\0" + logExerciseSet.getReps() + "\0" + logExerciseSet.getWeight() + "\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getLogWorkoutList() throws Exception{
            String loggedInMail = dis.readUTF();
            ArrayList<LogWorkout> logWorkoutList = services.selectLogWorkout(loggedInMail);
            StringBuilder temp = new StringBuilder();

            for (LogWorkout logWorkout : logWorkoutList){
                temp.append(logWorkout.getLogWorkoutId() + "\0" + logWorkout.getWorkoutId() + "\0" +
                        logWorkout.getCreator() + "\0" + logWorkout.getDate() + "\0" + logWorkout.getEvaluation());
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getLogProgramList() throws Exception{
            String loggedInMail = dis.readUTF();
            ArrayList<LogProgram> logProgramList = services.selectLogProgram(loggedInMail);
            StringBuilder temp = new StringBuilder();

            for (LogProgram logProgram : logProgramList){
                temp.append(logProgram.getLogProgramId() + "\0" + logProgram.getEmail() + "\0" + logProgram.getProgramId()
                        + "\0" + logProgram.getDate() + "\0" + logProgram.getEvaluation() + "\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
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
