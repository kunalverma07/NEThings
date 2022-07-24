package com.nething;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ServiceProviderProfile extends AppCompatActivity {

    private static final int REQUEST_CALL =1;
    ImageView dp;
    TextView spName,spAddress,spEmail,spPincode,spLocality,spChargingFee;
    private TextView spPhone;
    Button callbtn;
    String name, address, phone, email, pincode, locality, image ,charging_fee, service_type, uid;
    private FirebaseFirestore db;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_provider_profile);

        Intent i = getIntent();
        image = i.getStringExtra("image");
        name = i.getStringExtra("name");
        address = i.getStringExtra("address");
        phone = i.getStringExtra("phone");
        email = i.getStringExtra("email");
        pincode = i.getStringExtra("pincode");
        locality = i.getStringExtra("locality");
        image = i.getStringExtra("image");
        charging_fee = i.getStringExtra("charging_fee");
        service_type = i.getStringExtra("service_type");

        dp = findViewById(R.id.image_service_provider_profileId);
        spName =findViewById(R.id.tv_service_provider_name);
        spAddress = findViewById(R.id.tv_service_provider_address);
        spPhone =findViewById(R.id.tv_service_provider_phone);
        spEmail =findViewById(R.id.tv_service_provider_email);
        spPincode =findViewById(R.id.tv_service_provider_pincode);
        spLocality =findViewById(R.id.tv_service_provider_locality);
        spChargingFee =findViewById(R.id.tv_service_provider_fee);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        db = FirebaseFirestore.getInstance();

        callbtn =findViewById(R.id.call_btn);
        callbtn.setOnClickListener(v -> CallButton());

        initData();

    }

    private void initData() {

        Glide.with(getApplicationContext()).load(image).circleCrop().into(dp);
        spName.setText(name);
        spAddress.setText(address);
        spPhone.setText(phone);
        spPincode.setText(pincode);
        spEmail.setText(email);
        spLocality.setText(locality);
        spChargingFee.setText(charging_fee);

    }

    private void CallButton() {
        String number = spPhone.getText().toString();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else
        {
            String dial = "tel:"+number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");
        String key =  myRef.push().getKey();
        Map<String, Object> activityCall = new HashMap<>();
        activityCall.put("name", name);
        activityCall.put("caller_uid", uid);
        activityCall.put("reciever_uid", "");
        activityCall.put("date", formattedDate);
        activityCall.put("time", currentTime);
        activityCall.put("service_type",service_type);
        activityCall.put("image",image);
        db.collection("ActivityLogs").document(uid)
                .set(activityCall)
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CallButton();
            } else {
                Toast.makeText(this, "permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}