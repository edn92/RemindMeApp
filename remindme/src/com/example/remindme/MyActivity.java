package com.example.remindme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        //listAdapter = new ListViewAdapter(this, R.layout.listview_row, adapter.getAllReminders());

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast("selected " + position);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.add(0, 0, 0, "Edit");
        menu.add(0, 1, 1, "Set Alarm");
        menu.add(0, 2, 2, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                showToast("selected: " + menuInfo.position);
                break;
            case 1:
                showToast("adding: " + menuInfo.position);
                break;
            case 2:
                showToast("deleted: " + menuInfo.position);
                adapter.deleteReminder(reminds.get(menuInfo.position));
                //listAdapter.refresh(adapter.getAllReminders());
                //listAdapter.notifyDataSetChanged();
                //adapter.deleteReminder(adapter.getReminder(menuInfo.position));
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        listAdapter.refresh(adapter.getAllReminders());
    }

    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
