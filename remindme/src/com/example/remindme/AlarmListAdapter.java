package com.example.remindme;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Edward on 18/03/2015.
 */
public class AlarmListAdapter extends ArrayAdapter<Alarm> {
    Context context;
    int layoutResourceId;
    List<Alarm> alarms;
    DbAdapter dbAdapter;

    public AlarmListAdapter(Context context, int layoutResourceId, List<Alarm> alarms){
        super(context, layoutResourceId, alarms);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.alarms = alarms;

        dbAdapter = new DbAdapter(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        Alarm alarmItem = alarms.get(position);

        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(alarmItem.getTitle() + "-" + alarmItem.getAlarmTime());

        return convertView;
    }
}
