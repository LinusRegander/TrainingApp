package HelperClasses;

public class ExerciseInfo {
    private int id;
    private String name;
    private String description;
    private String primary;
    private String secondary;

    public ExerciseInfo(int id, String name, String description, String primary, String secondary){
        this.id = id;
        this.name = name;
        this.description = description;
        this.primary = primary;
        this.secondary = secondary;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPrimary() {
        return primary;
    }
    public void setPrimary(String primary) {
        this.primary = primary;
    }
    public String getSecondary() {
        return secondary;
    }
    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }
    public String toString(){
        return name;
    }
}
