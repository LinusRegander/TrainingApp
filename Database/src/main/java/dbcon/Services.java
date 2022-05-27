package dbcon;

import HelperClasses.*;
import com.codename1.db.Database;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 @author William Dock, Yun-Bo Chow
 */

public class Services {

    public ConnectionRequest getDatabaseConnectionTest() {
        String url = "jdbc:postgresql://pgserver.mau.se:5432/am2578";
        String user = "am2578";
        String password = "29zptibk";
        ConnectionRequest con = null;

        try{
            con = new ConnectionRequest(url);
            System.out.println("Connection Established");
            return con;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Connection getDatabaseConnection(){
        String url = "jdbc:postgresql://pgserver.mau.se:5432/am2578";
        String user = "am2578";
        String password = "29zptibk";
        Connection con = null;

        try{
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Established");
            return con;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String login(String email, String password) throws SQLException{
        String loginMailAndUsername = "";
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.users where email = ? and password = ?");
        pstmt.setString(1, email);
        pstmt.setString(2, Encrypter.encrypt(password));
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()){
            loginMailAndUsername += rs.getString("email");
            loginMailAndUsername += "\0";
            loginMailAndUsername += rs.getString("username");

        }
        pstmt.close();
        rs.close();
        con.close();

        return loginMailAndUsername;
    }

    public boolean checkIfEmailExists(String email) throws SQLException{
        boolean exists = false;
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.users where email = ?");
        pstmt.setString(1,email);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()){
            String userMail = rs.getString("email");
            if(userMail!=null){
                exists = true;
            }
        }

        return exists;
    }

    public boolean checkIfUsernameExists(String username) throws SQLException{
        boolean exists = false;
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.users where username = ?");
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()){
            String userMail = rs.getString("username");
            if(userMail!=null){
                exists = true;
            }
        }

        return exists;
    }

    //todo: lägg till så att isAdmin är en column på databas tabellen users istället för username admin
    public boolean checkIfLoggedInMailIsAdmin(String loggedInEmail) throws SQLException{
        boolean isAdmin = false;
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select name from training.users where email = ?");
        pstmt.setString(1, loggedInEmail);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            String name = rs.getString("name");
            if (name.equals("admin")){
                isAdmin = true;
            }
        }

        return isAdmin;
    }


//nedan är alla inserts
    public void insertNewUser(String email, String username, String password) throws SQLException {
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("call training.insertNewUser(?,?,?)");
        pstmt.setString(1, email);
        pstmt.setString(2, username);
        pstmt.setString(3, Encrypter.encrypt(password));

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    public void insertNewExercise(String name, String description, String primaryMuscleGroup, String secondaryMuscleGroup) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("call training.insertNewExercise(?,?,?,?)");
        pstmt.setString(1, name);
        pstmt.setString(2, description);
        pstmt.setString(3, primaryMuscleGroup);
        pstmt.setString(4, secondaryMuscleGroup);

        pstmt.execute();
        pstmt.close();
        con.close();

    }

    //TODO: lägg till en privat metod som checkar om exerciseId samt workoutId redan finns. genom select XX where YY + if() sats
    public String insertExerciseInToWorkout(String loggedInMail, ExerciseInfo exercise, WorkoutInfo workout) throws SQLException{
        if(workout.getCreatorEmail().equals(loggedInMail)) {
            Connection con = this.getDatabaseConnection();
            PreparedStatement pstmt = con.prepareStatement("call training.insertExerciseInToWorkout(?,?)");
            pstmt.setInt(1, exercise.getId());
            pstmt.setInt(2, workout.getId());

            pstmt.execute();
            pstmt.close();
            con.close();

            return "Exercise has been added to " + workout.getName();
        }
        else {
            return "You do not have access";
        }

    }

    public String insertExerciseInToWorkout(String loggedInMail, int exerciseId, int workoutId) throws SQLException{
        if(checkIfCreatorOfWorkout(loggedInMail, workoutId)) {
            Connection con = this.getDatabaseConnection();
            PreparedStatement pstmt = con.prepareStatement("call training.insertExerciseInToWorkout(?,?)");
            pstmt.setInt(1, exerciseId);
            pstmt.setInt(2, workoutId);

            pstmt.execute();
            pstmt.close();
            con.close();

            return "Successful";
        }
        else {
            return "You do not have access";
        }
    }

    public void insertExerciseInToWorkout(int exerciseId, int workoutId, int sets) throws SQLException{

        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("insert into training.workout(exerciseid, workoutid, sets) " +
                "values(?,?,?);");

        pstmt.setInt(1, exerciseId);
        pstmt.setInt(2, workoutId);
        pstmt.setInt(3, sets);

        pstmt.execute();
        pstmt.close();
        con.close();

    }

    //creatorEmail == email som man är inloggad på
    public WorkoutInfo insertNewWorkout(String name, String creatorEmail, String description, String tag1, String tag2, String tag3) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertNewWorkout(?,?,?,?,?,?)");
        pstmt.setString(1, name);
        pstmt.setString(2, creatorEmail);
        pstmt.setString(3, description);
        pstmt.setString(4, tag1);
        pstmt.setString(5, tag2);
        pstmt.setString(6, tag3);

        pstmt.execute();
        pstmt.close();
        con.close();
        int id = getWorkoutId(creatorEmail);
        return new WorkoutInfo(id, name, creatorEmail, description, tag1, tag2, tag3, getUsername(creatorEmail));
    }

    //creatorEmail == email som man är inloggad på
    public ProgramInfo insertNewProgram(String name, String creatorEmail, String description, String tag1, String tag2, String tag3) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertNewProgram(?,?,?,?,?,?,?)");
        pstmt.setString(1, name);
        pstmt.setString(2, creatorEmail);
        pstmt.setString(3, description);
        pstmt.setString(4, tag1);
        pstmt.setString(5, tag2);
        pstmt.setString(6, tag3);

        pstmt.execute();
        pstmt.close();
        con.close();
        int id = getProgramId(creatorEmail);
        return  new ProgramInfo(id, name, creatorEmail, description, tag1, tag2, tag3, getUsername(creatorEmail));
    }

    public String insertWorkoutInToProgram(String loggedInMail,ProgramInfo programInfo, WorkoutInfo workoutInfo) throws SQLException{
        if(programInfo.getCreatorEmail().equals(loggedInMail)){
            Connection con = this.getDatabaseConnection();
            PreparedStatement pstmt = con.prepareStatement("Call training.insertWorkoutInToProgram(?,?)");
            pstmt.setInt(1, programInfo.getId());
            pstmt.setInt(1, workoutInfo.getId());

            pstmt.execute();
            pstmt.close();
            con.close();
            return "Workout has been added to " + programInfo.getName();
        } else {
            return "You do not have access";
        }
    }

    public String insertWorkoutInToProgram(String loggedInMail, int programInfoId, int workoutId) throws SQLException{
        if(checkIfCreatorOfProgram(loggedInMail, programInfoId)){
            Connection con = this.getDatabaseConnection();
            PreparedStatement pstmt = con.prepareStatement("Call training.insertWorkoutInToProgram(?,?)");
            pstmt.setInt(1, programInfoId);
            pstmt.setInt(1, workoutId);

            pstmt.execute();
            pstmt.close();
            con.close();
            return "Successful";
        } else {
            return "You do not have access";
        }
    }

    public void insertNewPLanWorkout(String loggedInMail, int workoutId, Date date) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertNewPlanWorkout(?,?,?)");
        pstmt.setString(1, loggedInMail);
        pstmt.setInt(2, workoutId);
        pstmt.setDate(3, date);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    public void insertPlanExerciseSetIntoPlanWorkout(int planWorkoutId, int exerciseInfoId, int set, int reps, double weight) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertPlanExerciseSetIntoPlanWorkout(?,?,?,?,?)");
        pstmt.setInt(1, planWorkoutId);
        pstmt.setInt(2, exerciseInfoId);
        pstmt.setInt(3, set);
        pstmt.setInt(4, reps);
        pstmt.setDouble(5, weight);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    //email == inloggade email
    public void insertLogExerciseSet(int exerciseId, int set, int reps, double weight, String loggedInMail, int logWorkoutId) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("call training.logexerciseset(?,?,?,?,?,?)");
        pstmt.setInt(1, exerciseId);
        pstmt.setInt(2, set);
        pstmt.setInt(3, reps);
        pstmt.setDouble(4, weight);
        pstmt.setString(5, loggedInMail);
        pstmt.setInt(6, logWorkoutId);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    //Ska denna köras automatiskt efter logExerciseSet har exekverats?

    public int insertNewLogworkout(String email, int workoutid, Date date, String evaluation) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertNewLogWorkout(?,?,?,?)");
        pstmt.setString(1, email);
        pstmt.setInt(2, workoutid);
        pstmt.setDate(3, date);
        pstmt.setString(4, evaluation);

        pstmt.execute();
        pstmt.close();
        con.close();
        return getLogWorkoutId(email);
    }

    public void insertNewLogProgram(String email, int programId, Date date, String evaluation) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertNewLogProgram(?,?,?,?)");
        pstmt.setString(1, email);
        pstmt.setInt(2, programId);
        pstmt.setDate(3, date);
        pstmt.setString(4, evaluation);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    public void insertNewAchievementsInfo(String name, String description) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertAchievementsInfo(?,?)");
        pstmt.setString(1, name);
        pstmt.setString(2, description);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    public void insertCompleteAchievement(int achievementId, String loggedInMail, Date date) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertCompletedAchievement(?,?,?)");
        pstmt.setInt(1, achievementId);
        pstmt.setString(2, loggedInMail);
        pstmt.setDate(3, date);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

// ovanför är alla inserts, sedan remove sedan update

    public void removeLoggedExerciseSet(String logExerciseId) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Delete from training.logexerciseset where logexerciseid = ?");
        pstmt.setString(1, logExerciseId);
        pstmt.execute();
        pstmt.close();
        con.close();
    }

    public void removeLoggedWorkout(String logWorkoutId) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Delete from training.logworkout where logworkoutid = ?");
        pstmt.setString(1, logWorkoutId);
        pstmt.execute();
        pstmt.close();
        con.close();
    }

    public void removeLoggedProgram(String logProgramId) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Delete from training.logprogram where logprogramid = ?");
        pstmt.setString(1, logProgramId);
        pstmt.execute();
        pstmt.close();
        con.close();
    }

    public void removeUser(String userId) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Delete from training.users where email = ?");
        pstmt.setString(1, userId);
        pstmt.execute();
        pstmt.close();
        con.close();
    }


// ovanför är alla removes, sedan update

    //loggedInMail är mail från GUI/Controller? som den ska ha fått efter anrop till login metoden. Det är så vi kan se om de är inloggade eller ej
    //denna metoden kan endast köras när man är inloggad, det är inte en forget password metod. Utan det är en när man är inloggad så kan man byta.
    public String updateLogin(String loggedInMail, String email, String username, String password, int choice) throws SQLException{
        Connection con = this.getDatabaseConnection();

        switch (choice){
            case 1:
                if(!this.checkIfEmailExists(email)){
                    PreparedStatement updateEmail = con.prepareStatement("update training.users set email = ? where email = ?");
                    updateEmail.setString(1, email);
                    updateEmail.setString(2, loggedInMail);
                    updateEmail.executeUpdate();

                    updateEmail.close();
                    con.close();
                    return "email has been changed";
                } else {
                    return "invalid mail";
                }

            case 2:
                if(!this.checkIfUsernameExists(username)){
                    PreparedStatement updateName = con.prepareStatement("update training.users set name = ? where email = ?");
                    updateName.setString(1, username);
                    updateName.setString(2, loggedInMail);
                    updateName.executeUpdate();

                    updateName.close();
                    con.close();
                    return "username has been changed";
                } else {
                    return "invalid username";
                }

            case 3:
                PreparedStatement updatePassword = con.prepareStatement("update training.users set password = ? where email = ?");
                updatePassword.setString(1, Encrypter.encrypt(password));
                updatePassword.setString(2, loggedInMail);
                updatePassword.executeUpdate();

                updatePassword.close();
                con.close();
                return "password has been changed";
            default:
                con.close();
                return "choice is out of bounds";

        }
    }

    //Antingen så ska man kunna se alla sina saker genom select statements innan metoden anropas eller längst upp i metoden
    //TODO: Ändra så att det blir med objekt istället...
    public String updateExercise(int exerciseId, String loggedInMail, String name, String description, String primaryMuscleGroup, String secondaryMuscleGroup, int choice) throws SQLException{

        if(this.checkIfLoggedInMailIsAdmin(loggedInMail)){
            Connection con = this.getDatabaseConnection();
            switch (choice){
                case 1:
                    PreparedStatement updateName = con.prepareStatement("update training.exercise set name = ? where exerciseid = ?");
                    updateName.setString(1, name);
                    updateName.setInt(2, exerciseId);
                    updateName.executeUpdate();

                    updateName.close();
                    con.close();
                    return "Exercise name has been changed";
                case 2:
                    PreparedStatement updateDescription = con.prepareStatement("update training.exercise set description = ? where exerciseid = ?");
                    updateDescription.setString(1, description);
                    updateDescription.setInt(2, exerciseId);
                    updateDescription.executeUpdate();

                    updateDescription.close();
                    con.close();
                    return "Exercise description has been changed";
                case 3:
                    PreparedStatement updatePrimary = con.prepareStatement("update training.exercise set primarymusclegroup = ? where exerciseid = ?");
                    updatePrimary.setString(1, primaryMuscleGroup);
                    updatePrimary.setInt(2, exerciseId);
                    updatePrimary.executeUpdate();

                    updatePrimary.close();
                    con.close();
                    return "Exercise primaryMuscleGroup has been changed";
                case 4:
                    PreparedStatement updateSecondary = con.prepareStatement("update training.exercise set secondarymusclegroup = ? where exerciseid = ?");
                    updateSecondary.setString(1, secondaryMuscleGroup);
                    updateSecondary.setInt(2, exerciseId);
                    updateSecondary.executeUpdate();

                    updateSecondary.close();
                    con.close();
                    return "Exercise secondaryMuscleGroup has been changed";
                default:
                    return "choice is out of bounds";
            }
        } else {
            return "You do not have access";
        }
    }


    public String updateWorkout(String loggedInMail, WorkoutInfo workoutInfo) throws SQLException{
        if(workoutInfo.getCreatorEmail().equals(loggedInMail)){
            Connection con = this.getDatabaseConnection();
            PreparedStatement pstmt = con.prepareStatement("update training.workoutinfo set name = ?, description = ?, tag_1 = ?, tag_2 = ?, tag_3 = ? where workoutid = ?");
            pstmt.setString(1, workoutInfo.getName());
            pstmt.setString(2, workoutInfo.getDescription());
            pstmt.setString(3, workoutInfo.getTag1());
            pstmt.setString(4, workoutInfo.getTag2());
            pstmt.setString(5, workoutInfo.getTag3());
            pstmt.setInt(6, workoutInfo.getId());
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
            return "WorkoutInfo has been changed";
        } else {
            return "You do not have access";
        }
    }

    public String updateProgram(String loggedInMail, ProgramInfo programInfo) throws SQLException{
        if(programInfo.getCreatorEmail().equals(loggedInMail)){
            Connection con = this.getDatabaseConnection();
            PreparedStatement pstmt = con.prepareStatement("update training.programinfo set name = ?, description = ?, tag1 = ?, tag2 = ?, tag3 = ? where programid = ?");
            pstmt.setString(1, programInfo.getName());
            pstmt.setString(2, programInfo.getDescription());
            pstmt.setString(3, programInfo.getTag1());
            pstmt.setString(4, programInfo.getTag2());
            pstmt.setString(5, programInfo.getTag3());
            pstmt.setInt(6, programInfo.getId());
            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            return "ProgramInfo has been changed";
        } else {
            return "You do not have access";
        }

    }

    //behöver man kunna ändra logworkoutid?
    public String updateLogExerciseSet(String loggedInMail, LogExerciseSet logExerciseSet) throws SQLException{
        if(logExerciseSet.getEmail().equals(loggedInMail)){
            Connection con = this.getDatabaseConnection();
            PreparedStatement pstmt = con.prepareStatement("update training.logexerciseset set reps = ?, set = ?, weight = ?, logworkoutid = ? where logexerciseid = ?");
            pstmt.setInt(1, logExerciseSet.getReps());
            pstmt.setInt(2, logExerciseSet.getSet());
            pstmt.setDouble(3, logExerciseSet.getWeight());
            pstmt.setInt(4, logExerciseSet.getLogWorkoutId());
            pstmt.setInt(5, logExerciseSet.getLogWorkoutId());
            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            return "LogExerciseSet has been changed";
        } else {
            return "You do not have access";
        }

    }

    //todo: Fixa date
    //SKA MAN ENDAST KUNNA ÄNDRA DATE OCH EVALUATION? ISÅFALL HADE VI KUNNAT TA BORT GETTERS OCH SETTERS I KLASSERNA
    public String updateLogWorkout(String loggedInMail, LogWorkout logWorkout) throws SQLException{
        if(logWorkout.getCreator().equals(loggedInMail)){
            Connection con = this.getDatabaseConnection();
            PreparedStatement pstmt = con.prepareStatement("update training.logworkout set date = ?, evaluation = ? where logworkoutid = ?");
            //pstmt.setDate(1, logWorkout.getDate());
            pstmt.setString(2, logWorkout.getEvaluation());
            pstmt.setInt(3, logWorkout.getLogWorkoutId());
            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            return "LogWorkout has been changed";
        } else {
            return "You do not have access";
        }
    }

    public String updateLogProgram(String loggedInMail, LogProgram logProgram) throws SQLException{
        if(logProgram.getEmail().equals(loggedInMail)){
            Connection con = this.getDatabaseConnection();
            PreparedStatement pstmt = con.prepareStatement("update training.logprogram set date = ?, evaluation = ? where logprogramid = ?");
            pstmt.setDate(1, (Date) logProgram.getDate());
            pstmt.setString(2, logProgram.getEvaluation());
            pstmt.setInt(3, logProgram.getLogProgramId());
            pstmt.executeUpdate();

            pstmt.close();
            con.close();
            return "logProgram has been changed";
        } else {
            return "You do not have access";
        }
    }


// nedan är alla select statements

    public ArrayList<ExerciseInfo> selectExercises() throws SQLException {
        ArrayList<ExerciseInfo> exercises = new ArrayList<>();
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.exercise");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int id = rs.getInt("exerciseid");
            String name = rs.getString("name");
            String des = rs.getString("description");
            String pri = rs.getString("primarymusclegroup");
            String sec = rs.getString("secondarymusclegroup");

            exercises.add(new ExerciseInfo(id, name, des, pri, sec));
        }
        pstmt.close();
        rs.close();
        con.close();

        return exercises;
    }

    public ArrayList<WorkoutInfo> selectWorkoutInfo() throws SQLException {
        ArrayList<WorkoutInfo> workoutInfos = new ArrayList<>();
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select name, workoutid, description, tag_1, tag_2, tag_3, creatoremail, " +
                "username from training.workoutinfo " +
                "join training.users on workoutinfo.creatoremail = users.email");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int id = rs.getInt("workoutid");
            String name = rs.getString("name");
            String creatorEmail = rs.getString("creatoremail");
            String description = rs.getString("description");
            String tag1 = rs.getString("tag_1");
            String tag2 = rs.getString("tag_2");
            String tag3 = rs.getString("tag_3");
            String username = rs.getString("username");

            ArrayList<ExerciseInfo> exerciseInfos = new ArrayList<>();
            PreparedStatement pstmt1 = con.prepareStatement("select * from training.workout where workoutid = ?");
            pstmt1.setInt(1, id);
            ResultSet rs1 = pstmt.executeQuery();
            while (rs1.next()){
                int exerciseId = rs1.getInt("exerciseid");
                int sets = rs1.getInt("sets");
                exerciseInfos.add(getExerciseInfo(con, exerciseId, sets));
            }
            rs1.close();
            pstmt1.close();

            WorkoutInfo workoutInfo = new WorkoutInfo(id, name, creatorEmail, description, tag1, tag2, tag3, username, exerciseInfos);
            workoutInfos.add(workoutInfo);
        }
        pstmt.close();
        rs.close();
        con.close();

        return workoutInfos;
    }

    public ArrayList<ProgramInfo> selectProgramInfo() throws SQLException {
        ArrayList<ProgramInfo> programInfos = new ArrayList<>();
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select name, programid, description, tag1, tag2, tag3, creatoremail, " +
                "username from training.programinfo " +
                "join training.users on programinfo.creatoremail = users.email");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int id = rs.getInt("programid");
            String name = rs.getString("name");
            String creator = rs.getString("creator");
            String description = rs.getString("description");
            String tag1 = rs.getString("tag1");
            String tag2 = rs.getString("tag2");
            String tag3 = rs.getString("tag3");
            String username = rs.getString("username");

            programInfos.add(new ProgramInfo(id, name, creator, description, tag1, tag2, tag3, username));
        }
        pstmt.close();
        rs.close();
        con.close();

        return programInfos;
    }

    public ArrayList<LogExerciseSet> selectLogExerciseSet(String userEmail) throws SQLException{
        ArrayList<LogExerciseSet> logExerciseSets = new ArrayList<>();
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.logexerciseset where email = ?");
        pstmt.setString(1, userEmail);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int setId = rs.getInt("exercisesetid");
            int exerciseId = rs.getInt("exerciseid");
            int workoutId = rs.getInt("logworkoutid");
            String email = rs.getString("email");
            int set = rs.getInt("set");
            int rep = rs.getInt("reps");
            double weight = rs.getDouble("weight");

            logExerciseSets.add(new LogExerciseSet(setId, exerciseId,  set, rep, weight, email, workoutId));
        }
        pstmt.close();
        rs.close();
        con.close();

        return logExerciseSets;
    }

    public ArrayList<LogWorkout> selectLogWorkout(String userEmail) throws SQLException{
        ArrayList<LogWorkout> logWorkouts = new ArrayList<>();
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.logworkout where email = ?");
        pstmt.setString(1, userEmail);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int logId = rs.getInt("logworkoutid");
            int workoutId = rs.getInt("workoutid");
            String email = rs.getString("email");
            Date date = rs.getDate("date");
            String evaluation = rs.getString("evaluation");

            logWorkouts.add(new LogWorkout(logId, workoutId, email, date, evaluation));
        }
        pstmt.close();
        rs.close();
        con.close();

        return logWorkouts;
    }

    public ArrayList<LogProgram> selectLogProgram(String userEmail) throws SQLException{
        ArrayList<LogProgram> logPrograms = new ArrayList<>();

        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.logprogram where email = ?");
        pstmt.setString(1, userEmail);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int logId = rs.getInt("logprogramid");
            String email = rs.getString("email");
            int programId = rs.getInt("programid");
            Date date = rs.getDate("date");
            String evaluation = rs.getString("evaluation");

            logPrograms.add(new LogProgram(logId, email, programId, date, evaluation));
        }
        rs.close();
        pstmt.close();
        con.close();

        return logPrograms;
    }

    public ArrayList<AchievementsInfo> selectAllAchievements() throws SQLException{
        ArrayList<AchievementsInfo> achievementsInfoList = new ArrayList<>();

        Connection con = this.getDatabaseConnection();
        String query = "select * from training.achievementsInfo";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            int achievementId = rs.getInt("achievementid");
            String name = rs.getString("name");
            String description = rs.getString("description");

            achievementsInfoList.add(new AchievementsInfo(achievementId, name, description));
        }

        rs.close();
        stmt.close();
        con.close();

        return achievementsInfoList;
    }

    public ArrayList<CompletedAchievement> selectCompletedAchievements(String loggedInMail) throws SQLException{
        ArrayList<CompletedAchievement> completedAchievementsList = new ArrayList<>();

        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("select * from training.completedachievements where email = ?");
        pstmt.setString(1, loggedInMail);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            int achievementId = rs.getInt("achievementid");
            String email = rs.getString("email");
            Date date = rs.getDate("date");

            completedAchievementsList.add(new CompletedAchievement(achievementId, email, date));
        }

        rs.close();
        pstmt.close();
        con.close();

        return completedAchievementsList;
    }

    public String getUsername(String email) throws SQLException{
        String username = null;
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("select username from training.users where email = ?");
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            username = rs.getString("username");
        }

        rs.close();
        pstmt.close();
        con.close();
        return username;
    }

    public int getWorkoutId(String email) throws SQLException {
        int id = 0;
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("select MAX(workoutid) from training.workoutinfo where creatoremail = ?");
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            id = rs.getInt("max");
        }
        rs.close();
        pstmt.close();
        con.close();
        return id;
    }

    public int getLogWorkoutId(String email) throws SQLException {
        int id = 0;
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("select MAX(logworkoutid) from training.logworkout where email = ?");
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            id = rs.getInt("max");
        }
        rs.close();
        pstmt.close();
        con.close();
        return id;
    }

    public int getProgramId(String email) throws SQLException {
        int id = 0;
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("select MAX(programid) from training.programinfo where email = ?");
        pstmt.setString(1, email);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            id = rs.getInt("programid");
        }
        rs.close();
        pstmt.close();
        con.close();
        return id;
    }

    public ExerciseInfo getExerciseInfo(Connection con, int exerciseId, int sets) throws SQLException{
        ExerciseInfo exerciseInfo = null;
        PreparedStatement pstmt = con.prepareStatement("Select * from training.exercise where exerciseid = ?");
        pstmt.setInt(1, exerciseId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            String name = rs.getString("name");
            String description = rs.getString("description");
            String pri = rs.getString("primarymusclegroup");
            String sec = rs.getString("secondarymusclegroup");
            exerciseInfo = new ExerciseInfo(exerciseId, name, description, pri, sec, sets);
        }
        rs.close();
        pstmt.close();
        return exerciseInfo;
    }

    public boolean checkIfCreatorOfWorkout(String email, int workoutId) throws SQLException {
        String temp = null;

        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("select * from training.workoutinfo where creatoremail = ? and workoutid = ?");
        pstmt.setString(1, email);
        pstmt.setInt(2, workoutId);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            temp = rs.getString("name");
        }
        rs.close();
        pstmt.close();
        con.close();

        return temp != null;
    }

    public boolean checkIfCreatorOfProgram(String email, int programId) throws SQLException{
        String temp = null;

        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.programinfo where creatoremail = ? and programid = ?");
        pstmt.setString(1, email);
        pstmt.setInt(2, programId);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            temp = rs.getString("name");
        }
        rs.close();
        pstmt.close();
        con.close();

        return temp != null;
    }

    public void terminateIdle() throws SQLException{
        Connection con = this.getDatabaseConnection();

        String selectPids = "SELECT pid from pg_stat_activity where state = 'idle'";
        Statement getPids = con.createStatement();
        ResultSet rs = getPids.executeQuery(selectPids);

        while (rs.next()){
            PreparedStatement pstmt = con.prepareStatement("Select pg_terminate_backend(?)");
            pstmt.setInt(1, rs.getInt("pid"));
            pstmt.execute();
        }
        rs.close();
        getPids.close();
        con.close();
    }

}
