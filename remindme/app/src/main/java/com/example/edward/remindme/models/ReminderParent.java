package com.example.edward.remindme.models;

import java.util.ArrayList;

/**
 * Created by Edward on 22/03/2015.
 */
public class ReminderParent extends Reminder{
    private ArrayList<ReminderChild> children;

    public ArrayList<ReminderChild> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ReminderChild> children) {
        this.children = children;
    }
}
