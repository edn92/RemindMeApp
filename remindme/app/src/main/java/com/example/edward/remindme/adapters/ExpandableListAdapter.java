package com.example.edward.remindme.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.edward.remindme.R;
import com.example.edward.remindme.models.ReminderChild;
import com.example.edward.remindme.models.ReminderParent;

import java.util.List;

/**
 * Created by Edward on 26/03/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<ReminderParent> parents;

    public ExpandableListAdapter(Context context, List<ReminderParent> parents){
        this.context = context;
        this.parents = parents;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parentView) {
        ReminderParent parent = parents.get(groupPosition);

        convertView = inflater.inflate(R.layout.reminder_parent_rows, parentView, false);

        ((TextView)convertView.findViewById(R.id.titleText)).setText(parent.getTitle());
        ((TextView)convertView.findViewById(R.id.descriptionText)).setText(parent.getDescription());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentView) {
        ReminderParent parent = parents.get(groupPosition);
        ReminderChild child = parent.getChildren().get(childPosition);

        convertView = inflater.inflate(R.layout.reminder_child_rows, parentView, false);

        ((TextView)convertView.findViewById(R.id.descriptionText)).setText(child.getDescription());

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return parents.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = 0;
        if(parents.get(groupPosition).getChildren()!=null)
            size = parents.get(groupPosition).getChildren().size();
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return ((parents == null) || parents.isEmpty());
    }

    @Override
    public int getGroupCount() {
        return parents.size();
    }

    @Override
    public int getGroupTypeCount() {
        return super.getGroupTypeCount();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }


}
