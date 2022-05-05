package com.example.trainingapp.Controller;

import HelperClasses.*;
import com.codename1.db.Database;
import com.codename1.io.BufferedOutputStream;
import com.codename1.io.ConnectionRequest;
import com.codename1.ui.Form;
import com.example.trainingapp.Model.User;
import com.example.trainingapp.View.*;
import dbcon.Services;
import jdk.tools.jmod.Main;
import com.codename1.io.Socket;
import com.codename1.io.SocketConnection;

import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.io.*;
import java.security.Provider;
import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;


public class Controller {
    private ArrayList<ExerciseInfo> exerciseList = new ArrayList<>();
    private ArrayList<WorkoutInfo> workoutList = new ArrayList<>();
    private ArrayList<ProgramInfo> programList = new ArrayList<>();
    private ArrayList<LogExerciseSet> logExerciseSetList = new ArrayList<>();
    private ArrayList<LogWorkout> logWorkoutList = new ArrayList<>();
    private ArrayList<LogProgram> logProgramList = new ArrayList<>();
    private AchievementFrame achievementFrame;
    private CreateFrame createFrame;
    private Database database;
    private LoginFrame loginFrame;
    private MainFrame mainFrame;
    private ProgramFrame programFrame;
    private ProfileFrame profileFrame;
    private RegisterFrame registerFrame;
    private Services services;
    private SettingsFrame settingsFrame;
    private String loggedInEmail;
    private User user;
    private UserManager userManager;

    public Controller() {
        Setup();
    }

    //Setup constructor.
    public void Setup() {
        services = new Services(); //Creates a new Database object, containing the Services class.
        mainFrame = new MainFrame(this); //MainFrame is the main GUI frame.
       loginFrame = new LoginFrame(this);
        user = new User();
        userManager = new UserManager(user);
    }

    public void connect(SocketConnection socketConnection){
        System.out.println("inne");
        Socket.connect("192.168.56.1", 541, socketConnection);

    }


    //todo: Will be replaced:
    public void loginVerification() {
        boolean login = false;

        do {
            if (userManager.existingUser(loginFrame.getFieldContent())) {
                user = userManager.getCurrUser();
                login = true;
                openMainFrame();
                System.out.println("Login complete");
            } else {
                System.err.println("Login failed");
            }
        } while (!login);
    }

    //todo: Will be replaced:
    public void registration() {
        boolean login = false;

        try {
            do {
                user = new User();
                user.setUserName(registerFrame.getUserName());
                user.setPassword(registerFrame.getPassword());
                user.setEmail(registerFrame.getEmail());
                if (userManager.addUser(user)) {
                    userManager.setCurrUser(user);
                    login = true;
                }
            } while (!login);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    System.out.println("connection!");
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));

                    String temp = username + "\0" + email + "\0" + password;
                    dos.writeInt(1);
                    dos.writeUTF(temp);
                    dos.flush();
                    /*
                    dos.writeUTF(email);
                    dos.writeUTF(username);
                    dos.writeUTF(password);
                    dos.flush();

                     */
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
        return false;
    }

    public void login(String email, String password){
        SocketConnection sc = new SocketConnection() {
            @Override
            public void connectionError(int i, String s) {

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream) {
                try {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    dos.writeUTF(email);
                    dos.writeUTF(password);
                    dos.flush();
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
                    dos.writeUTF(name);
                    dos.writeUTF(creatorEmail);
                    dos.writeUTF(description);
                    dos.writeUTF(tag1);
                    dos.writeUTF(tag2);
                    dos.writeUTF(tag3);
                    for(ExerciseInfo exerciseInfo : exerciseInfos){
                        dos.writeUTF(String.valueOf(exerciseInfo.getId()));
                        dos.writeUTF(exerciseInfo.getName());
                        dos.writeUTF(exerciseInfo.getDescription());
                        dos.writeUTF(exerciseInfo.getPrimary());
                        dos.writeUTF(exerciseInfo.getSecondary());
                    }
                    dos.flush();
                } catch (IOException e) {
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
        try{
            exerciseList = services.selectExercises();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateWorkoutList(){
        try{
            workoutList = services.selectWorkoutInfo();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateProgramList(){
        try{
            programList = services.selectProgramInfo();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void updateLogExerciseSetList(){
        if(!loggedInEmail.isEmpty()){
            try {
                logExerciseSetList = services.selectExerciseSet(loggedInEmail);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void updateLogWorkoutList(){
        if(!loggedInEmail.isEmpty()){
            try {
                logWorkoutList = services.selectLogWorkout(loggedInEmail);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void updateLogProgramList(){
        if(!loggedInEmail.isEmpty()){
            try{
                logProgramList = services.selectLogProgram(loggedInEmail);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    
}
