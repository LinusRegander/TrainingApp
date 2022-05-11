package com.example.trainingapp.Controller;

import HelperClasses.*;
import com.codename1.db.Database;
import com.codename1.io.BufferedOutputStream;
import com.codename1.io.BufferedInputStream;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.example.trainingapp.View.*;
import dbcon.Services;
import com.codename1.io.Socket;
import com.codename1.io.SocketConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Controller {
    private ArrayList<ExerciseInfo> exerciseList = new ArrayList<>();
    private ArrayList<WorkoutInfo> workoutList = new ArrayList<>();
    private ArrayList<ProgramInfo> programList = new ArrayList<>();
    private ArrayList<LogExerciseSet> logExerciseSetList = new ArrayList<>();
    private ArrayList<PlanExerciseSet> planExerciseSetList = new ArrayList<>();
    private ArrayList<LogWorkout> logWorkoutList = new ArrayList<>();
    private ArrayList<PlanWorkout> planWorkoutList = new ArrayList<>();
    private ArrayList<LogProgram> logProgramList = new ArrayList<>();
    private AchievementFrame achievementFrame;
    private CreateFrame createFrame;
    private Database database;
    private LoginFrame loginFrame;
    private MainFrame mainFrame;
    private ProgramFrame programFrame;
    private ProfileFrame profileFrame;
    private WorkoutLogFrame workoutLogFrame;
    private RegisterFrame registerFrame;
    private Services services;
    private SettingsFrame settingsFrame;
    private String loggedInEmail;
    private String username;

    public Controller() {
        Setup();
    }

    //Setup constructor.
    public void Setup() {
        updateLogWorkoutList();
        services = new Services(); //Creates a new Database object, containing the Services class.
        mainFrame = new MainFrame(this); //MainFrame is the main GUI frame.
       //loginFrame = new LoginFrame(this);
    }

    public void connect(SocketConnection socketConnection){
        System.out.println("inne");
        Socket.connect("127.0.0.1", 541, socketConnection);

    }

    //The code below creates all of the GUI frames part of the application:
    public void openLoginFrame() {
        loginFrame = new LoginFrame(this);
    }

    public void openMainFrame() {
        mainFrame = new MainFrame(this);
    }

    public void openRegFrame() {
        registerFrame = new RegisterFrame(this);
    }

    public void openAchievementFrame() {
        achievementFrame = new AchievementFrame(this);
    }

    public void openCreateFrame() {
        createFrame = new CreateFrame(this);
    }

    public void openProgramFrame() {
        programFrame = new ProgramFrame(this);
    }

    public void openSettingsFrame() {
        settingsFrame = new SettingsFrame(this);
    }

    public void openProfileFrame() {
        profileFrame = new ProfileFrame(this);
    }

    public void openWorkoutLogFrame(){
        workoutLogFrame = new WorkoutLogFrame(this);
    }

    public Form getLoginForm() {
        return loginFrame.getLoginForm();
    }

    public Form getMainForm() {
        return mainFrame.getMainForm();
    }

    //todo: Everything below will be used later:
    public boolean register(String username, String email, String password) {
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {

                try {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));

                    String temp = username + "\0" + email + "\0" + password;
                    dos.writeInt(1);
                    dos.writeUTF(temp);
                    dos.flush();

                    int reply = dis.readInt();
                    switch(reply){
                        case 0:
                            System.out.println("det gick");
                            openLoginFrame();
                        case 1:
                            System.out.println("email");
                            registerFrame.geteLabel().setText("Email is already in use");
                        case 2:
                            System.out.println("användarnamn");
                        case 3:
                            System.out.println("båda");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
        return false;
    }

    //write int talar om för servern vilken metod det är som ska anropas
    //writeUTF är själva datan som ska skickas till servern och det är server sidan som hanterar det. 
    public void login(String email, String password){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                System.out.println("hej");
                try {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));

                    String temp = email + "\0" + password;
                    dos.writeInt(0);
                    dos.writeUTF(temp);
                    dos.flush();

                    String response = dis.readUTF();
                    System.out.println(response);
                    if(!response.isEmpty()){
                        String[] loggedIn = split(response);
                        loggedInEmail = loggedIn[0];
                        username = loggedIn[1];
                        // TODO: 2022-05-06 BYT PANEL TILL NÄR MAN ÄR INLOGGAD
                        openMainFrame();
                        System.out.println("du är inloggad!");
                    } else {
                        // TODO: 2022-05-06 Error meddelande som säger login failed
                        loginFrame.failedLogin();
                        System.out.println("du kom inte in!");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        connect(sc);
    }

    public void addWorkoutInfo(String name, String creatorEmail, String description, String tag1, String tag2, String tag3, ArrayList<ExerciseInfo> exerciseInfos){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));

                    String temp = name + "\0" + creatorEmail + "\0" + description + "\0" + tag1 + "\0" + tag2 + "\0" + tag3;

                    for(ExerciseInfo exerciseInfo : exerciseInfos){
                        temp += "\0" + exerciseInfo.getId();
                        temp += "\0" + exerciseInfo.getName();
                        temp += "\0" + exerciseInfo.getDescription();
                        temp += "\0" + exerciseInfo.getPrimary();
                        temp += "\0" + exerciseInfo.getSecondary();
                    }
                    dos.writeInt(5);
                    dos.writeUTF(temp);
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }

    public void addLogWorkout(String email, int workoutId, java.util.Date date, String evaluation){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));

                    String temp = email + "\0" + workoutId + "\0" + date + "\0" + evaluation;
                    dos.writeInt(6);
                    dos.writeUTF(temp);
                    dos.flush();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        };
        connect(sc);
    }

    // TODO: 2022-05-08 PlanWorkoutId finns inte förens efter den har insertats till databasen
    public void addPlanWorkout(int planWorkoutId, int workoutId, String creator, Date date){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {

                try {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));

                    String temp = planWorkoutId + "\0" + workoutId + "\0" + creator + "\0" + date;
                    dos.writeInt(22);
                    dos.writeUTF(temp);
                    dos.flush();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        };
        connect(sc);
    }

    // TODO: 2022-05-08 planExerciseId finns inte förens första har lagts till i databasen.
    public void addPlanExerciseSet(int planExerciseId, int exerciseId, int set, int reps, double weight, String email, int planWorkoutId){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));

                    String temp = planExerciseId + "\0" + exerciseId + "\0" + set + "\0" + reps + "\0" + weight + "\0" + email + "\0" + planWorkoutId;
                    dos.writeInt(69);
                    dos.writeUTF(temp);
                    dos.flush();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        };
        connect(sc);
    }

    public void addProgramInfo(String name, String creatorEmail, String description, String tag1, String tag2, String tag3, ArrayList<WorkoutInfo> workoutInfos){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {

            }
        };
    }

    public void logout(){
        loggedInEmail = "";
        logExerciseSetList = new ArrayList<>();
        logWorkoutList = new ArrayList<>();
        logProgramList = new ArrayList<>();
    }

    //todo: refresh knapp på gui som updaterar specifika listor.
    public void updateExerciseList(){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(4);
                    System.out.println("hej");
                    dos.flush();
                    ArrayList<ExerciseInfo> arrayTemp = new ArrayList<>();
                    String[] strings;
                    String temp = dis.readUTF();
                    System.out.println(temp);
                    strings = split(temp);
                    for(int i = 0; i < strings.length / 5; i++){
                        int id = Integer.parseInt(strings[i * 5]);
                        String name = strings[i * 5 + 1];
                        String description = strings[i * 5 + 2];
                        String primary = strings[i * 5 + 3];
                        String secondary = strings[i * 5 + 4];

                        arrayTemp.add(new ExerciseInfo(id, name, description, primary, secondary));
                    }
                    exerciseList = arrayTemp;
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }

    public void updateWorkoutList(){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(8);
                    dos.flush();
                    ArrayList<WorkoutInfo> arrayTemp = new ArrayList<>();
                    String[] strings;
                    String temp = dis.readUTF();
                    strings = split(temp);
                    for(int i = 0; i < strings.length / 8; i++){
                        int id = Integer.parseInt(strings[i * 8]);
                        String name = strings[i * 8 + 1];
                        String creatorEmail = strings[i * 8 + 2];
                        String description = strings[i * 8 + 3];
                        String tag1 = strings[i * 8 + 4];
                        String tag2 = strings[i * 8 + 5];
                        String tag3 = strings[i * 8 + 6];
                        String creatorUsername = strings[i * 8 + 7];

                        arrayTemp.add(new WorkoutInfo(id, name, creatorEmail, description, tag1, tag2, tag3, creatorUsername));
                    }
                    workoutList = arrayTemp;
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }

    public void updateProgramList(){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(9);
                    dos.flush();
                    ArrayList<ProgramInfo> arrayTemp = new ArrayList<>();
                    String[] strings;
                    String temp = dis.readUTF();
                    strings = split(temp);
                    for(int i = 0; i < strings.length / 8; i++){
                        int id = Integer.parseInt(strings[i * 8]);
                        String name = strings[i * 8 + 1];
                        String creatorEmail = strings[i * 8 + 2];
                        String description = strings[i * 8 + 3];
                        String tag1 = strings[i * 8 + 4];
                        String tag2 = strings[i * 8 + 5];
                        String tag3 = strings[i * 8 + 6];
                        String creatorUsername = strings[i * 8 + 7];

                        arrayTemp.add(new ProgramInfo(id, name, creatorEmail, description, tag1, tag2, tag3, creatorUsername));
                    }
                    programList = arrayTemp;
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }
    public void updateLogExerciseSetList(){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(10);
                    dos.flush();
                    ArrayList<LogExerciseSet> arrayTemp = new ArrayList<>();
                    String[] strings;
                    String temp = dis.readUTF();
                    strings = split(temp);
                    for(int i = 0; i < strings.length / 7; i++){
                        int logExerciseid = Integer.parseInt(strings[i * 7]);
                        int exerciseId = Integer.parseInt(strings[i * 7 + 1]);
                        int set = Integer.parseInt(strings[i * 7 + 2]);
                        int reps = Integer.parseInt(strings[i * 7 + 3]);
                        double weight = Double.parseDouble(strings[i * 7 + 4]);
                        String email = strings[i * 7 + 5];
                        int logWorkoutId = Integer.parseInt(strings[i * 7 + 6]);

                        arrayTemp.add(new LogExerciseSet(logExerciseid, exerciseId, set, reps, weight, email, logWorkoutId));
                    }
                    logExerciseSetList = arrayTemp;
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }
    public void updateLogWorkoutList(){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(3);
                    dos.writeUTF("daniel.olsson@gmail.com");
                    dos.flush();
                    ArrayList<LogWorkout> arrayTemp = new ArrayList<>();
                    String[] strings;
                    String temp = dis.readUTF();
                    strings = split(temp);
                    for(int i = 0; i < strings.length / 5; i++){
                        int logWorkoutId = Integer.parseInt(strings[i * 5]);
                        int workoutId = Integer.parseInt(strings[i * 5 + 1]);
                        String creatorEmail = strings[i * 5 + 2];
                        java.util.Date date = new SimpleDateFormat("yyyy-mm-dd").parse(strings[i * 5 + 3]);
                        String evaluation = strings[i * 5 + 4];

                        arrayTemp.add(new LogWorkout(logWorkoutId, workoutId, creatorEmail, date, evaluation));
                    }
                    logWorkoutList = arrayTemp;
                    System.out.println(arrayTemp);
                    System.out.println(logWorkoutList);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }
    public void updateLogProgramList(){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(12);
                    dos.flush();
                    ArrayList<LogProgram> arrayTemp = new ArrayList<>();
                    String[] strings;
                    String temp = dis.readUTF();
                    strings = split(temp);
                    for(int i = 0; i < strings.length / 5; i++){
                        int logProgramId = Integer.parseInt(strings[i * 5]);
                        String email = strings[i * 5 + 1];
                        int programId = Integer.parseInt(strings[i * 5 + 2]);
                        Date date = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(strings[i * 5 + 3]);
                        String evaluation = strings[i * 5 + 4];

                        arrayTemp.add(new LogProgram(logProgramId, email, programId, date, evaluation));
                    }
                    logProgramList = arrayTemp;
                } catch (IOException | ParseException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }

    public String[] split(String str)
    {
        ArrayList<String> splitArray = new ArrayList<>();
        StringTokenizer arr = new StringTokenizer(str, "\0");//split by commas
        while(arr.hasMoreTokens())
            splitArray.add(arr.nextToken());
        return splitArray.toArray(new String[splitArray.size()]);
    }
    public ArrayList<ExerciseInfo> getExerciseList(){
        return exerciseList;
    }

    public void updatePlanExercise(){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(8);
                    dos.flush();
                    ArrayList<WorkoutInfo> arrayTemp = new ArrayList<>();
                    String[] strings;
                    String temp = dis.readUTF();
                    strings = temp.split("\0");
                    for(int i = 0; i < strings.length / 8; i++){
                        int id = Integer.parseInt(strings[i * 8]);
                        String name = strings[i * 8 + 1];
                        String creatorEmail = strings[i * 8 + 2];
                        String description = strings[i * 8 + 3];
                        String tag1 = strings[i * 8 + 4];
                        String tag2 = strings[i * 8 + 5];
                        String tag3 = strings[i * 8 + 6];
                        String creatorUsername = strings[i * 8 + 7];

                        arrayTemp.add(new WorkoutInfo(id, name, creatorEmail, description, tag1, tag2, tag3, creatorUsername));
                    }
                    workoutList = arrayTemp;
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }

    public void addExercise(int workoutId, int exerciseId, int sets, int reps, double weight){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));

                    String temp = workoutId + "\0" + exerciseId + "\0" + sets + "\0" + reps + "\0" + weight + "\0";
                    dos.writeInt(13);
                    dos.writeUTF(temp);
                    dos.flush();
                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        };
        connect(sc);
    }

    public String getLoggedInEmail() {
        return loggedInEmail;
    }

    public ArrayList<LogWorkout> getLogWorkoutList() {
        return logWorkoutList;
    }
}
