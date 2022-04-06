package dbcon;

import HelperClasses.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Services {
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
        String loginMail = "";
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.users where email = ? and password = ?");
        pstmt.setString(1, email);
        pstmt.setString(2, Encrypter.encrypt(password));
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()){
            loginMail = rs.getString("email");
        }
        pstmt.close();
        rs.close();
        con.close();

        return loginMail;
    }

    public boolean checkIfEmailExists(String email) throws SQLException{
        boolean exists = false;
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.users where email = ?");
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
        PreparedStatement pstmt = con.prepareStatement("Select * from training.users where name = ?");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()){
            String userMail = rs.getString("name");
            if(userMail!=null){
                exists = true;
            }
        }

        return exists;
    }


//nedan är alla inserts
    public void insertNewUser(String email, String name, String password) throws SQLException {
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("call training.insertNewUser(?,?,?)");
        pstmt.setString(1, email);
        pstmt.setString(2, name);
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
    public void insertExerciseInToWorkout(int exerciseId, int workoutId) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("call training.insertExerciseInToWorkout(?,?)");
        pstmt.setInt(1, exerciseId);
        pstmt.setInt(2, workoutId);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    //creatorEmail == email som man är inloggad på
    public void insertNewWorkout(String name, String creatorEmail, String description, String tag1, String tag2, String tag3) throws SQLException{
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
    }

    //creatorEmail == email som man är inloggad på
    public void insertNewProgram(String name, String creatorEmail, String description, String tag1, String tag2, String tag3) throws SQLException{
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
    }

    //TODO: lägg till en check så att dessa PK redan finns.
    //TODO: Lägg till check på program så och kolla med inloggade creatorEmail och se så att de matchar. Annars får de inte ändra
    public void insertWorkoutInToProgram(int programId, int workoutId) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertWorkoutInToProgram(?,?)");
        pstmt.setInt(1, programId);
        pstmt.setInt(1, workoutId);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    //email == inloggade email
    //TODO: lägg till check på PK så att de finns.
    public void logExerciseSet(int exerciseId, int set, int reps, double weight, String email, int logWorkoutId) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training,logExerciseSet(?,?,?,?,?,?)");
        pstmt.setInt(1, exerciseId);
        pstmt.setInt(2, set);
        pstmt.setInt(3, reps);
        pstmt.setDouble(4, weight);
        pstmt.setString(5, email);
        pstmt.setInt(6, logWorkoutId);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    //Ska denna köras automatiskt efter logExerciseSet har exekverats?

    public void insertNewLogworkout(String email, int workoutid, Date date) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.insertNewLogWorkout(?,?,?)");
        pstmt.setString(1, email);
        pstmt.setInt(2, workoutid);
        pstmt.setDate(3, date);
        pstmt.execute();
        pstmt.close();
        con.close();
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
    //TODO: Ska man kunna göra "egna exercises? Cause isåfall behöver vi lägga till i db creator
    public void updateExercise(int exerciseId, String loggedInMail) throws SQLException{

    }


    public void updateWorkout(int workoutId, String loggedInMail) throws SQLException{

    }

    public void updateProgram(int programId, String loggedInMail) throws SQLException{

    }

    public void updateLogExerciseSet(int logExerciseId, String loggedInMail) throws SQLException{

    }

    public void updateLogWorkout(int logWorkoutId, String loggedInMail) throws SQLException{

    }

    public void updateLogProgram(int logProgramId, String loggedInMail) throws SQLException{

    }


// nedan är alla select statements

    public ArrayList<Exercise> selectExercises() throws SQLException {
        ArrayList<Exercise> exercises = new ArrayList<>();
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.exercise");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int id = rs.getInt("exerciseid");
            String name = rs.getString("name");
            String des = rs.getString("description");
            String pri = rs.getString("primarymusclegroup");
            String sec = rs.getString("secondarymusclegroup");

            exercises.add(new Exercise(id, name, des, pri, sec));
        }
        pstmt.close();
        rs.close();
        con.close();

        return exercises;
    }

    public ArrayList<WorkoutInfo> selectWorkoutInfo() throws SQLException {
        ArrayList<WorkoutInfo> workoutInfos = new ArrayList<>();
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.workoutinfo");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int id = rs.getInt("workoutid");
            String name = rs.getString("name");
            String creatorEmail = rs.getString("creatoremail");
            String description = rs.getString("description");
            String tag1 = rs.getString("tag_1");
            String tag2 = rs.getString("tag_2");
            String tag3 = rs.getString("tag_3");

            workoutInfos.add(new WorkoutInfo(id, name, creatorEmail, description, tag1, tag2, tag3));
        }
        pstmt.close();
        rs.close();
        con.close();

        return workoutInfos;
    }

    public ArrayList<ProgramInfo> selectProgramInfo() throws SQLException {
        ArrayList<ProgramInfo> programInfos = new ArrayList<>();
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Select * from training.programinfo");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            int id = rs.getInt("programid");
            String name = rs.getString("name");
            String creator = rs.getString("creator");
            String description = rs.getString("description");
            String tag1 = rs.getString("tag1");
            String tag2 = rs.getString("tag2");
            String tag3 = rs.getString("tag3");

            programInfos.add(new ProgramInfo(id, name, creator, description, tag1, tag2, tag3));
        }
        pstmt.close();
        rs.close();
        con.close();

        return programInfos;
    }

    public ArrayList<ExerciseSet> selectExerciseSet(String userEmail) throws SQLException{
        ArrayList<ExerciseSet> exerciseSets = new ArrayList<>();
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
            int rep = rs.getInt("rep");
            double weight = rs.getDouble("weight");

            exerciseSets.add(new ExerciseSet(setId, exerciseId, workoutId, email, set, rep, weight));
        }
        pstmt.close();
        rs.close();
        con.close();

        return exerciseSets;
    }

    public ArrayList<LogWorkout> selecetLogWorkout(String userEmail) throws SQLException{
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

            logWorkouts.add(new LogWorkout(logId, workoutId, email, date));
        }
        pstmt.close();
        rs.close();
        con.close();

        return logWorkouts;
    }


}
