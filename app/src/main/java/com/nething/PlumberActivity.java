package com.nething;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PlumberActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ServiceProviders> userArrayList;
    MyAdapterServiceProvider myAdapterServiceProvider;
    FirebaseFirestore db;
    ImageView bg; //
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber);

        location = getIntent().getStringExtra("location");

        bg = findViewById(R.id.no_content); //

        recyclerView = findViewById(R.id.plumberId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        myAdapterServiceProvider = new MyAdapterServiceProvider(this, userArrayList);

        recyclerView.setAdapter(myAdapterServiceProvider);
        EventChangeListener();
        checkDocument(); //

    }

    private void checkDocument() { //

        db.collection("Plumber").whereEqualTo("district", location).get() //plumber  //change xml
                .addOnCompleteListener(task -> {
                    if (task.getResult().isEmpty()){
                        bg.setVisibility(View.VISIBLE);
                    } else {
                        bg.setVisibility(View.GONE);
                    }
                });

    }

    private void EventChangeListener() {
        db.collection("Plumber")
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