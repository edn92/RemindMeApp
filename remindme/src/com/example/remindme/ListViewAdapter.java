package com.example.remindme;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Edward on 8/01/2015.
 */
public class ListViewAdapter extends ArrayAdapter<Reminder> {
    Context context;
    int layoutResourceId;
    List<Reminder> reminds;
    DbAdapter adapter;

    public ListViewAdapter(Context context, int layoutResourceId, List<Reminder> reminds) {
        super(context, layoutResourceId, reminds);
        adapter = new DbAdapter(context);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.reminds = reminds;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        Reminder remindItem = reminds.get(position);

        TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
        titleTextView.setText(remindItem.getTitle());

        TextView descriptionTextView = (TextView) convertView.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(remindItem.getDescription());

        /*ImageView alarmImage = (ImageView) convertView.findViewById(R.id.alarmIcon);
        alarmImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "setting", Toast.LENGTH_LONG).show();
            }
        });

        ImageView deleteImage = (ImageView) convertView.findViewById(R.id.deleteIcon);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast.makeText(context, "deleting", Toast.LENGTH_LONG).show();
                //dbAdapter.getReminder(position).getTitle();
                adapter.deleteReminder(reminds.get(position));
                notifyDataSetChanged();
                //refresh(adapter.getAllReminders());
                //adapter.getAllReminders();
            }
        });*/

        return convertView;
    }
}
