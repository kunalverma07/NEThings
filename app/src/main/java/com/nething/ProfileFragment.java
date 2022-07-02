package com.nething;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;


public class profileFragment extends Fragment {
    EditText editName,editEmail;
    Button addBtn;
    FirebaseFirestore dbroot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        editName = view.findViewById(R.id.editname);
        editEmail = view.findViewById(R.id.editemail);
        dbroot = FirebaseFirestore.getInstance();

        addBtn = view.findViewById(R.id.addbtn);
        addBtn.setOnClickListener(view1 -> {
            insertdata();
        });

        return  view;
    }

    private void insertdata() {

        Map<String,String> items=new HashMap<>();
        items.put("name",editName.getText().toString().trim());
        items.put("email",editEmail.getText().toString().trim());

        dbroot.collection("Categories").add(items)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        editName.setText("");
                        editEmail.setText("");
                        Toast.makeText(getContext(),"Inserted Successfully",Toast.LENGTH_SHORT);
                    }
                });
    }
}