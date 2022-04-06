package HelperClasses;

public class ExerciseSet {
    private int exerciseSetId;
    private int exerciseId;
    private int logWorkoutId;
    private String email;
    private int set;
    private int rep;
    private double weight;

    public ExerciseSet(int exerciseSetId, int exerciseId, int logWorkoutId, String email, int set, int rep, double weight){
        this.exerciseSetId = exerciseSetId;
        this.exerciseId = exerciseId;
        this.logWorkoutId = logWorkoutId;
        this.email = email;
        this.set = set;
        this.rep = rep;
        this.weight = weight;
    }


    public int getExerciseSetId() {
        return exerciseSetId;
    }
    public void setExerciseSetId(int exerciseSetId) {
        this.exerciseSetId = exerciseSetId;
    }
    public int getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
    public int getLogWorkoutId() {
        return logWorkoutId;
    }
    public void setLogWorkoutId(int logWorkoutId) {
        this.logWorkoutId = logWorkoutId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getSet() {
        return set;
    }
    public void setSet(int set) {
        this.set = set;
    }
    public int getRep() {
        return rep;
    }
    public void setRep(int rep) {
        this.rep = rep;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
}
