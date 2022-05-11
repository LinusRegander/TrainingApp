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

    public ArrayList<Set> getSets() {
        return sets;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
