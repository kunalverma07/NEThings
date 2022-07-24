package com.nething;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class ElectricianActivity extends AppCompatActivity {

    private static final String TAG = "HomeFragment";
    RecyclerView recyclerView;
    ImageView bg;
    ArrayList<ServiceProviders> userArrayList;
    MyAdapterServiceProvider myAdapterServiceProvider;
    FirebaseFirestore db;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician);

        location = getIntent().getStringExtra("location");
        bg = findViewById(R.id.no_content);

        recyclerView = findViewById(R.id.electricianId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        myAdapterServiceProvider = new MyAdapterServiceProvider(this, userArrayList);

        recyclerView.setAdapter(myAdapterServiceProvider);
        EventChangeListener();
        checkDocument();

    }

    private void checkDocument() {

        db.collection("Electrician").whereEqualTo("district", location).get()
                .addOnCompleteListener(task -> {
                   if (task.getResult().isEmpty()){
                       bg.setVisibility(View.VISIBLE);
                   } else {
                       bg.setVisibility(View.GONE);
                   }
                });

    }

    private void EventChangeListener() {
        db.collection("Electrician").whereEqualTo("district", location)
                .addSnapshotListener((value, error) -> {
                    assert value != null;
                    for (DocumentChange dc : value.getDocumentChanges()){
                        if(dc.getType() == DocumentChange.Type.ADDED){
                            userArrayList.add(dc.getDocument().toObject(ServiceProviders.class));
                        }
                        myAdapterServiceProvider.notifyDataSetChanged();
                    }
                });
    }

}