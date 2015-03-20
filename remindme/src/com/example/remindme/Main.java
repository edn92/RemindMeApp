package com.example.remindme;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.*;

public class Main extends Activity {
    /**
     * Called when the activity is first created.
     */
    private DbAdapter dbAdapter;
    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;

    private ArrayList<Parent> list;
    private int groupPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exp_listview);

        dbAdapter = new DbAdapter(this);

        listView = (ExpandableListView)findViewById(R.id.list);

        list = new ArrayList<Parent>();
        getListData();
        listAdapter = new ExpandableListAdapter(this, list);

        listView.setAdapter(listAdapter);

        //hiding default arrow temporarily
        listView.setGroupIndicator(null);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //retrieves position of clicked element so that database id can be obtained
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long packedPosition = listView.getExpandableListPosition(position);
                groupPosition = listView.getPackedPositionGroup(packedPosition);

                return false;
            }
        });

        registerForContextMenu(listView);
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
                Intent newReminderIntent = new Intent(this, AddNewReminder.class);
                startActivity(newReminderIntent);
                return true;
            case R.id.view_alarms:
                Intent displayAlarmsIntent = new Intent(this, DisplayAlarms.class);
                startActivity(displayAlarmsIntent);
                return true;
            case R.id.action_sortTitle:
                sortByTitle();
                return true;
            case R.id.action_help:
                showToast("Displaying help.");
                //TODO make help screen fragment, temporarily being used to test other methods

                sortByDescription();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sortByTitle() {
        Collections.sort(list, new Comparator<Parent>() {
            @Override
            public int compare(Parent parent1, Parent parent2) {
                return parent1.getTitle().compareTo(parent2.getTitle());
            }
        });
        listAdapter.notifyDataSetChanged();
    }

    private void sortByDescription() {
        Collections.sort(list, new Comparator<Parent>() {
            @Override
            public int compare(Parent parent1, Parent parent2) {
                return parent1.getDescription().compareTo(parent2.getDescription());
            }
        });
        listAdapter.notifyDataSetChanged();
    }

    private void sortByUrgency(){
        //TODO method
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_list_context, menu);
        menu.setHeaderTitle(list.get(groupPosition).getTitle());

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_alarm:
                setAlarm();
                return true;
            case R.id.action_edit:
                editReminderDialog();
                return true;
            case R.id.action_delete:
                dbAdapter.deleteReminder(dbAdapter.getReminder(list.get(groupPosition).getId()));
                list.remove(groupPosition);
                listAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void setAlarm(){
        View layout = getLayoutInflater().inflate(R.layout.set_alarm, null);

        final TimePicker timePicker = (TimePicker) layout.findViewById(R.id.timePicker);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setTitle("Setting reminder for: " + list.get(groupPosition).getTitle());
        builder.setPositiveButton("Set Alarm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                AlarmManager alarmManager;
                PendingIntent alarmIntent;

                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(Main.this, AlarmReceiver.class);
                intent.putExtra("ID", list.get(groupPosition).getId());
                intent.putExtra("title", list.get(groupPosition).getTitle());
                intent.putExtra("description", list.get(groupPosition).getDescription());

                alarmIntent = PendingIntent.getBroadcast(Main.this, intent.getExtras().getInt("ID") , intent, 0);

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

                String s;
                if (hour < 10){
                    s = "Alarm set to 0" + hour + ":" + minute;
                } else {
                    s = "Alarm set to " + hour + ":" + minute;
                }

                showToast(s);
                //add to the alarm table in database
                dbAdapter.addAlarm(new Alarm(intent.getExtras().getString("title"),
                        s, intent.getExtras().getInt("ID")));
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        });
        builder.create().show();
    }

    //TODO method - dialog fragment that takes title and description into editboxes
    //takes id to update in database
    private void editReminderDialog(){
        View layout = getLayoutInflater().inflate(R.layout.edit_reminder, null);

        final EditText description = (EditText) layout.findViewById(R.id.descriptionEdit);
        description.setText(list.get(groupPosition).getDescription());

        final EditText title = (EditText) layout.findViewById(R.id.titleEdit);
        String s = list.get(groupPosition).getTitle();
        title.setText(s);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setTitle("Editing " + s);
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.get(groupPosition).setTitle(title.getText().toString());
                list.get(groupPosition).setDescription(description.getText().toString());
                list.get(groupPosition).getChildren().get(0).setDescription(description.getText().toString());
                dbAdapter.updateReminder(list.get(groupPosition));

                listAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    //fill list with data
    private ArrayList<Parent> getListData() {
        List<Reminder> dbList = dbAdapter.getAllReminders();

        for (int i = 0; i < dbList.size(); i++){
            Parent parent = new Parent();
            parent.setId(dbList.get(i).getId());
            parent.setTitle(dbList.get(i).getTitle());
            parent.setDescription(dbList.get(i).getDescription());
            parent.setChildren(new ArrayList<Child>());

            Child child = new Child();
            child.setDescription(parent.getDescription());
            parent.getChildren().add(child);

            list.add(parent);
        }

        return list;
    }

    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
