package com.example.edward.remindme;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.edward.remindme.adapters.DatabaseAdapter;

import com.example.edward.remindme.adapters.ExpandableListAdapter;
import com.example.edward.remindme.models.Reminder;
import com.example.edward.remindme.models.ReminderChild;
import com.example.edward.remindme.models.ReminderParent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 22/03/2015.
 */
public class ViewReminders extends Fragment {
    private View view;
    private ExpandableListView expListView;
    private ExpandableListAdapter expListAdapter;
    private DatabaseAdapter dbAdapter;
    private int groupPosition;
    private ArrayList<ReminderParent> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbAdapter = new DatabaseAdapter(getActivity().getBaseContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.reminder_listview, container, false);
        expListView = (ExpandableListView)view.findViewById(R.id.expListView);

        list = new ArrayList<ReminderParent>();
        getListData();
        expListAdapter = new ExpandableListAdapter(getActivity().getBaseContext(), list);

        expListView.setAdapter(expListAdapter);
        expListView.setGroupIndicator(null);
        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long packedPosition = expListView.getExpandableListPosition(position);
                groupPosition = expListView.getPackedPositionGroup(packedPosition);
                return false;
            }
        });
        registerForContextMenu(expListView);

        return view;
    }

    //redraws list when this tab is active
    @Override
    public void setMenuVisibility(boolean visible) {
        super.setMenuVisibility(visible);
        if (getActivity() != null){
            if (visible) {
                list.clear();
                getListData();
                expListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_reminder_context, menu);
        menu.setHeaderTitle(list.get(groupPosition).getTitle());

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_alarm:
                showToast("alarm");
                return true;
            case R.id.action_edit:
                editReminderDialog();
                return true;
            case R.id.action_delete:
                dbAdapter.deleteReminder(dbAdapter.getReminder(list.get(groupPosition).getId()));
                list.remove(groupPosition);
                expListAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void editReminderDialog(){
        /*View layout = getActivity().getLayoutInflater().inflate(R.layout.reminder_edit, null);

        final EditText title = (EditText) layout.findViewById(R.id.titleEdit);
        title.setText(list.get(groupPosition).getTitle());

        final EditText description = (EditText) layout.findViewById(R.id.descriptionEdit);
        description.setText(list.get(groupPosition).getDescription());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getBaseContext());
        builder.setView(layout);
        builder.create().show();*/
        showToast("edit");
    }

    private ArrayList<ReminderParent> getListData(){
        List<Reminder> dbList = dbAdapter.getAllReminders();

        for (int i = 0; i < dbList.size(); i++){
            ReminderParent parent = new ReminderParent();
            parent.setId(dbList.get(i).getId());
            parent.setTitle(dbList.get(i).getTitle());
            parent.setDescription(dbList.get(i).getDescription());
            parent.setChildren(new ArrayList<ReminderChild>());

            ReminderChild child = new ReminderChild();
            child.setDescription(parent.getDescription());
            parent.getChildren().add(child);

            list.add(parent);
        }

        return list;
    }

    private void showToast(String s){
        Toast.makeText(getActivity().getBaseContext(), s, Toast.LENGTH_SHORT).show();
    }
}
