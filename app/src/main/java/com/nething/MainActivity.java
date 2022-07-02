package com.nething;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button logoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        logoutbtn = findViewById(R.id.logout_btn);

        logoutbtn.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this,SignupActivity.class));
            finish();

        });

    }

   @Override
      protected void onStart() {
          super.onStart();
       FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
           if (currentUser==null){
                Intent i = new Intent(this, SignupActivity.class);
                  startActivity(i);
         }
        }
}