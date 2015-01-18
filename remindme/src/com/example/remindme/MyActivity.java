package com.example.remindme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    DbAdapter adapter;
    ListViewAdapter listAdapter;
    List<Reminder> reminds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        adapter = new DbAdapter(this);
        this.reminds = adapter.getAllReminders();

        listAdapter = new ListViewAdapter(this, R.layout.listview_row, reminds);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetailedDialog(position);
            }
        });
        registerForContextMenu(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_new:
                Intent i = new Intent(this, NewReminder.class);
                startActivity(i);
                return true;
            case R.id.action_delete:
                showToast("Deleting all marked memos.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDetailedDialog(final int i){
        View layout = getLayoutInflater().inflate(R.layout.details_dialog, null);

        //binding title and description to textviews
        TextView descriptionTextView = (TextView) layout.findViewById(R.id.descriptionTextView) ;
        descriptionTextView.setText(reminds.get(i).getDescription());
        TextView titleTextView = (TextView) layout.findViewById(R.id.titleTextView);
        titleTextView.setText(reminds.get(i).getTitle());

        Button deleteButton = (Button) layout.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(i);
            }
        });

        Button setAlarmButton = (Button) layout.findViewById(R.id.setAlarmButton);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmDialog();
            }
        });
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.create().show();
    }

    private void showConfirmationDialog(int i){
        showToast("" + i);
    }

    private void alarmDialog(){

    }
    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
