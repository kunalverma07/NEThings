package com.nething;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ServiceProviders> userArrayList;
    AdminAdapter myAdapterServiceProvider;
    FirebaseFirestore db;
    Button adminbtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.adminrRcyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adminbtn = findViewById(R.id.adminlogout_btn);

        adminbtn.setOnClickListener(v -> {
            logOut();
        });

        userArrayList = new ArrayList<>();
        myAdapterServiceProvider = new AdminAdapter(this, userArrayList);

        recyclerView.setAdapter(myAdapterServiceProvider);
        EventChangeListener();
    }

    private void EventChangeListener() {
        db.collection("PendingRequest")
                .addSnapshotListener((value, error) -> {
                    for (DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            userArrayList.add(dc.getDocument().toObject(ServiceProviders.class));
                        }
                        myAdapterServiceProvider.notifyDataSetChanged();
                    }

                });
    }
    private void logOut() {
        mAuth.signOut();
        startActivity(new Intent(this, ActivityStart.class));
    }
}