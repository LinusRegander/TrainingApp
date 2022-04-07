package HelperClasses;

public class LogExerciseSet {
    private int logExerciseId;
    private int exerciseId;
    private int set;
    private int reps;
    private double weight;
    private String email;
    private int logWorkoutId;

    public LogExerciseSet(int logExerciseId, int exerciseId, int set, int reps, double weight, String email, int logWorkoutId) {
        this.logExerciseId = logExerciseId;
        this.exerciseId = exerciseId;
        this.set = set;
        this.reps = reps;
        this.weight = weight;
        this.email = email;
        this.logWorkoutId = logWorkoutId;
    }

    public int getLogExerciseId() {
        return logExerciseId;
    }

    public void setLogExerciseId(int logExerciseId) {
        this.logExerciseId = logExerciseId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLogWorkoutId() {
        return logWorkoutId;
    }

    public void setLogWorkoutId(int logWorkoutId) {
        this.logWorkoutId = logWorkoutId;
    }
}
