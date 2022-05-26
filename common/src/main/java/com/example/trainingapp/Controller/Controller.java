package com.example.trainingapp.Controller;

import HelperClasses.*;
import com.codename1.io.BufferedOutputStream;
import com.codename1.io.BufferedInputStream;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Form;
import com.example.trainingapp.View.*;
import com.codename1.io.Socket;
import com.codename1.io.SocketConnection;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

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

    public Controller(){
        setup();
    }

    //Setup constructor
    public void setup(){
        //createProgramFrame = new CreateProgramFrame(this);
        mainFrame = new MainFrame(this); //MainFrame is the main GUI frame.
        loginFrame = new LoginFrame(this);
    }

    public void connect(SocketConnection socketConnection){
        Socket.connect("127.0.0.1", 541, socketConnection);
    }

    //The code below creates all the GUI frames part of the application:
    public void openLoginFrame(){
        loginFrame = new LoginFrame(this);
    }

    public void openMainFrame(){
        if(mainFrame == null){
            mainFrame = new MainFrame(this);
        } else{
            mainFrame.getMainForm().show();
        }
    }

    public void openRegFrame(){
        registerFrame = new RegisterFrame(this);
    }

    public void openAchievementFrame(){
        if(achievementFrame == null){
            achievementFrame = new AchievementFrame(this, allAchievementsList, completedAchievements);
        } else{
            achievementFrame.getForm().show();
        }
    }

    public void openCreateFrame(){
        System.out.println(logWorkoutList);
        if(createFrame == null){
            createFrame = new CreateFrame(this);
        }
        createFrame.getForm().show();
    }

    public void openProgramFrame(){
        programFrame = new ProgramFrame(this);
    }

    public void openSettingsFrame(){
        if(settingsFrame == null){
            settingsFrame = new SettingsFrame(this);
        } else{
            settingsFrame.getForm().show();
        }
    }

    public void openProfileFrame(){
        if(profileFrame == null){
            profileFrame = new ProfileFrame(this);
        } else{
            profileFrame.getProfileForm().show();
        }
    }

    public void openWorkoutLogFrame(){
        updateLogWorkoutList();
        workoutLogFrame = new WorkoutLogFrame(this);
    }
    public void openExerciseSelectFrame(CreateFrame createFrame){
        ArrayList<ExerciseInfo> temp = getExerciseList();
        exerciseSelectFrame = new ExerciseSelectFrame(this, temp, createFrame);
    }

    public void openWorkoutSelectFrame(CreateProgramFrame createProgramFrame){
        ArrayList<WorkoutInfo> temp = getWorkoutInfoList();
        workoutSelectFrame = new WorkoutSelectFrame(this, temp, createProgramFrame);
    }

    public void newCreateFrame(){
        createFrame = new CreateFrame(this);
    }

    public void openCreateProgramFrame(){
        createProgramFrame = new CreateProgramFrame(this);
    }

    public Form getMainForm(){
        return mainFrame.getMainForm();
    }

    //todo: Everything below will be used later:
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

    //write int tells the server about what method it is calling
    //writeUTF is the data that gets sent to the server which also handles it server-sided.
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

    public int addWorkoutInfo(String name, String creatorEmail, String description, String tag1, String tag2, String tag3, ArrayList<ExerciseInfo> exerciseInfos){
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

                    for(ExerciseInfo exerciseInfo : exerciseInfos){
                        temp.append("\0").append(exerciseInfo.getId());
                        temp.append("\0").append(exerciseInfo.getName());
                        temp.append("\0").append(exerciseInfo.getDescription());
                        temp.append("\0").append(exerciseInfo.getPrimary());
                        temp.append("\0").append(exerciseInfo.getSecondary());
                    }
                    dos.writeInt(5);
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
                    dos.writeInt(6);
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

    // TODO: 2022-05-08 PlanWorkoutId finns inte förens efter den har insertats till databasen
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

    // TODO: 2022-05-08 planExerciseId does not exist until the first data gets added into the database.
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
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){

            }
        };
    }

    //todo: refresh button in GUI that updates specific lists
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
                    dos.writeInt(7);
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
                    dos.writeInt(8);
                    dos.flush();

                    ArrayList<WorkoutInfo> arrayTemp = new ArrayList<>();
                    String[] strings = split(dis.readUTF());

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
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
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
        SocketConnection sc = new SocketConnection(){
            @Override
            public void connectionError(int i, String s){

            }

            @Override
            public void connectionEstablished(InputStream inputStream, OutputStream outputStream){
                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(outputStream));
                    DataInputStream dis = new DataInputStream(new BufferedInputStream(inputStream));
                    dos.writeInt(3);
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

    public String[] split(String str){
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

    public int setCount(){
        int count = 0;

        for(int i = 0; i < logExerciseSetList.size(); i++){
            if (logExerciseSetList.get(i).getEmail().equals(loggedInEmail)){
                count = logExerciseSetList.get(i).getSet();
            }
        }
        return count;
    }

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

    public int getCAchievements(){
        int count = 0;

        for(int i = 0; i < completedAchievementsList.size(); i++){
            count++;
        }

        return count;
    }

    public String getLoggedInEmail(){
        return loggedInEmail;
    }

    public String getUsername(){
        return username;
    }

    public ArrayList<LogWorkout> getLogWorkoutList(){
        return logWorkoutList;
    }

    public ArrayList<WorkoutInfo> getWorkoutInfoList(){
        return workoutList;
    }

    public ArrayList<LogExerciseSet> getLogExerciseSetList(){
        return logExerciseSetList;
    }

    public void setInformee(ICallback informee){
        this.informee = informee;
    }
}
