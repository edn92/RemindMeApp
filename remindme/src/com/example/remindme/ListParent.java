package com.example.remindme;

import java.util.ArrayList;

/**
 * Created by Edward on 4/02/2015.
 * Model class for Parent rows for expandable list
 */
public class ListParent extends Reminder{
    //private String title;
    //private String description;
    private ArrayList<ListChild> children;

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

    public ArrayList<ListChild> getChildren(){
        return children;
    }

    public void setChildren(ArrayList<ListChild> children){
        this.children = children;
    }
}
