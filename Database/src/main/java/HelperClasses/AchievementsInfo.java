package HelperClasses;

public class AchievementsInfo {
    private int achievementId;
    private String name;
    private String description;

    public AchievementsInfo(int achievementId, String name, String description) {
        this.achievementId = achievementId;
        this.name = name;
        this.description = description;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
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
}
