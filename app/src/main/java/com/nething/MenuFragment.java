package com.nething;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

public class MenuFragment extends Fragment {

    ExpandableListView expandableListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        expandableListView = view.findViewById(R.id.expandableListview);

        ArrayList<String> listGroups = new ArrayList<>();
        listGroups.add("Home Service");
        listGroups.add("Cleaning Service");
        listGroups.add("Salon Service");
        listGroups.add("Gadget-Repair Service");
        listGroups.add("24*7 Service");

        HashMap<String, ArrayList<String>> listItems = new HashMap<>();
        ArrayList<String> childItems1 = new ArrayList<>();
        childItems1.add("Electrician");
        childItems1.add("Plumber");
        childItems1.add("Carpenter");
        childItems1.add("Mechanics");
        ArrayList<String> childItems2 = new ArrayList<>();
        childItems2.add("Maid");
        childItems2.add("Home Cleaning");
        childItems2.add("Laundry");
        ArrayList<String> childItems3 = new ArrayList<>();
        childItems3.add("Salon-Men");
        childItems3.add("Salon-Women");
        ArrayList<String> childItems4 = new ArrayList<>();
        childItems4.add("Mobile & Laptop");
        childItems4.add("Electronics");
        childItems4.add("Television & Radio");
        childItems4.add("AC & Fan");
        ArrayList<String> childItems5 = new ArrayList<>();
        childItems5.add("Service-Boy 24*7");
        childItems5.add("Rental Service");


        listItems.put(listGroups.get(0), childItems1);
        listItems.put(listGroups.get(1), childItems2);
        listItems.put(listGroups.get(2), childItems3);
        listItems.put(listGroups.get(3), childItems4);
        listItems.put(listGroups.get(4), childItems5);

        MainAdapter customExpandableAdapter = new MainAdapter(getContext(),listGroups,listItems);
        expandableListView.setAdapter(customExpandableAdapter);

        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {

            Toast.makeText(getContext(), listGroups.get(groupPosition)+" Clicked", Toast.LENGTH_SHORT).show();

            return false;
        });

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            Toast.makeText(getContext(), listItems.get(listGroups.get(groupPosition)).get(childPosition)+" Clicked", Toast.LENGTH_SHORT).show();
            if (childPosition==0 && groupPosition==0){
                Toast.makeText(getContext(), "electrician", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), ElectricianActivity.class));
            }
            if (childPosition==1 && groupPosition==0){
                startActivity(new Intent(getContext(), PlumberActivity.class));
            }
            if (childPosition==2 && groupPosition==0){
                startActivity(new Intent(getContext(), CarpenterActivity.class));
            }
            if (childPosition==3 && groupPosition==0){
                startActivity(new Intent(getContext(), MechanicActivity.class));
            }
            if (childPosition==0 && groupPosition==1){
                startActivity(new Intent(getContext(), MaidServiceActivity.class));
            }
            if (childPosition==1 && groupPosition==1){
                startActivity(new Intent(getContext(), HomeActivity.class));
            }
            if (childPosition==2 && groupPosition==1){
                startActivity(new Intent(getContext(), LaundryActivity.class));
            }
            if (childPosition==0 && groupPosition==2){
                startActivity(new Intent(getContext(), SalonMenActivity.class));
            }
            if (childPosition==1 && groupPosition==2){
                startActivity(new Intent(getContext(), SalonWomenAcitivity.class));
            }
            if (childPosition==0 && groupPosition==3){
                startActivity(new Intent(getContext(), MobileLaptop.class));
            }
            if (childPosition==1 && groupPosition==3){
                startActivity(new Intent(getContext(), Electronics.class));
            }
            if (childPosition==2 && groupPosition==3){
                startActivity(new Intent(getContext(), TelevisionRadio.class));
            }
            if (childPosition==3 && groupPosition==3){
                startActivity(new Intent(getContext(), AcFan.class));
            }
            if (childPosition==0 && groupPosition==4){
                startActivity(new Intent(getContext(), ServiceBoy.class));
            }


            return false;
        });



        return view;
    }

}


   /* List<String> listGroup;
    HashMap<String,List<String>> listItem;
    MainAdapter categoryListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        expandableListView = view.findViewById(R.id.expandableListview);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        categoryListAdapter = new MainAdapter(getContext(),listGroup,listItem);
        expandableListView.setAdapter(categoryListAdapter);
        initListData();
        return view;
    }

    private void initListData() {
        listGroup.add(getString(R.string.group1));
        listGroup.add(getString(R.string.group2));
        listGroup.add(getString(R.string.group3));
        listGroup.add(getString(R.string.group4));
        listGroup.add(getString(R.string.group5));

        String[] array;

        List<String> list1 = new ArrayList<>();
        array =  getResources().getStringArray(R.array.group1);
        for (String item : array){
            list1.add(item);
        }

        List<String> list2 = new ArrayList<>();
        array =  getResources().getStringArray(R.array.group2);
        for (String item : array){
            list2.add(item);
        }

        List<String> list3 = new ArrayList<>();
        array =  getResources().getStringArray(R.array.group3);
        for (String item : array){
            list3.add(item);
        }

        List<String> list4 = new ArrayList<>();
        array =  getResources().getStringArray(R.array.group4);
        for (String item : array){
            list4.add(item);
        }

        List<String> list5 = new ArrayList<>();
        array =  getResources().getStringArray(R.array.group5);
        for (String item : array){
            list5.add(item);
        }

        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list3);
        listItem.put(listGroup.get(3),list4);
        listItem.put(listGroup.get(4),list5);
        categoryListAdapter.notifyDataSetChanged();
    }*/
