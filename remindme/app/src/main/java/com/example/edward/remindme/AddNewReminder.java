package com.example.edward.remindme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.edward.remindme.adapters.DatabaseAdapter;
import com.example.edward.remindme.models.Reminder;

/**
 * Created by Edward on 22/03/2015.
 */
public class AddNewReminder extends Fragment {
    private View view;
    private String title, description;
    private DatabaseAdapter dbAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbAdapter = new DatabaseAdapter(getActivity().getBaseContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_new_reminder, container, false);

        Button addReminder = ((Button)view.findViewById(R.id.addButton));
        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewReminder();
            }
        });

        return view;
    }

    private void addNewReminder(){
        title = ((EditText)view.findViewById(R.id.titleEdit)).getText().toString();
        description = ((EditText)view.findViewById(R.id.descriptionEdit)).getText().toString();

        if (title.length() == 0){
            showToast("Please add a title.");
        } else {
            dbAdapter.addReminder(new Reminder(title, description));

            showToast("Added new reminder '" + title +"'");
        }

        /*List<Reminder> reminders = dbAdapter.getAllReminders();

        for (Reminder remind:reminders){
            showToast("ID: " + remind.getId() + ", Title: " + remind.getTitle() +
                    ", Description: " + remind.getDescription() + "\n");
        }*/
    }

    private void showToast(String s){
        Toast.makeText(getActivity().getBaseContext(), s, Toast.LENGTH_SHORT).show();
    }
}
