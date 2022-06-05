package com.example.trainingapp.Controller;

import HelperClasses.*;
import HelperClasses.Set;
import com.codename1.io.BufferedOutputStream;
import com.codename1.io.BufferedInputStream;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Form;
import com.example.trainingapp.View.*;
import com.codename1.io.Socket;
import com.codename1.io.SocketConnection;

import java.io.*;
import java.util.*;

/**
 @author Linus Regander, Daniel Olsson, William Dock, Yun-Bo Chow, Francis Jonsson
 */

public class Controller{
    private ArrayList<CompletedAchievement> completedAchievementsList = new ArrayList<>();
    private ArrayList<ExerciseInfo> exerciseList = new ArrayList<>();
    private ArrayList<WorkoutInfo> workoutList = new ArrayList<>();
    private ArrayList<ProgramInfo> programList = new ArrayList<>();
    private ArrayList<LogExerciseSet> logExerciseSetList = new ArrayList<>();
    private ArrayList<PlanExerciseSet> planExerciseSetList = new ArrayList<>();
    private ArrayList<LogWorkout> logWorkoutList = new ArrayList<>();
    private ArrayList<PlanWorkout> planWorkoutList = new ArrayList<>();
    private ArrayList<LogProgram> logProgramList = new ArrayList<>();
    private ArrayList<AchievementsInfo> allAchievementsList = new ArrayList<>();
    private ArrayList<CompletedAchievement> completedAchievements = new ArrayList<>();
    private AchievementFrame achievementFrame;
    private CreateFrame createFrame;
    private CreateProgramFrame createProgramFrame;
    private LoginFrame loginFrame;
    private MainFrame mainFrame;
    private ProgramFrame programFrame;
    private ProfileFrame profileFrame;
    private WorkoutLogFrame workoutLogFrame;
    private WorkoutSelectFrame workoutSelectFrame;
    private RegisterFrame registerFrame;
    private ExerciseSelectFrame exerciseSelectFrame;
    private SettingsFrame settingsFrame;
    private String loggedInEmail;
    private String username;
    private ICallback informee;

    /**
     * Constructor of Controller
     */
    public Controller(){
        setup();
    }

    /**
     * Setup Constructor
     */
    public void setup(){
        mainFrame = new MainFrame(this); //MainFrame is the main GUI frame.
        loginFrame = new LoginFrame(this);
    }

    /**
     * Connect method for connecting to Socket
     * @param socketConnection for connection to Socket
     */
    public void connect(SocketConnection socketConnection){
        Socket.connect("127.0.0.1", 541, socketConnection);
    }

    /**
     * Opens LoginFrame
     */
    public void openLoginFrame(){
        loginFrame = new LoginFrame(this);
    }

    /**
     * Opens MainFrame
     */
    public void openMainFrame(){
        if(mainFrame == null){
            mainFrame = new MainFrame(this);
        } else{
            mainFrame.getMainForm().show();
        }
    }

    /**
     * Opens RegisterFrame
     */
    public void openRegFrame(){
        registerFrame = new RegisterFrame(this);
    }

    /**
     * Opens AchievementFrame
     */
    public void openAchievementFrame(){
        if(achievementFrame == null){
            achievementFrame = new AchievementFrame(this, allAchievementsList, completedAchievements);
        } else{
            achievementFrame.getForm().show();
        }
    }

    /**
     * Opens CreateFrame
     */
    public void openCreateFrame(){
        if(createFrame == null){
            createFrame = new CreateFrame(this);
        }
        createFrame.getForm().show();
    }

    /**
     * Opens a new CreateFrame
     * @param exerciseInfos gets an ArrayList of ExerciseInfo objects
     * @param workoutId gets a workoutId
     */
    public void openNewCreateFrame(ArrayList<ExerciseInfo> exerciseInfos, int workoutId){
        createFrame = new CreateFrame(this, exerciseInfos, workoutId);
        createFrame.getForm().show();
    }

    /**
     * Opens ProgramFrame
     */
    public void openProgramFrame(){
        programFrame = new ProgramFrame(this);
    }

    /**
     * Opens SettingsFrame
     */
    public void openSettingsFrame(){
        if(settingsFrame == null){
            settingsFrame = new SettingsFrame(this);
        } else{
            settingsFrame.getForm().show();
        }
    }

    /**
     * Opens ProfileFrame
     */
    public void openProfileFrame(){
        if(profileFrame == null){
            profileFrame = new ProfileFrame(this);
        } else{
            profileFrame.getProfileForm().show();
        }
    }

    /**
     * Opens WorkoutLogFrame
     */
    public void openWorkoutLogFrame(){
        updateLogWorkoutList();
        workoutLogFrame = new WorkoutLogFrame(this);
    }

    /**
     * Opens ExerciseSelectFrame
     * @param createFrame Gets an instance of the CreateFrame
     */
    public void openExerciseSelectFrame(CreateFrame createFrame){
        ArrayList<ExerciseInfo> temp = getExerciseList();
        exerciseSelectFrame = new ExerciseSelectFrame(this, temp, createFrame);
    }

    /**
     * Opens WorkoutSelectFrame
     * @param createProgramFrame Gets an instance of the CreateProgramFrame
     */
    public void openWorkoutSelectFrame(CreateProgramFrame createProgramFrame){
        ArrayList<WorkoutInfo> temp = getWorkoutInfoList();
        workoutSelectFrame = new WorkoutSelectFrame(this, temp, createProgramFrame);
    }

    /**
     * Gets a new CreateFrame object
     */
    public void newCreateFrame(){
        createFrame = new CreateFrame(this);
    }

    /**
     * Opens CreateProgramFrame
     */
    public void openCreateProgramFrame(){
        createProgramFrame = new CreateProgramFrame(this);
    }

    /**
     * Login method for logging in.
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     * @param email Gets a String of email
     * @param password Gets a String of password
     */
    public void login(String email, String password){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));

                    String temp = email + "\0" + password;
                    dos.writeInt(0);
                    dos.writeUTF(temp);
                    dos.flush();

                    String response = dis.readUTF();
                    if(!response.isEmpty()){
                        String[] loggedIn = split(response);
                        loggedInEmail = loggedIn[0];
                        username = loggedIn[1];
                        updateWorkoutList();
                        updateLogWorkoutList();
                        updateLogExerciseSetList();
                        updateExerciseList();
                        updateAllAchievementsList();
                        updateCompletedAchievementsList();
                        openMainFrame();
                    } else{
                        loginFrame.failedLogin();
                    }

                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        };
        connect(sc);
    }

    /**
     * Register method for registration to the Server.
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     * @param username Gets a String of the username
     * @param email Gets a String of email
     * @param password Gets a String of password
     * @return boolean If the registration is successfull or not
     */
    public boolean register(String username, String email, String password){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){

                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));

                    String temp = email + "\0" + username + "\0" + password;
                    dos.writeInt(1);
                    dos.writeUTF(temp);
                    dos.flush();

                    int response = dis.readInt();
                    switch(response){
                        case 0:
                            registerFrame.showSuccess();
                            openLoginFrame();
                            break;
                        case 1:
                            registerFrame.getError().setText("Email is already in use");
                            break;
                        case 2:
                            registerFrame.getError().setText("Username is already in use");
                            break;
                        case 3:
                            registerFrame.getError().setText("Both username and email are already in use");
                            break;
                    }

                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
        return false;
    }

    /**
     * Adds info to a Workout.
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     * @param name Gets a String of the username
     * @param creatorEmail Gets a String of email
     * @param description Gets a String of description
     * @param exercises Gets a ArrayList of exercises
     * @param tag1 Gets a String of tag1
     * @param tag2 Gets a String of tag2
     * @param tag3 Gets a String of tag3
     */
    public int addWorkoutInfo(String name, String creatorEmail, String description, String tag1, String tag2, String tag3, ArrayList<Exercise> exercises){
        final int[] id = new int[1];
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));

                    StringBuilder temp = new StringBuilder(name + "\0" + creatorEmail + "\0" + description + "\0" + tag1 + "\0" + tag2 + "\0" + tag3);

                    for(Exercise exercise : exercises){
                        temp.append("\0").append(exercise.getId());
                        temp.append("\0").append(exercise.getSetSize());
                    }
                    dos.writeInt(3);
                    dos.writeUTF(temp.toString());
                    dos.flush();

                    id[0] = dis.readInt();
                    workoutList.add(new WorkoutInfo(id[0], name, creatorEmail, description, tag1, tag2, tag3, username));
                    informee.inform(id[0], description);

                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
        return id[0];
    }

    /**
     * Adds info of a Workout to the Log
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     * @param email Gets a String of email
     * @param workoutId Gets a workoutId
     * @param evaluation Gets a String of evaluation
     * @param date Gets a String of date
     * @param exercises Gets a ArrayList of exercises
     */
    public void addLogWorkout(String email, int workoutId, String date, String evaluation, ArrayList<Exercise> exercises){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));

                    StringBuilder temp = new StringBuilder(email + "\0" + workoutId + "\0" + date + "\0" + evaluation);
                    int i = 1;
                    for(Exercise exercise : exercises){
                        for(Set set : exercise.getSets()){
                            temp.append("\0").append(exercise.getId());
                            temp.append("\0").append(i);
                            temp.append("\0").append(set.getReps());
                            temp.append("\0").append(set.getWeight());
                            i++;
                        }
                        i = 1;
                    }
                    dos.writeInt(4);
                    dos.writeUTF(temp.toString());
                    dos.flush();

                    int id = dis.readInt();
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                    logWorkoutList.add(new LogWorkout(id, workoutId, email, date1, evaluation));

                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
        connect(sc);
    }

    public int addProgramInfo(String name, String creatorEmail, String description, String tag1, String tag2, String tag3, ArrayList<WorkoutInfo> workoutInfos){
        final int[] id = new int[1];
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));

                    StringBuilder temp = new StringBuilder(name + "\0" + creatorEmail + "\0" + description + "\0" + tag1 + "\0" + tag2 + "\0" + tag3);

                    dos.writeInt(5);
                    dos.writeUTF(temp.toString());
                    dos.flush();

                    id[0] = dis.readInt();
                    programList.add(new ProgramInfo(id[0], name, creatorEmail, description, tag1, tag2, tag3, username));
                    informee.inform(id[0], description);

                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
        return id[0];
    }

    public void addExercise(int workoutId, int exerciseId, int sets, int reps, double weight){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));

                    String temp = workoutId + "\0" + exerciseId + "\0" + sets + "\0" + reps + "\0" + weight + "\0";
                    dos.writeInt(7);
                    dos.writeUTF(temp);
                    dos.flush();

                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        };
        connect(sc);
    }

    /**
     * Gets a list of logged workouts.
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     */
    public void updateLogWorkoutList(){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(8);
                    dos.flush();
                    dos.writeUTF(loggedInEmail);
                    dos.flush();

                    ArrayList<LogWorkout> arrayTemp = new ArrayList<>();
                    String[] strings;
                    String temp = dis.readUTF();
                    strings = split(temp);

                    for(int i = 0; i < strings.length / 5; i++){
                        int logWorkoutId = Integer.parseInt(strings[i * 5]);
                        int workoutId = Integer.parseInt(strings[i * 5 + 1]);
                        String creatorEmail = strings[i * 5 + 2];
                        Date date = new SimpleDateFormat("yyyy-mm-dd").parse(strings[i * 5 + 3]);
                        String evaluation = strings[i * 5 + 4];

                        arrayTemp.add(new LogWorkout(logWorkoutId, workoutId, creatorEmail, date, evaluation));
                    }
                    logWorkoutList = arrayTemp;
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }

    /**
     * Gets a list of exercises.
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     */
    public void updateExerciseList(){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(10);
                    dos.flush();

                    ArrayList<ExerciseInfo> arrayTemp = new ArrayList<>();
                    String[] strings;
                    String temp = dis.readUTF();
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

    /**
     * Gets a list of workouts.
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     */
    public void updateWorkoutList(){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(11);
                    dos.flush();

                    ArrayList<WorkoutInfo> arrayTemp = new ArrayList<>();
                    String[] strings = split(dis.readUTF());
                    List<String> listTemp = Arrays.asList(strings);
                    LinkedList<String> linkTemp = new LinkedList<>(listTemp);

                    while(!linkTemp.isEmpty()){
                        int id = Integer.parseInt(linkTemp.remove());
                        String name = linkTemp.remove();
                        String creatorEmail = linkTemp.remove();
                        String description = linkTemp.remove();
                        String tag1 = linkTemp.remove();
                        String tag2 = linkTemp.remove();
                        String tag3 = linkTemp.remove();
                        String creatorUsername = linkTemp.remove();
                        int sets = Integer.parseInt(linkTemp.remove());

                        ArrayList<ExerciseInfo> exerciseTemp = new ArrayList<>();

                        for(int i = 0; i < sets; i++){
                            int exId = Integer.parseInt(linkTemp.remove());
                            String exName = linkTemp.remove();
                            String exDescription = linkTemp.remove();
                            String primary = linkTemp.remove();
                            String secondary = linkTemp.remove();
                            int setCount = Integer.parseInt(linkTemp.remove());

                            exerciseTemp.add(new ExerciseInfo(exId, exName, exDescription, primary, secondary, setCount));
                        }

                        arrayTemp.add(new WorkoutInfo(id, name, creatorEmail, description, tag1, tag2, tag3, creatorUsername, exerciseTemp));
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
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(12);
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

    /**
     * Gets a list of logged exercises.
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     */
    public void updateLogExerciseSetList(){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(13);
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

    public void updateLogProgramList(){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
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
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(strings[i * 5 + 3]);
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

    /**
     * Gets a list of achievements.
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     */
    public void updateAllAchievementsList(){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){

                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));

                    dos.writeInt(14);
                    dos.flush();

                    ArrayList<AchievementsInfo> arrayTemp = new ArrayList<>();
                    String temp = dis.readUTF();
                    String[] strings = split(temp);

                    for(int i = 0; i < strings.length / 3; i++){
                        int achievementId = Integer.parseInt(strings[i * 3]);
                        String name = strings[i * 3 + 1];
                        String description = strings[i * 3 + 2];

                        arrayTemp.add(new AchievementsInfo(achievementId, name, description));
                    }

                    allAchievementsList = arrayTemp;
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }

    /**
     * Gets a list of completed achievements.
     * Sends data to the server through a OutputStream with writeInt and writeUTF.
     */
    public void updateCompletedAchievementsList(){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){

                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));

                    dos.writeInt(15);
                    dos.writeUTF(loggedInEmail);
                    dos.flush();

                    ArrayList<CompletedAchievement> arrayTemp = new ArrayList<>();
                    String temp = dis.readUTF();
                    String[] strings = split(temp);

                    for(int i = 0; i < strings.length / 3; i++){
                        int achievementId = Integer.parseInt(strings[i * 3]);
                        String email = strings[i * 3 + 1];
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(strings[i * 3 + 2]);
                        arrayTemp.add(new CompletedAchievement(achievementId, email, date));
                    }

                    completedAchievements = arrayTemp;
                } catch (IOException | ParseException e){
                    e.printStackTrace();
                }
            }
        };
        connect(sc);
    }

    public void addPlanWorkout(int planWorkoutId, int workoutId, String creator, Date date){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){

                try{
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

    public void updatePlanExercise(){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(23);
                    dos.flush();

                    ArrayList<WorkoutInfo> arrayTemp = new ArrayList<>();
                    String temp = dis.readUTF();
                    String[] strings = temp.split("\0");

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

    public void addPlanExerciseSet(int planExerciseId, int exerciseId, int set, int reps, double weight, String email, int planWorkoutId){
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));

                    String temp = planExerciseId + "\0" + exerciseId + "\0" + set + "\0" + reps + "\0" + weight + "\0" + email + "\0" + planWorkoutId;
                    dos.writeInt(24);
                    dos.writeUTF(temp);
                    dos.flush();

                } catch (IOException e){
                    e.printStackTrace();
                }

            }
        };
        connect(sc);
    }

    /**
     * Splits string with a StringTokenizer.
     * @return a String array.
     */
    public String[] split(String str){
        ArrayList<String> splitArray = new ArrayList<>();
        StringTokenizer arr = new StringTokenizer(str, "\0");//split by commas
        while(arr.hasMoreTokens())
            splitArray.add(arr.nextToken());
        return splitArray.toArray(new String[splitArray.size()]);
    }

    /**
     * Gets a list of logged workouts.
     * @return a ExerciseInfo ArrayList.
     */
    public ArrayList<ExerciseInfo> getExerciseList(){
        return exerciseList;
    }

    /**
     * Gets the number of Workouts created by the user.
     * @return Number of created workouts.
     */
    public int workoutCount(){
        updateWorkoutList();
        int count = 0;

        for(int i = 0; i < workoutList.size(); i++){
            if (workoutList.get(i).getCreatorEmail().equals(loggedInEmail)){
                count++;
            }
        }

        return count;
    }

    /**
     * Gets the number of sets in the log.
     * @return The amount of sets.
     */
    public int setCount(){
        int count = 0;

        for(int i = 0; i < logExerciseSetList.size(); i++){
            if (logExerciseSetList.get(i).getEmail().equals(loggedInEmail)){
                count = logExerciseSetList.get(i).getSet();
            }
        }
        return count;
    }

    /**
     * Gets the number of reps in the log.
     * @return The amount of reps.
     */
    public int repCount(){
        int count = 0;

        for(int i = 0; i < logExerciseSetList.size(); i++){
            if (logExerciseSetList.get(i).getEmail().equals(loggedInEmail)){
                count = logExerciseSetList.get(i).getReps();
            }
        }
        return count;
    }

    public int getWorkoutId(){
        int id = 0;

        for(WorkoutInfo w : workoutList){
            id = w.getId();
        }

        return id;
    }

    /**
     * Gets the number of completed achievements.
     * @return The amount of completed achievements.
     */
    public int getCAchievements(){
        int count = 0;

        for(int i = 0; i < completedAchievementsList.size(); i++){
            count++;
        }

        return count;
    }

    /**
     * Gets a String value of the logged-in email.
     * @return The logged-in email.
     */
    public String getLoggedInEmail(){
        return loggedInEmail;
    }

    /**
     * Gets a String value of the username.
     * @return The username.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Gets the MainForm of the MainFrame class.
     * @return The form.
     */
    public Form getMainForm(){
        return mainFrame.getMainForm();
    }

    /**
     * Gets a list of logged workouts.
     * @return The list of logged workouts.
     */
    public ArrayList<LogWorkout> getLogWorkoutList(){
        return logWorkoutList;
    }

    /**
     * Gets a list of information about workouts.
     * @return The list of information.
     */
    public ArrayList<WorkoutInfo> getWorkoutInfoList(){
        return workoutList;
    }

    public ArrayList<LogExerciseSet> getLogExerciseSetList(){
        return logExerciseSetList;
    }

    /**
     * Creates a informee callback reference to the server.
     */
    public void setInformee(ICallback informee){
        this.informee = informee;
    }
}
