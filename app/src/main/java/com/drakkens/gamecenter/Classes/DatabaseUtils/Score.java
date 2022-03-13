package com.drakkens.gamecenter.Classes.DatabaseUtils;

public class Score {
    private int score;
    private int time;
    private String user;
    private String date;

    public Score(String user, int score, int time, String date) {
        this.user = user;
        this.score = score;
        this.time = time;
        this.date = date;


    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }
}
