package com.nething;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class LoginActivity extends AppCompatActivity {
    TextView tvPhoneLogin,tvUserLogin,tvloginOtp;
    EditText etPhonenoLogin,etloginOtp;
    Button  btnLogin,btnloginverify;
    ProgressBar pBar;
    FirebaseAuth mAuth;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        tvPhoneLogin = findViewById(R.id.tv_phone_login);
        tvUserLogin = findViewById(R.id.tv_user_login);
        etPhonenoLogin = findViewById(R.id.et_phone_no_login);
        btnLogin = findViewById(R.id.btn_login);
        btnloginverify = findViewById(R.id.btnloginverify);
        tvloginOtp = findViewById(R.id.tv_loginOtp);
        etloginOtp = findViewById(R.id.et_loginOtp);
        pBar = findViewById(R.id.bar1);

        tvloginOtp.setVisibility(View.GONE);
        etloginOtp.setVisibility(View.GONE);
        btnloginverify.setVisibility(View.GONE);



      /*  btnLogin.setOnClickListener(view -> {

            if (!TextUtils.isEmpty(etPhonenoLogin.getText())){
                tvloginOtp.setVisibility(View.VISIBLE);
                etloginOtp.setVisibility(View.VISIBLE);
                btnloginverify.setVisibility(View.VISIBLE);
            } else{
                Toast.makeText(this, "Enter your mobile Number", Toast.LENGTH_SHORT).show();
            }



        });

        tvUserLogin.setOnClickListener(view -> {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
        });
    }


}       */


        tvUserLogin.setOnClickListener(view -> {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
        });


        btnLogin.setOnClickListener(view -> {

            if(!TextUtils.isEmpty((etPhonenoLogin.getText())))
            {
                String phonenumber = etPhonenoLogin.getText().toString();
                pBar.setVisibility(View.VISIBLE);
                sendverificationcode(phonenumber);

                tvloginOtp.setVisibility(View.VISIBLE);
                etloginOtp.setVisibility(View.VISIBLE);
                btnloginverify.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Please Enter a Valid Number", Toast.LENGTH_SHORT).show();

            }



        });

        btnloginverify.setOnClickListener(view1 -> {
            if(TextUtils.isEmpty(etloginOtp.getText())){
                Toast.makeText(this, "Wrong OTP Entered", Toast.LENGTH_SHORT).show();
            } else {
                verifycode(etloginOtp.getText().toString());
            }
        });


    }

    private void sendverificationcode(String phoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            final String code = credential.getSmsCode();
            if (code!=null){
                verifycode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(LoginActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token)
        {
            super.onCodeSent(s,token);
            verificationId = s;
            Toast.makeText(LoginActivity.this, "Code Send", Toast.LENGTH_SHORT).show();
            btnloginverify.setEnabled(true);
            pBar.setVisibility(View.INVISIBLE);


        }
    };

    private void verifycode(String Code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, Code);
        signinbyCredintials(credential);
    }

    private void signinbyCredintials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    }
                });


    }


}