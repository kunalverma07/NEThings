package com.nething;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityStart extends AppCompatActivity {
    private Button customer;
    private Button service_Provider,guest;
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //Intilization
            customer = findViewById(R.id.start_customer);
            service_Provider = findViewById(R.id.start_service_provider);
           // guest = findViewById(R.id.start_guest_user);
        //Onlick Events
        customer.setOnClickListener(v -> startActivity(new Intent(ActivityStart.this, ActivityLogInCustomer.class)));
        // Onclick event for service provider
        service_Provider.setOnClickListener(v -> startActivity(new Intent(ActivityStart.this, ActivityLogInServiceProvider.class)));
        //Onclick event for guest
//        guest.setOnClickListener(v -> {
//            Intent intent = new Intent(ActivityStart.this, HomeActivity.class);
//            intent.putExtra("guest", "guest");
//            startActivity(intent);
//        });
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
