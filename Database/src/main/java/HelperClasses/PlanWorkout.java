package HelperClasses;

import java.sql.Date;

public class PlanWorkout {
    private int planWorkoutId;
    private int workoutId;
    private String creator;
    private Date date;

    public PlanWorkout(int planWorkoutId, int workoutId, String name, Date date){
        this.planWorkoutId = planWorkoutId;
        this.workoutId = workoutId;
        this.creator = name;
        this.date = date;
    }

    public int getPlanWorkoutId() {
        return planWorkoutId;
    }
    public void setPlanWorkoutId(int planWorkoutId) {
        this.planWorkoutId = planWorkoutId;
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
}
