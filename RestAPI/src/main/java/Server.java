import HelperClasses.*;
import dbcon.Services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;

/**
 @author William Dock, Yun-Bo Chow
 */


public class Server extends Thread{
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
        public void run(){
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

        /**
         * runs when start is called.
         * The selected case is determined by which number that is received from the client.
         */
        @Override
        public void run(){
            System.out.println(socket.getInetAddress().getHostName() + "Connected to server");

            int choice;
            try{
                choice = dis.readInt();
                switch (choice){
                    case 0 -> login();
                    case 1 -> register();
                    case 2 -> insertLogExerciseSet();
                    case 3 -> insertNewWorkoutInfo();
                    case 4 -> insertLogWorkout();
                    case 5 -> insertLogProgram();
                    case 6 -> insertWorkoutIntoProgram();
                    case 7 -> insertExerciseIntoWorkout();
                    case 8 -> getLogWorkoutList();
                    case 9 -> getLogProgramList();
                    case 10 -> getExerciseList();
                    case 11 -> getWorkoutList();
                    case 12 -> getProgramList();
                    case 13 -> getLogExerciseSetList();
                    case 14 -> getAllAchievements();
                    case 15 -> getCompletedAchievements();
                }

                services.terminateIdle();
            } catch (Exception e){
                e.printStackTrace();
            }

        }

        /**
         * services.login() returns "" if login is unsuccessful
         * If you successfully log in, return a String that is the email of the log in attempt
         * @throws Exception
         */
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

        /**
         * checks if email or username is already in the database, gives an error if it exists.
         * @throws Exception
         */
        private void register() throws Exception{
            String[] strings;

            String temp = dis.readUTF();
            strings = temp.split("\0");

            String email = strings[0];
            String username = strings[1];
            String password = strings[2];

            if(services.checkIfEmailExists(email) && services.checkIfUsernameExists(username)){
                dos.writeInt(3);
                dos.flush();
                return;
            }

            if(services.checkIfUsernameExists(username)){
                dos.writeInt(2);
                dos.flush();
                return;
            }

            if (services.checkIfEmailExists(email)){
                dos.writeInt(1);
                dos.flush();
                return;
            }

            services.insertNewUser(email, username, password);
            dos.writeInt(0);
            dos.flush();

        }

        /**
         * Inserts a new workout into the database
         * @throws Exception
         */
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

            for(int i=6; i<strings.length; i+=2){
                services.insertExerciseInToWorkout(Integer.parseInt(strings[i]), id, Integer.parseInt(strings[i+1]));
            }

            dos.writeInt(id);
            dos.flush();
        }

        /**
         * Inserts a new exercise into a workout to the database
         * @throws Exception
         */
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

        /**
         * Inserts a workout into program
         * @throws Exception
         */
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

        /**
         * Inserts a LogExerciseSet to the database
         * @throws Exception
         */
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

        /**
         * Inserts a logWorkout to the database
         * @throws Exception
         */
        private void insertLogWorkout() throws Exception{
            String temp = dis.readUTF();
            String[] strings = temp.split("\0");

            String email = strings[0];
            int workoutId = Integer.parseInt(strings[1]);
            Date date = Date.valueOf(strings[2]);
            String evaluation = strings[3];

            int id = services.insertNewLogworkout(email, workoutId, date, evaluation);

            for(int i = 4; i<strings.length; i += 4){
                services.insertLogExerciseSet(Integer.parseInt(strings[i]), Integer.parseInt(strings[i+1]), Integer.parseInt(strings[i+2]), Double.parseDouble(strings[i+3]), email, id);
            }

            dos.writeInt(id);
            dos.flush();
        }

        /**
         * Inserts a logProgram to the database
         * @throws Exception
         */
        private void insertLogProgram() throws Exception{
            String temp = dis.readUTF();
            String[] strings = temp.split("\0");

            String loggedInMail = strings[0];
            int programId = Integer.parseInt(strings[1]);
            Date date = Date.valueOf(strings[3]);
            String evaluation = strings[4];

            services.insertNewLogProgram(loggedInMail, programId, date, evaluation);
        }

        private void insertPlanWorkout() throws Exception{
            String temp = dis.readUTF();
            String[] strings = temp.split("\0");

            int workoutId = Integer.parseInt(strings[0]);
            String email = strings[1];
            Date date = Date.valueOf(strings[2]);

            services.insertNewPLanWorkout(email, workoutId, date);
        }

        private void insertPlanExerciseSet() throws Exception{
            String temp = dis.readUTF();
            String[] strings = temp.split("\0");

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

        private void getExerciseList() throws Exception{
            ArrayList<ExerciseInfo> exerciseInfoList = services.selectExercises();
            StringBuilder temp = new StringBuilder();

            for(ExerciseInfo exercise : exerciseInfoList){
                temp.append(exercise.getId()).append("\0")
                        .append(exercise.getName()).append("\0")
                        .append(exercise.getDescription()).append("\0")
                        .append(exercise.getPrimary()).append("\0")
                        .append(exercise.getSecondary()).append("\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getWorkoutList() throws Exception{
            ArrayList<WorkoutInfo> workoutInfoList = services.selectWorkoutInfo();
            StringBuilder temp = new StringBuilder();

            for (WorkoutInfo workoutInfo : workoutInfoList){
                temp.append(workoutInfo.getId()).append("\0")
                        .append(workoutInfo.getName()).append("\0")
                        .append(workoutInfo.getCreatorEmail()).append("\0")
                        .append(workoutInfo.getDescription()).append("\0")
                        .append(workoutInfo.getTag1()).append("\0")
                        .append(workoutInfo.getTag2()).append("\0")
                        .append(workoutInfo.getTag3()).append("\0")
                        .append(workoutInfo.getCreatorUsername()).append("\0")
                        .append(workoutInfo.getAmountOfExercise()).append("\0");
                ArrayList<ExerciseInfo> exercises = workoutInfo.getExerciseInfos();
                for (ExerciseInfo exerciseInfo : exercises){
                    temp.append(exerciseInfo.getId()).append("\0")
                            .append(exerciseInfo.getName()).append("\0")
                            .append(exerciseInfo.getDescription()).append("\0")
                            .append(exerciseInfo.getPrimary()).append("\0")
                            .append(exerciseInfo.getSecondary()).append("\0")
                            .append(exerciseInfo.getSetCount()).append("\0");
                }
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getProgramList() throws Exception{
            ArrayList<ProgramInfo> programInfoList = services.selectProgramInfo();
            StringBuilder temp = new StringBuilder();

            for (ProgramInfo programInfo : programInfoList){
                temp.append(programInfo.getId()).append("\0")
                        .append(programInfo.getName()).append("\0")
                        .append(programInfo.getCreatorEmail()).append("\0")
                        .append(programInfo.getDescription()).append("\0")
                        .append(programInfo.getTag1()).append("\0")
                        .append(programInfo.getTag2()).append("\0")
                        .append(programInfo.getTag3()).append("\0")
                        .append(programInfo.getCreatorUsername()).append("\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getLogExerciseSetList() throws Exception{
            String loggedInMail = dis.readUTF();
            ArrayList<LogExerciseSet> logExerciseSetList = services.selectLogExerciseSet(loggedInMail);
            StringBuilder temp = new StringBuilder();

            for(LogExerciseSet logExerciseSet : logExerciseSetList){
                temp.append(logExerciseSet.getLogExerciseId()).append("\0")
                        .append(logExerciseSet.getExerciseId()).append("\0")
                        .append(logExerciseSet.getLogWorkoutId()).append("\0")
                        .append(logExerciseSet.getEmail()).append("\0")
                        .append(logExerciseSet.getSet()).append("\0")
                        .append(logExerciseSet.getReps()).append("\0")
                        .append(logExerciseSet.getWeight()).append("\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getLogWorkoutList() throws Exception{
            String loggedInMail = dis.readUTF();
            ArrayList<LogWorkout> logWorkoutList = services.selectLogWorkout(loggedInMail);
            StringBuilder temp = new StringBuilder();

            for (LogWorkout logWorkout : logWorkoutList){
                temp.append(logWorkout.getLogWorkoutId()).append("\0")
                        .append(logWorkout.getWorkoutId()).append("\0")
                        .append(logWorkout.getCreator()).append("\0")
                        .append(logWorkout.getDate()).append("\0")
                        .append(logWorkout.getEvaluation()).append("\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getLogProgramList() throws Exception{
            String loggedInMail = dis.readUTF();
            ArrayList<LogProgram> logProgramList = services.selectLogProgram(loggedInMail);
            StringBuilder temp = new StringBuilder();

            for (LogProgram logProgram : logProgramList){
                temp.append(logProgram.getLogProgramId()).append("\0")
                        .append(logProgram.getEmail()).append("\0")
                        .append(logProgram.getProgramId()).append("\0")
                        .append(logProgram.getDate()).append("\0")
                        .append(logProgram.getEvaluation()).append("\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void getAllAchievements() throws Exception{
            ArrayList<AchievementsInfo> achievementsInfoList = services.selectAllAchievements();
            StringBuilder temp = new StringBuilder();

            for (AchievementsInfo achievementsInfo : achievementsInfoList){
                temp.append(achievementsInfo.getAchievementId()).append("\0")
                        .append(achievementsInfo.getName()).append("\0")
                        .append(achievementsInfo.getDescription()).append("\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        public void getCompletedAchievements() throws Exception{
            String loggedInMail = dis.readUTF();
            ArrayList<CompletedAchievement> completedAchievementsList = services.selectCompletedAchievements(loggedInMail);
            StringBuilder temp = new StringBuilder();

            for (CompletedAchievement completedAchievement : completedAchievementsList){
                temp.append(completedAchievement.getAchievementId()).append("\0")
                        .append(completedAchievement.getEmail()).append("\0")
                        .append(completedAchievement.getDate()).append("\0");
            }

            dos.writeUTF(temp.toString());
            dos.flush();
        }

        private void insertNewAchievementsInfo() throws Exception{
            String temp = dis.readUTF();
            String[] strings = temp.split("\0");

            String name = strings[0];
            String description = strings[1];

            services.insertNewAchievementsInfo(name, description);
        }

        private void insertCompleteAchievement() throws Exception{
            String temp = dis.readUTF();
            String[] strings = temp.split("\0");

            int achievementId = Integer.parseInt(strings[0]);
            String loggedInMail = strings[1];
            Date date = Date.valueOf(strings[2]);

            services.insertCompleteAchievement(achievementId, loggedInMail, date);
        }

    }

    public static void main(String[] args){
        Server server = new Server(541);
    }
}
