package HelperClasses;

import java.util.ArrayList;

public class Exercise {
    private ArrayList<Set> sets;
    private String name;
    private int id;

    public Exercise(String name, int id){
        sets = new ArrayList<>();
        this.name = name;
        this.id = id;
    }
    public Exercise(String name, int id, int setCount){
        this.name = name;
        this.id = id;
        sets = new ArrayList<>();
        for(int i = 0; i < setCount; i++){
            Set set = new Set(0, 0.0);
            sets.add(set);
        }
    }

    public ArrayList<Set> getSets() {
        return sets;
    }
    public int getSetSize(){
        return sets.size();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "sets=" + sets +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
