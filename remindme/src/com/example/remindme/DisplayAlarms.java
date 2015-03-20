package com.example.remindme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Edward on 6/03/2015.
 * Displays all alarms currently set by the user.
 */
public class DisplayAlarms extends Activity {
    DbAdapter dbAdapter;
    List<Alarm> alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        dbAdapter = new DbAdapter(this);
        alarms = dbAdapter.getAllAlarms();

        AlarmListAdapter listAdapter = new AlarmListAdapter(this, R.layout.alarm_rows, alarms);
        ListView listViewItems = (ListView)findViewById(R.id.listView);
        listViewItems.setAdapter(listAdapter);
    }
}
