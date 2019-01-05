package com.objects;

public class OutfitDisplay {
    private String onlineStatus;
    private String name;
    private String rank;
    private String battleRank;
    private String dateJoined;
    private String lastLogin;
    private String playTime;
    private String killDeathRatio;
    private String scorePerMinute;
    private String killsPerMinute;

    public OutfitDisplay(){}

    public OutfitDisplay(String onlineStatus, String name, String rank, String battleRank, String dateJoined, String lastLogin, String playTime, String killDeathRatio, String scorePerMinute, String killsPerMinute) {
        this.onlineStatus = onlineStatus;
        this.name = name;
        this.rank = rank;
        this.battleRank = battleRank;
        this.dateJoined = dateJoined;
        this.lastLogin = lastLogin;
        this.playTime = playTime;
        this.killDeathRatio = killDeathRatio;
        this.scorePerMinute = scorePerMinute;
        this.killsPerMinute = killsPerMinute;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getBattleRank() {
        return battleRank;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public String getPlayTime() {
        return playTime;
    }

    public String getKillDeathRatio() {
        return killDeathRatio;
    }

    public String getScorePerMinute() {
        return scorePerMinute;
    }

    public String getKillsPerMinute() {
        return killsPerMinute;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setBattleRank(String battleRank) {
        this.battleRank = battleRank;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public void setKillDeathRatio(String killDeathRatio) {
        this.killDeathRatio = killDeathRatio;
    }

    public void setScorePerMinute(String scorePerMinute) {
        this.scorePerMinute = scorePerMinute;
    }

    public void setKillsPerMinute(String killsPerMinute) {
        this.killsPerMinute = killsPerMinute;
    }
}
