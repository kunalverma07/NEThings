package com.nething;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainAdapter extends BaseExpandableListAdapter {

private Context context;
private ArrayList<String> listGroups;
private HashMap<String,ArrayList<String>> listItems;

public MainAdapter(Context context,ArrayList<String> listGroups,HashMap<String, ArrayList<String>> listItems){
    this.context = context;
    this.listGroups = listGroups;
    this.listItems = listItems;

}

    public MainAdapter(ArrayList<String> listGroups,HashMap<String,ArrayList<String>> listItems){
    this.listGroups = listGroups;
    this.listItems = listItems;
    }

    @Override
    public int getGroupCount() {
        return listGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listItems.get(listGroups.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return  listItems.get(listGroups.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return (groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = layoutInflater.inflate(R.layout.list_group,null);
        }
        TextView tvParentTitle = convertView.findViewById(R.id.list_parent);
        tvParentTitle.setText((String) getGroup(groupPosition));
            return convertView;
        }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
            convertView = layoutInflater.inflate(R.layout.list_item,null);
        }
        TextView tvChildTitle = convertView.findViewById(R.id.list_child);
        tvChildTitle.setText((String) getChild(groupPosition, childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


  /*  ArrayList<String> listGroup;
    HashMap<String,ArrayList<String>> listChild;
    private AlertDialog.Builder viewGrouop;

    public MainAdapter(ArrayList<String> listGroup, HashMap<String, ArrayList<String>> listChild) {
        this.listGroup = listGroup;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listChild.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewGroup viewGroup= null;
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(android.R.layout.simple_expandable_list_item_1, viewGroup,false);
        TextView textView = view.findViewById(android.R.id.text1);
        String sGroup = String.valueOf(getGroup(groupPosition));
        textView.setText(sGroup);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.BLUE);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewGroup viewGroup = null;
        View view = LayoutInflater.from(viewGrouop.getContext())
                .inflate(android.R.layout.simple_selectable_list_item, viewGroup, false);
        TextView textView = view.findViewById(android.R.id.text1);
        String sChild = String.valueOf(getChild(groupPosition,childPosition));

        textView.setText(sChild);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(viewGroup.getContext(),sChild, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }*/

  /*  Context context;
    List<String> listGroup;
HashMap<String, List<String>> listItem;

    public MainAdapter(Context context, List<String> listGroup, HashMap<String, List<String>> listItem) {
        this.context = context;
        this.listGroup = listGroup;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return Objects.requireNonNull(listItem.get(listGroup.get(i))).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.listGroup.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.listItem.get(this.listGroup.get(i))
                .get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String group = (String) getGroup(i);
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_group,null);
        }
        TextView textView = view.findViewById(R.id.list_parent);
        textView.setText(group);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String child = (String) getChild(i,i1);
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item,null);
        }
        TextView textView = view.findViewById(R.id.list_child);
        textView.setText(child);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }*/
}
