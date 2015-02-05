package com.example.remindme;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private DbAdapter adapter;
    private ExpandableListAdapter mAdapter;
    private ExpandableListView expListView;

    private ArrayList<ListParent> list;
    private int groupPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        adapter = new DbAdapter(this);

        expListView = (ExpandableListView)findViewById(R.id.list);

        list = new ArrayList<ListParent>();
        getListData();
        mAdapter = new ExpandableListAdapter(this, list);

        expListView.setAdapter(mAdapter);
        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long packedPosition = expListView.getExpandableListPosition(position);
                groupPosition = expListView.getPackedPositionGroup(packedPosition);
                int childPosition = expListView.getPackedPositionChild(packedPosition);

                showToast("groupPosition: " +  groupPosition + " childPosition: " + childPosition);
                return false;
            }
        });

        registerForContextMenu(expListView);
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
            case R.id.action_help:
                showToast("Displaying help.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_list_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_alarm:
                showToast("alarm");
                return true;
            case R.id.action_edit:
                showToast("edit");
                return true;
            case R.id.action_delete:
                showToast("delete");
                adapter.deleteReminder(adapter.getReminder(list.get(groupPosition).getId()));
                list.remove(groupPosition);
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    //fill list with data
    private ArrayList<ListParent> getListData() {
        List<Reminder> dbList = adapter.getAllReminders();

        for (int i = 0; i < dbList.size(); i++){
            ListParent parent = new ListParent();
            parent.setId(dbList.get(i).getId());
            parent.setTitle(dbList.get(i).getTitle());
            parent.setDescription(dbList.get(i).getDescription());
            parent.setChildren(new ArrayList<ListChild>());

            ListChild child = new ListChild();
            child.setDescription(parent.getDescription());
            parent.getChildren().add(child);

            list.add(parent);
        }
        //list = new List<ListParent>();
        /*ArrayList<ListParent> list = new ArrayList<ListParent>();

        for (int i = 0; i < adapter.getDatabaseSize(); i++){
            ListParent parent = new ListParent();
            //Log.d("Value", "" + adapter.getReminder(i+1).getId()); //adapter.getReminder(i+1);
            parent.setTitle(adapter.getReminder(i+1).getTitle());
            parent.setChildren(new ArrayList<ListChild>());

            ListChild child = new ListChild();
            child.setDescription(adapter.getReminder(i+1).getDescription());
            parent.getChildren().add(child);

            list.add(parent);
            //id seems to start at 1
        }
        return list;*/
        return list;
    }

    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
