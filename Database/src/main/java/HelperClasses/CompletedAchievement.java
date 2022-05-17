package HelperClasses;

import java.util.Date;

public class CompletedAchievement {
    private int achievementId;
    private String email;
    private Date date;

    public CompletedAchievement(int achievementId, String email, Date date) {
        this.achievementId = achievementId;
        this.email = email;
        this.date = date;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
