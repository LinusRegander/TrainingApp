package HelperClasses;

public class Achievement {
    private int id;
    private String name;
    private String description;
    private boolean completed;

    public Achievement(int id, String name, String description, boolean completed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public String getStatus(){
        if(completed){
            return "Completed";
        }
        return "Not completed";
    }
}
