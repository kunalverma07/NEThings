package com.nething;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nething.databinding.ActivityMainBinding;
import com.nething.databinding.ActivityServiceProviderDetailsBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ActivityServiceProviderDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityServiceProviderDetailsBinding binding;
    private EditText address1, pincode, state, locality;
    private Spinner spinner, distSpinner;
    private TextView textView;
    private static final String TAG = "MYTAG";
    private String uid, name, phone, email, imageUrl;
    private Button btn,selectbtn;
    private ProgressDialog progressDialog;
    private EditText charging_fee;
    private FirebaseFirestore myDB;
    private FirebaseUser user;
    ImageButton ib_servicePprovider_dp_edit;
    ImageView iv_servicePprovider_dp;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_details);
        binding = ActivityServiceProviderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");

        //Intialize
        address1 = findViewById(R.id.service_provider_address1);
        pincode = findViewById(R.id.service_provider_pincode);
        locality = findViewById(R.id.service_provider_locality);
        state = findViewById(R.id.service_provider_state);
        charging_fee = findViewById(R.id.service_provider_chargingfee);
        btn = findViewById(R.id.service_provider_submitdetails);
        textView = findViewById(R.id.service_provider_name);
        progressDialog = new ProgressDialog(ActivityServiceProviderDetails.this);
        spinner = (Spinner) findViewById(R.id.serviceprovider_servicespinner);
        distSpinner = (Spinner) findViewById(R.id.serviceprovider_district);
        user = FirebaseAuth.getInstance().getCurrentUser();
        iv_servicePprovider_dp = findViewById(R.id.service_provider_dp);
        ib_servicePprovider_dp_edit = findViewById(R.id.service_provider_dp_edit);

        //selectbtn = findViewById(R.id.selectBtnId);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        assert user != null;
        uid = user.getUid();
        List<String> list = new ArrayList<String>();
        list.add("Electrician");
        list.add("Plumber");
        list.add("Carpenter");
        list.add("Mechanic");
        list.add("Maid");
        list.add("Home Cleaning");
        list.add("Laundry");
        list.add("Salon-Men");
        list.add("Salon-Women");
        list.add("Rental");
        list.add("Electronics");
        list.add("Service-Boy");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textView.setText("Hello " + getIntent().getStringExtra("name"));
        spinner.setAdapter(dataAdapter);
        myDB = FirebaseFirestore.getInstance();
        email = getIntent().getStringExtra("email");

        // district spinner

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.district, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distSpinner.setAdapter(adapter);

        distSpinner.setOnItemSelectedListener(this);

//       //-------------------------image upload-----------------

        binding.serviceProviderDpEdit.setOnClickListener(v -> choosePicture());

        //----------------------------upload document--------------

        btn.setOnClickListener(v -> {
            Toast.makeText(this, distSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            if (validate()) {
                progressBarSet();
                Map<String, Object> map = new HashMap<>();
                map.put("uid", uid);
                map.put("service_type", spinner.getSelectedItem().toString());
                map.put("district", distSpinner.getSelectedItem().toString());
                map.put("address", address1.getText().toString().trim());
                map.put("name", name);
                map.put("phone", phone);
                map.put("email", email);
                map.put("locality", locality.getText().toString().trim());
                map.put("pincode", pincode.getText().toString().trim());
                map.put("charging_fee", charging_fee.getText().toString().trim());
                map.put("image", imageUrl);
                myDB.collection("PendingRequest").document(uid).set(map).addOnSuccessListener(aVoid -> {
                    Map<String, Object> user = new HashMap<>();
                    user.put("uid", uid);
                    user.put("service_type", spinner.getSelectedItem().toString());
                    user.put("district", distSpinner.getSelectedItem().toString());
                    user.put("user_type", "service_provider");
                    user.put("address", address1.getText().toString().trim());
                    user.put("name", name);
                    user.put("phone", phone);
                    user.put("email", email);
                    user.put("locality", locality.getText().toString().trim());
                    user.put("pincode", pincode.getText().toString().trim());
                    user.put("charging_fee", charging_fee.getText().toString().trim());
                    user.put("image", imageUrl);
                    myDB.collection("Users").document(uid).set(user).addOnSuccessListener(unused -> {
                        progressBarUnset();
                        Toast.makeText(ActivityServiceProviderDetails.this, "Welcome",
                                Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ActivityServiceProviderDetails.this, ActivityServiceProviderPdf.class);
                        startActivity(i);
                    });
                }).addOnFailureListener(e -> {
                    progressBarSet();
                    Log.d(TAG, "Failure Details" + e.toString());
                    Toast.makeText(ActivityServiceProviderDetails.this, "Unable to Update", Toast.LENGTH_SHORT).show();
                });
            }
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
            Glide.with(getApplicationContext()).load(imageUri).into(binding.serviceProviderDp);
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

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    private boolean validate() {
        boolean istrue = true;
        if (address1.getText().toString().equals("")) {
            address1.setError("Please enter address1.");
            address1.requestFocus();
            istrue = false;
        }

        if (pincode.getText().toString().equals("")) {
            pincode.setError("Please enter pincode.");
            pincode.requestFocus();
            istrue = false;
        }
        if (state.getText().toString().equals("")) {
            state.setError("Please enter state.");
            state.requestFocus();
            istrue = false;
        }
        if (locality.getText().toString().equals("")) {
            locality.setError("Please enter locality.");
            locality.requestFocus();
            istrue = false;
        }
        return istrue;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    void progressBarSet() {
        progressDialog.setMessage("Registering In!");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void progressBarUnset() {
        progressDialog.dismiss();
    }

    // district spinner

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice  = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
