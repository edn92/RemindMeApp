package com.example.remindme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Edward on 12/12/2014.
 * Adds a new reminder to the database.
 */
public class AddNewReminder extends Activity {
    private String title, description;
    DbAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reminder);

        dbAdapter = new DbAdapter(this);
    }

    public void AddNew(View view){
        title = ((EditText)findViewById(R.id.titleEdit)).getText().toString();
        description = ((EditText)findViewById(R.id.descriptionEdit)).getText().toString();

        if (title.length() == 0){
            showToast("Please add a title.");
        } else {
            dbAdapter.addReminder(new Reminder(title, description));

            showToast("Added new reminder '" + title + "'");
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
    }

    public void Cancel(View view){
        //temporary method for testing purposes
        List<Reminder> reminders = dbAdapter.getAllReminders();

        for (Reminder remind : reminders) {
            showToast("ID: " + remind.getId() + ", Title: " + remind.getTitle() +
            ", Description: " + remind.getDescription() + "\n");
        }
        showToast("cancelling");
    }

    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
