package HelperClasses;

public class PlanExerciseSet {
    private int planExerciseId;
    private int exerciseId;
    private int set;
    private int reps;
    private double weight;
    private String email;
    private int planWorkoutId;

    public PlanExerciseSet(int planExerciseId, int exerciseId, int set, int reps, double weight, String email, int planWorkoutId) {
        this.planExerciseId = planExerciseId;
        this.exerciseId = exerciseId;
        this.set = set;
        this.reps = reps;
        this.weight = weight;
        this.email = email;
        this.planWorkoutId = planWorkoutId;
    }

    public int getPlanExerciseId() {
        return planExerciseId;
    }

    public void setPlanExerciseId(int planExerciseId) {
        this.planExerciseId = planExerciseId;
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

    public int getPlanWorkoutId() {
        return planWorkoutId;
    }

    public void setPlanWorkoutId(int planWorkoutId) {
        this.planWorkoutId = planWorkoutId;
    }
}
