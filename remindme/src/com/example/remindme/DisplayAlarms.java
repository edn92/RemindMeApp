package com.example.remindme;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Edward on 6/03/2015.
 * Displays all alarms currently set by the user.
 */
public class DisplayAlarms extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
    }
}
