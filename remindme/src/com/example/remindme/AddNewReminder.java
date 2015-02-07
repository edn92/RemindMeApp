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
    private String mTitle, mDescription;
    DbAdapter mDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reminder);

        mDBAdapter = new DbAdapter(this);
    }

    public void AddNew(View view){
        mTitle = ((EditText)findViewById(R.id.titleEdit)).getText().toString();
        mDescription = ((EditText)findViewById(R.id.descriptionEdit)).getText().toString();

        if (mTitle.length() == 0){
            showToast("Please add a mTitle.");
        } else {
            mDBAdapter.addReminder(new Reminder(mTitle, mDescription));

            showToast("Added new reminder '" + mTitle + "'");
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
    }

    public void Cancel(View view){
        //temporary method for testing purposes
        List<Reminder> reminders = mDBAdapter.getAllReminders();

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
