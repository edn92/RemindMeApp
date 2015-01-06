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
 */
public class NewReminder extends Activity {
    private String title, description;
    DbAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reminder);

        adapter = new DbAdapter(this);
    }

    public void AddNew(View view){
        description = ((EditText)findViewById(R.id.description_editText)).getText().toString();
        title = ((EditText)findViewById(R.id.title_editText)).getText().toString();

        if (title.length() == 0){
            showToast("Please add a title.");
        } else {
            adapter.addReminder(new Reminder(title, description));

            showToast("Added new reminder '" + title + "'");
            Intent i = new Intent(this, MyActivity.class);
            startActivity(i);
        }
    }

    public void Cancel(View view){
        List<Reminder> reminders = adapter.getAllReminders();

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
