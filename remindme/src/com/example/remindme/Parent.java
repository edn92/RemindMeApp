package com.example.remindme;

import java.util.ArrayList;

/**
 * Created by Edward on 4/02/2015.
 * Model class for Parent rows for expandable list
 * Extends Reminder class because only addition is Child arrays
 */
public class Parent extends Reminder{
    private ArrayList<Child> children;

    /*public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }*/

    public ArrayList<Child> getChildren(){
        return children;
    }

    public void setChildren(ArrayList<Child> children){
        this.children = children;
    }
}
