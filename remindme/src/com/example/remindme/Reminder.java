package com.example.remindme;

/**
 * Created by Edward on 5/01/2015.
 * Database model class
 */
public class Reminder {
    private int id;
    private String title, description;

    public Reminder(){

    }

    //used for creating new reminders since idPK is auto-assigned
    public Reminder(String title, String description) {
        this.title = title;
        this.description = description;
    }

    //used for database retrieval from cursor
    public Reminder(int id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
