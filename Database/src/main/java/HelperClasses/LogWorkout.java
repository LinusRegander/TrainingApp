package HelperClasses;

import java.sql.Date;

public class LogWorkout {
    private int logWorkoutId;
    private int workoutId;
    private String name;
    private Date date;

    public LogWorkout(int logWorkoutId, int workoutId, String name, Date date){
        this.logWorkoutId = logWorkoutId;
        this.workoutId = workoutId;
        this.name = name;
        this.date = date;
    }

    public int getLogWorkoutId() {
        return logWorkoutId;
    }
    public void setLogWorkoutId(int logWorkoutId) {
        this.logWorkoutId = logWorkoutId;
    }
    public int getWorkoutId() {
        return workoutId;
    }
    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
