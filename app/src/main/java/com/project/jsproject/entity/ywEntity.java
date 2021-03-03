package com.project.jsproject.entity;


import java.io.Serializable;



public class ywEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    private int id;

    private String username;

    private String time;

    private String action;

    private String qbs;

    private String ytx;

    private String runscore;

    private String jumpscore;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getQbs() {
        return qbs;
    }

    public void setQbs(String qbs) {
        this.qbs = qbs;
    }

    public String getYtx() {
        return ytx;
    }

    public void setYtx(String ytx) {
        this.ytx = ytx;
    }

    public String getRunscore() {
        return runscore;
    }

    public void setRunscore(String runscore) {
        this.runscore = runscore;
    }

    public String getJumpscore() {
        return jumpscore;
    }

    public void setJumpscore(String jumpscore) {
        this.jumpscore = jumpscore;
    }
}
