package Controller;

import HelperClasses.*;
import View.*;
import dbcon.Services;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;


public class Controller {
    private MainFrame mainFrame;
    private RegisterFrame registerFrame;
    private LoginFrame loginFrame;
    private ProgramFrame programFrame;
    private SettingsFrame settingsFrame;
    private CreateFrame createFrame;
    private AchievementFrame achievementFrame;
    private UserManager userManager;
    private Services services;
    private String loggedInEmail;
    private ArrayList<ExerciseInfo> exerciseList = new ArrayList<>();
    private ArrayList<WorkoutInfo> workoutList = new ArrayList<>();
    private ArrayList<ProgramInfo> programList = new ArrayList<>();
    private ArrayList<LogExerciseSet> logExerciseSetList = new ArrayList<>();
    private ArrayList<LogWorkout> logWorkoutList = new ArrayList<>();
    private ArrayList<LogProgram> logProgramList = new ArrayList<>();

    public Controller() {
        Setup();
    }

    public void Setup() {
        services = new Services();
    }

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
        createFrame = new CreateFrame(/*this*/);
    }
    public void openProgramFrame() {
        programFrame = new ProgramFrame(this);
    }

    public void openSettingsFrame() {
        settingsFrame = new SettingsFrame(this);
    }

    public boolean register(String username, String email, String password) {
        try {
            if (!services.checkIfEmailExists(email) && !services.checkIfUsernameExists(username)) {
                services.insertNewUser(email, username, password);
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void addWorkoutInfo(String name, String creatorEmail, String description, String tag1, String tag2, String tag3, ArrayList<ExerciseInfo> exerciseInfos){
        try {
            WorkoutInfo workout =  services.insertNewWorkout(name, creatorEmail, description, tag1, tag2, tag3);
            for(ExerciseInfo e : exerciseInfos){
                services.insertExerciseInToWorkout(loggedInEmail, e, workout);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addProgramInfo(String name, String creatorEmail, String description, String tag1, String tag2, String tag3, ArrayList<WorkoutInfo> workoutInfos){
        try{
            ProgramInfo program = services.insertNewProgram(name, creatorEmail, description, tag1, tag2, tag3);
            for(WorkoutInfo e : workoutInfos){
                services.insertWorkoutInToProgram(loggedInEmail, program, e);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void login(String email, String password){
        try {
            loggedInEmail = services.login(email,password);
            System.out.println(loggedInEmail);
            if(!loggedInEmail.isEmpty()){
                openMainFrame();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
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
