package com.example.remindme;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private DbAdapter adapter;
    private ExpandableListAdapter mAdapter;
    private ExpandableListView expListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        adapter = new DbAdapter(this);

        expListView = (ExpandableListView)findViewById(R.id.list);

        mAdapter = new ExpandableListAdapter(this, getListData());

        expListView.setAdapter(mAdapter);
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

    private ArrayList<ListParent> getListData() {
        ArrayList<ListParent> list = new ArrayList<ListParent>();

        for (int i = 0; i < adapter.getDatabaseSize(); i++){
            ListParent parent = new ListParent();
            parent.setTitle(adapter.getReminder(i+1).getTitle());
            parent.setChildren(new ArrayList<ListChild>());

            ListChild child = new ListChild();
            child.setDescription(adapter.getReminder(i+1).getDescription());
            parent.getChildren().add(child);

            list.add(parent);
            //id seems to start at 1
        }
        return list;
    }

    private void showToast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
