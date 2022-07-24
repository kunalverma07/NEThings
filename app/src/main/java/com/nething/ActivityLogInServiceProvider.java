package com.nething;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityLogInServiceProvider extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView new_User;
    private EditText login_serviceprovider_email,login_serviceprovider_password;
    private TextView forgetpass;
    private Button serviceprovider_login;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private static final String TAG = "LOGIN";

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_service_provider);
        //Intialize
        new_User = findViewById(R.id.login_serviceprovider_register);
        toolbar = findViewById(R.id.sp_activity_login_toolbar);
        login_serviceprovider_email = findViewById(R.id.login_serviceprovider_email);
        login_serviceprovider_password = findViewById(R.id.login_serviceprovider_password);
        serviceprovider_login = findViewById(R.id.login_serviceprovider_login);
        forgetpass = findViewById(R.id.login_serviceprovider_forget_password);
        progressDialog = new ProgressDialog(ActivityLogInServiceProvider.this);
        mAuth = FirebaseAuth.getInstance();
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

        //Events
        new_User.setOnClickListener(v -> startActivity(new Intent(ActivityLogInServiceProvider.this, ActivityRegisterServiceProvider.class)));

        //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        serviceprovider_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    progressBarSet();
                    signInWithEmailAndPassword(login_serviceprovider_email.getText().toString().trim(),login_serviceprovider_password.getText().toString().trim());
                }
            }
        });

        //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!login_serviceprovider_email.equals(""))
                {
                    mAuth.sendPasswordResetEmail(login_serviceprovider_email.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ActivityLogInServiceProvider.this,"Password Resend Link send to email.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    login_serviceprovider_email.setError("Enter Email");
                }
            }
        });
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    private void signInWithEmailAndPassword(final String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            progressBarUnset();
                            if(email.equals("admin@admin.com")){
                                startActivity(new Intent(ActivityLogInServiceProvider.this, Admin.class));
                            } else {
                                startActivity(new Intent(ActivityLogInServiceProvider.this,HomeActivity.class));
                            }
                        } else {
                            progressBarUnset();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(ActivityLogInServiceProvider.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    private boolean validate()
    {
        boolean istrue = true;
        if(login_serviceprovider_email.getText().toString().equals(""))
        {
            istrue = false;
            login_serviceprovider_email.setError("Enter email.");
            login_serviceprovider_email.requestFocus();
        }
        if(login_serviceprovider_email.getText().toString().matches("^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$"))
        {
            login_serviceprovider_email.setError("Please enter valid email.");
            login_serviceprovider_email.requestFocus();
            istrue = false;
        }
        if(login_serviceprovider_password.getText().toString().equals(""))
        {
            istrue = false;
            login_serviceprovider_password.setError("Enter password.");
            login_serviceprovider_password.requestFocus();
        }
        if(login_serviceprovider_password.getText().toString().length()<5)
        {
            istrue = false;
            login_serviceprovider_password.setError("Enter valid password(minimum 6 alphanumeric).");
            login_serviceprovider_password.requestFocus();
        }
        return istrue;
    }
    void progressBarSet()
    {
        progressDialog.setMessage("Signing In");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    void progressBarUnset()
    {
        progressDialog.dismiss();
    }
}
