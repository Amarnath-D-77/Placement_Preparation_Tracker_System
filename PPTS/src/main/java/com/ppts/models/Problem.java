package com.ppts.models;

import java.sql.Date;

public class Problem {
    private int id;
    private int userId;
    private String problemName;
    private String topic;
    private String platform;
    private String difficulty;
    private String language;
    private Date solvedDate;

    public Problem() {}

    public Problem(int id, int userId, String problemName, String topic, String platform, String difficulty, String language, Date solvedDate) {
        this.id = id;
        this.userId = userId;
        this.problemName = problemName;
        this.topic = topic;
        this.platform = platform;
        this.difficulty = difficulty;
        this.language = language;
        this.solvedDate = solvedDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getProblemName() { return problemName; }
    public void setProblemName(String problemName) { this.problemName = problemName; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public Date getSolvedDate() { return solvedDate; }
    public void setSolvedDate(Date solvedDate) { this.solvedDate = solvedDate; }
    
    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-25s | Topic: %-15s | Platform: %-12s | Difficulty: %-8s | Lang: %-6s | Date: %s",
                id, problemName, topic, platform, difficulty, language, solvedDate);
    }
}
