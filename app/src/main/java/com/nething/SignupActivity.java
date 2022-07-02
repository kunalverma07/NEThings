package com.nething;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignupActivity extends AppCompatActivity {

    TextView tvotp,tvSignUpPhone,signUpLogin;
    EditText etotp,etSignUpPhone;
    Button btnSignup , btnVerify;
    FirebaseAuth mAuth;
    String verificationId;
    ProgressBar pBar;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        tvotp = findViewById(R.id.tv_Otp);
        tvSignUpPhone = findViewById(R.id.tv_signup_phone);
        etotp = findViewById(R.id.et_Otp);
        etSignUpPhone = findViewById(R.id.et_signup_phone);
        signUpLogin = findViewById(R.id.signuplogin);
        btnSignup = findViewById(R.id.btn_singup);
        btnVerify = findViewById(R.id.btnverify);
        pBar = findViewById(R.id.bar);

       tvotp.setVisibility(View.GONE);
       etotp.setVisibility(View.GONE);
       btnVerify.setVisibility(View.GONE);

       btnSignup.setOnClickListener(view -> {
           if(!TextUtils.isEmpty((etSignUpPhone.getText())))
           {
               String phonenumber = etSignUpPhone.getText().toString();
               pBar.setVisibility(View.VISIBLE);
               sendverificationcode(phonenumber);

               tvotp.setVisibility(View.VISIBLE);
               etotp.setVisibility(View.VISIBLE);
               btnVerify.setVisibility(View.VISIBLE);
           } else {
               Toast.makeText(this, "Please Enter a Valid Number", Toast.LENGTH_SHORT).show();

           }



       });

        btnVerify.setOnClickListener(view1 -> {
            if(TextUtils.isEmpty(etotp.getText())){
                Toast.makeText(this, "Wrong OTP Entered", Toast.LENGTH_SHORT).show();
            } else {
                verifycode(etotp.getText().toString());
            }
        });

        signUpLogin.setOnClickListener(view -> {
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
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

                Toast.makeText(SignupActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                
            }

            @Override
            public void onCodeSent(@NonNull String s,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token)
            {
                super.onCodeSent(s,token);
                verificationId = s;
                Toast.makeText(SignupActivity.this, "Code Send", Toast.LENGTH_SHORT).show();
                btnVerify.setEnabled(true);
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
                    Toast.makeText(SignupActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    }
                });


    }


}