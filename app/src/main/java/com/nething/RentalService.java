package com.nething;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class
RentalService extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ServiceProviders> userArrayList;
    MyAdapterServiceProvider myAdapterServiceProvider;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentalservice);

        recyclerView = findViewById(R.id.rentalId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        myAdapterServiceProvider = new MyAdapterServiceProvider(this, userArrayList);

        recyclerView.setAdapter(myAdapterServiceProvider);
        EventChangeListener();

    }

    private void EventChangeListener() {
        db.collection("RentalService")
                .addSnapshotListener((value, error) -> {

                    for (DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            userArrayList.add(dc.getDocument().toObject(ServiceProviders.class));
                        }
                        myAdapterServiceProvider.notifyDataSetChanged();
                    }

                });
    }

}