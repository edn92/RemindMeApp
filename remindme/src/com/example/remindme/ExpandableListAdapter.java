package com.example.remindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

/**
* Created by Edward on 4/02/2015.
*/
class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Parent> parents;

    public ExpandableListAdapter(Context context, List<Parent> parents){
        this.context = context;
        this.parents = parents;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parentView){
        final Parent parent = parents.get(groupPosition);

        convertView = inflater.inflate(R.layout.parent_rows, parentView, false);

        ((TextView)convertView.findViewById(R.id.titleText)).setText(parent.getTitle());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parentView){
        final Parent parent = parents.get(groupPosition);
        Child child = parent.getChildren().get(childPosition);

        convertView = inflater.inflate(R.layout.child_rows, parentView, false);

        ((TextView)convertView.findViewById(R.id.descriptionText)).setText(child.getDescription());

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        //Log.i("Childs", groupPosition+"=  getChild =="+childPosition);
        return parents.get(groupPosition).getChildren().get(childPosition);
    }


    @Override
    public int getChildrenCount(int groupPosition)
    {
        int size=0;
        if(parents.get(groupPosition).getChildren()!=null)
            size = parents.get(groupPosition).getChildren().size();
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }


    @Override
    public int getGroupCount()
    {
        return parents.size();
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        /*Log.i("Parent", groupPosition+"=  getGroupId "+ParentClickStatus);

        if(groupPosition==2 && ParentClickStatus!=groupPosition){

            //Alert to user
            Toast.makeText(getApplicationContext(), "Parent :"+groupPosition ,
                    Toast.LENGTH_LONG).show();
        }

        ParentClickStatus=groupPosition;
        if(ParentClickStatus==0)
            ParentClickStatus=-1;*/

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged()
    {
        // Refresh List rows
        super.notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty()
    {
        return ((parents == null) || parents.isEmpty());
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }
}
