package HelperClasses;

public class WorkoutInfo {
    private int id;
    private String name;
    private String creatorEmail;
    private String description;
    private String tag1;
    private String tag2;
    private String tag3;

    public WorkoutInfo(int id, String name, String creatorEmail, String description, String tag1, String tag2, String tag3){
        this.name = name;
        this.creatorEmail = creatorEmail;
        this.description = description;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.tag3 = tag3;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCreatorEmail() {
        return creatorEmail;
    }
    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTag1() {
        return tag1;
    }
    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }
    public String getTag2() {
        return tag2;
    }
    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }
    public String getTag3() {
        return tag3;
    }
    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }
}