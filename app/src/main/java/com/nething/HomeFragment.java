package com.nething;

import static com.google.firebase.storage.FirebaseStorage.getInstance;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationProvider;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nething.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Spinner spinnerLocation;
    FragmentHomeBinding binding;
    private static final String TAG = "HomeFragment";
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String userId, checkUser="user", selected_location;
    TextView seemore,searchBar;
    ArrayList<String> arrayList;
    ArrayList<ServiceProviders> list;
    Dialog dialog;
    ActivityLopAdapter adapter;
    RecyclerView recyclerView;
    TextView clearLogbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentHomeBinding.inflate(inflater, container, false);
       View view = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.call_log_rcyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        clearLogbtn = view.findViewById(R.id.clear_logbtn);

        list = new ArrayList<>();
        adapter = new ActivityLopAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
        EventChangeListener();

        spinnerLocation = view.findViewById(R.id.location);
        String[] location ={"Guwahati","Jorhat","Sivsagar","Dibrugarh","Tinsukia"};
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,location);
        spinnerLocation.setAdapter(adapter);
        seemore = view.findViewById(R.id.seeMore);
        searchBar = view.findViewById(R.id.search_bar);
        arrayList = new ArrayList<>();
        arrayList.add("Electrician");
        arrayList.add("Plumber");
        arrayList.add("Carpenter");
        arrayList.add("Mechanic");
        arrayList.add("Maid");
        arrayList.add("Home Cleaning");
        arrayList.add("Laundry");
        arrayList.add("Rental");
        arrayList.add("Salon-Men");
        arrayList.add("Salon-Women");
        arrayList.add("Mobile-Laptop");
        arrayList.add("Electronics");
        arrayList.add("Television-Radio");
        arrayList.add("Ac-Fan");
        arrayList.add("Service-Boy");

        searchBar.setOnClickListener(v -> {

            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_searchable_spinner);
            dialog.getWindow().setLayout(1000,1500);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            EditText editText = dialog.findViewById(R.id.edit_text);
            ListView listView = dialog.findViewById(R.id.list_view);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>( getContext(), android.R.layout.simple_list_item_1,arrayList);
            listView.setAdapter(adapter1);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter1.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            listView.setOnItemClickListener((parent, view12, position, id) -> {
                searchBar.setText(adapter1.getItem(position));

                if (position==0){
                    Intent i = new Intent(getContext(), ElectricianActivity.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==1){
                    Intent i = new Intent(getContext(), PlumberActivity.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==2){
                    Intent i = new Intent(getContext(), CarpenterActivity.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==3){
                    Intent i = new Intent(getContext(), MechanicActivity.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==4){
                    Intent i = new Intent(getContext(), MaidServiceActivity.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==5){
                    Intent i = new Intent(getContext(), HomecleaningActivity.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==6){
                    Intent i = new Intent(getContext(),LaundryActivity.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==7){
                    Intent i = new Intent(getContext(), RentalService.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==8){
                    Intent i = new Intent(getContext(), SalonMenActivity.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==9){
                    Intent i = new Intent(getContext(), SalonWomenAcitivity.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==10){
                    Intent i = new Intent(getContext(), MobileLaptop.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==11){
                    Intent i = new Intent(getContext(), Electronics.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==12){
                    Intent i = new Intent(getContext(), TelevisionRadio.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==13){
                    Intent i = new Intent(getContext(), AcFan.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                if (position==14){
                    Intent i = new Intent(getContext(), ServiceBoy.class);
                    i.putExtra("location", selected_location);
                    startActivity(i);
                }
                dialog.dismiss();
            });

        });

        seemore.setOnClickListener(v -> {
           Fragment mFragment  = new MenuFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_id, mFragment).commit();
        });

       if (getActivity().getIntent().getExtras() != null){
           checkUser = getActivity().getIntent().getStringExtra("guest");
           if (checkUser.equals("guest")){
               binding.namedisplay.setText("Guest");
           }
       } else {
           mAuth = FirebaseAuth.getInstance();
           db = FirebaseFirestore.getInstance();
           userId = mAuth.getCurrentUser().getUid();
           fetchData();
       }

       fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

       String[] title = {"Electrician","Plumber","Carpenter","Mechanic","Maid","Home Cleaning","Laundry","Rental","Salon-Men","Salon-Women","Mobile-Laptop","Electronics","Television-Radio","Ac-Fan","Service-Boy"};
       int[] img = {R.drawable.electrician,R.drawable.plumber,R.drawable.carpenter,R.drawable.mechanic,R.drawable.maid,R.drawable.home_cleaning,R.drawable.laundry,R.drawable.laundry,R.drawable.gentsalon,R.drawable.ladiesparlour,R.drawable.laptop_and_mobile,R.drawable.electronics,R.drawable.television_and_electronics,R.drawable.ac,R.drawable.serviceboy};

       spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selected_location = spinnerLocation.getSelectedItem().toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

       GridAdapter adapter1 = new GridAdapter(getContext(), title, img);
       binding.gvCategory.setAdapter(adapter1);

       binding.gvCategory.setOnItemClickListener((parent, view1, position, id) -> {
           if (position==0){
               Intent i = new Intent(getContext(), ElectricianActivity.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==1){
               Intent i = new Intent(getContext(), PlumberActivity.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==2){
               Intent i = new Intent(getContext(), CarpenterActivity.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==3){
               Intent i = new Intent(getContext(), MechanicActivity.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==4){
               Intent i = new Intent(getContext(), MaidServiceActivity.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==5){
               Intent i = new Intent(getContext(), HomecleaningActivity.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==6){
               Intent i = new Intent(getContext(),LaundryActivity.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==7){
               Intent i = new Intent(getContext(), RentalService.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==8){
               Intent i = new Intent(getContext(), SalonMenActivity.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==9){
               Intent i = new Intent(getContext(), SalonWomenAcitivity.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==10){
               Intent i = new Intent(getContext(), MobileLaptop.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==11){
               Intent i = new Intent(getContext(), Electronics.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==12){
               Intent i = new Intent(getContext(), TelevisionRadio.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==13){
               Intent i = new Intent(getContext(), AcFan.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
           if (position==14){
               Intent i = new Intent(getContext(), ServiceBoy.class);
               i.putExtra("location", selected_location);
               startActivity(i);
           }
       });
       return view;
    }

    private FragmentManager getSupportFragmentManager() {
        return null;
    }

    private void fetchData() {
        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Log.d(TAG, "fetchData: "+document);
                if (document.exists()) {
                    //Glide.with(getContext()).load(document.getString("image")).circleCrop().into(dp);
                    binding.namedisplay.setText(document.getString("name"));
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void EventChangeListener() {
        db.collection("ActivityLogs")
                .addSnapshotListener((value, error) -> {
                    for (DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            list.add(dc.getDocument().toObject(ServiceProviders.class));
                        }
                        adapter.notifyDataSetChanged();
                    }

                });


        clearLogbtn.setOnClickListener(v -> {
            db.collection("ActivityLogs").document(userId).delete()
                    .addOnSuccessListener(unused -> Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Cleared", Toast.LENGTH_SHORT).show();
                    });
        });
    }


}
