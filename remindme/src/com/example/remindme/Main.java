package com.example.remindme;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        setContentView(R.layout.listview);

        dbAdapter = new DbAdapter(this);

        listView = (ExpandableListView)findViewById(R.id.list);

        list = new ArrayList<Parent>();
        getListData();
        listAdapter = new ExpandableListAdapter(this, list);

        listView.setAdapter(listAdapter);
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
                Intent i = new Intent(this, AddNewReminder.class);
                startActivity(i);
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
                showToast("alarm");
                return true;
            case R.id.action_edit:
                showToast("edit");
                editReminderDialog();
                return true;
            case R.id.action_delete:
                showToast("delete");
                dbAdapter.deleteReminder(dbAdapter.getReminder(list.get(groupPosition).getId()));
                list.remove(groupPosition);
                listAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
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
