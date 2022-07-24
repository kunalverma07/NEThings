package com.nething;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nething.databinding.ActivityCustomerDetailsBinding;
import com.nething.databinding.ActivityServiceProviderDetailsBinding;

import java.util.HashMap;
import java.util.Map;

public class ActivityCustomerDetails extends AppCompatActivity {

    ActivityCustomerDetailsBinding binding;
    public Uri imageUri;
    private FirebaseFirestore myDB;
    private String uid, name, phone, email, imageUrl;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseUser user;
    private ImageView customer_image;
    private ImageButton customer_imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        binding = ActivityCustomerDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myDB = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        customer_image = findViewById(R.id.customer_dp);
        customer_imageButton = findViewById(R.id.customer_dp_edit);

       Intent intent = getIntent();
       name = intent.getStringExtra("name");
       phone = intent.getStringExtra("phone");
       email = intent.getStringExtra("email");

       binding.customerDpEdit.setOnClickListener(v ->  choosePicture());

    //-------------------------------------------------------

       binding.customerSubmitdetails.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("uid", uid);
            map.put("address", binding.customerAddress1.getText().toString().trim());
            map.put("name", name);
            map.put("phone", phone);
            map.put("email", email);
            map.put("locality", binding.customerLocality.getText().toString().trim());
            map.put("pincode", binding.customerPincode.getText().toString().trim());
            map.put("image", imageUrl);
            myDB.collection("Customers").document(uid).set(map).addOnSuccessListener(aVoid -> {
                Map<String, Object> user = new HashMap<>();
                user.put("uid", uid);
                user.put("user_type", "customer");
                user.put("address", binding.customerAddress1.getText().toString().trim());
                user.put("name", name);
                user.put("phone", phone);
                user.put("email", email);
                user.put("locality", binding.customerLocality.getText().toString().trim());
                user.put("pincode", binding.customerPincode.getText().toString().trim());
                user.put("image", imageUrl);
                myDB.collection("Users").document(uid).set(user).addOnSuccessListener(unused -> {
                    Toast.makeText(ActivityCustomerDetails.this, "Welcome",
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActivityCustomerDetails.this, HomeActivity.class);
                    startActivity(i);
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(ActivityCustomerDetails.this, "Unable to Update", Toast.LENGTH_SHORT).show();
            });
        });
}

    private void choosePicture() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data.getData()!=null){
            imageUri = data.getData();
            Glide.with(getApplicationContext()).load(imageUri).into(binding.customerDp);
            uploadToDatabase();
        }
    }

    private void uploadToDatabase() {

        StorageReference dpRef = storageReference.child("images/" + uid);
        dpRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    dpRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        imageUrl = uri.toString();
                    });
                    Toast.makeText(this, "Profile picture uploaded.", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to upload profile picture. "+e.toString(), Toast.LENGTH_SHORT).show();
                });
    }
}