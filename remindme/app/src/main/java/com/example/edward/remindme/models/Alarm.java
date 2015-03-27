package com.example.edward.remindme.models;

/**
 * Created by Edward on 22/03/2015.
 */
public class Alarm {
    private int id, alarmIntentID;
    private String title, alarmTime;

    public Alarm(){

    }

    public Alarm(String title, String alarmTime, int alarmIntentID){
        this.title = title;
        this.alarmTime = alarmTime;
        this.alarmIntentID = alarmIntentID;
    }

    public Alarm(int id, String title, String alarmTime, int alarmIntentID){
        this.id = id;
        this.title = title;
        this.alarmTime = alarmTime;
        this.alarmIntentID = alarmIntentID;
    }

    public int getId(){ return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle(){ return title; }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAlarmTime(){ return alarmTime; }

    public void setAlarmTime(String alarmTime){ this.alarmTime = alarmTime; }

    public int getAlarmIntentID() { return alarmIntentID; }

    public void setAlarmIntentID() { this.alarmIntentID = alarmIntentID; }
}

