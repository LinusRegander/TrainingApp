package dbcon;

import HelperClasses.Encrypter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

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
    public void logExerciseSet(int exerciseId, int set, int reps, double weight, String email) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training,logExerciseSet(?,?,?,?,?)");
        pstmt.setInt(1, exerciseId);
        pstmt.setInt(2, set);
        pstmt.setInt(3, reps);
        pstmt.setDouble(4, weight);
        pstmt.setString(5, email);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

    //Ska denna köras automatiskt efter logExerciseSet har exekverats?
    public void logSession(int logExerciseId, int logWorkoutId, int set) throws SQLException{
        Connection con = this.getDatabaseConnection();
        PreparedStatement pstmt = con.prepareStatement("Call training.logSession(?,?,?)");
        pstmt.setInt(1, logExerciseId);
        pstmt.setInt(2, logWorkoutId);
        pstmt.setInt(3, set);

        pstmt.execute();
        pstmt.close();
        con.close();
    }

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







}
