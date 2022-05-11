package HelperClasses;

public class Set {
    private int reps;
    private double weight;

    public Set(int reps, double weight) {
        this.reps = reps;
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Set{" +
                "reps=" + reps +
                ", weight=" + weight +
                '}';
    }
}
