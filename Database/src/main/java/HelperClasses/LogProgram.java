package HelperClasses;

import java.util.Date;

public class LogProgram {
    private int logProgramId;
    private String email;
    private int programId;
    private Date date;
    private String evaluation;

    public LogProgram(int logProgramId, String email, int programId, Date date, String evaluation) {
        this.logProgramId = logProgramId;
        this.email = email;
        this.programId = programId;
        this.date = date;
        this.evaluation = evaluation;
    }

    public int getLogProgramId() {
        return logProgramId;
    }

    public void setLogProgramId(int logProgramId) {
        this.logProgramId = logProgramId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
