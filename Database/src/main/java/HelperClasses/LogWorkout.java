package HelperClasses;

import com.codename1.l10n.SimpleDateFormat;

import java.util.Date;

public class LogWorkout {
    private int logWorkoutId;
    private int workoutId;
    private String creator;
    private Date date;
    private String evaluation;

    public LogWorkout(int logWorkoutId, int workoutId, String name, Date date, String evaluation){
        this.logWorkoutId = logWorkoutId;
        this.workoutId = workoutId;
        this.creator = name;
        this.date = date;
        this.evaluation = evaluation;
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
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
