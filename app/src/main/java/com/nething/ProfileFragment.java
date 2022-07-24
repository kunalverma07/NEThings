package com.nething;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    ImageView dp;
    TextView userName,userAddress,userEmail,userPhone,userPincode,userLocality;
    TextView profileName,profileAddress,profilePhone,profileEmail,profilePincode,profileLocality,guestId,guestResgister,guestRegisterLink;
    Button  logoutBtn,doneBtn;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private FirebaseUser user;
    String userId, checkUser, uid;
   // ImageButton userEditBtn;
    Dialog dialog;
    ImageView editImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dp = view.findViewById(R.id.user_image_profileId);
        userName =view.findViewById(R.id.tv_user_name);
        userAddress = view.findViewById(R.id.tv_user_address);
        userPhone =view.findViewById(R.id.tv_user_phone);
        userEmail =view.findViewById(R.id.tv_user_email);
        userPincode =view.findViewById(R.id.tv_user_pincode);
        userLocality =view.findViewById(R.id.tv_user_locality);
        logoutBtn = view.findViewById(R.id.logout_btn);

        profileName = view.findViewById(R.id.tv_profileName);
        profileAddress = view.findViewById(R.id.tv_profileAddress);
        profilePhone = view.findViewById(R.id.tv_profilePhone);
        profileEmail = view.findViewById(R.id.tv_profileEmail);
        profilePincode = view.findViewById(R.id.tv_profilePincode);
        profileLocality = view.findViewById(R.id.tv_profileLocality);

        //----------user update---------
        editImage =view.findViewById(R.id.edit_profileId);
        user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        uid = user.getUid();

       //-------------------------


        //--------------------dialog box ------for edit user ----------
       // userEditBtn = view.findViewById(R.id.user_editBtn);
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.editprofile_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

//        userEditBtn.setOnClickListener(v -> {
//            //ialog.show();
//            showDialog();
//        });


        guestId = view.findViewById(R.id.guest_id);
        guestResgister = view.findViewById(R.id.guestResgister);
        guestRegisterLink = view.findViewById(R.id.guest_link);

        guestId.setVisibility(View.GONE);
        guestResgister.setVisibility(View.GONE);
        guestRegisterLink.setVisibility(View.GONE);

        if (getActivity().getIntent().getExtras() != null){
            checkUser = getActivity().getIntent().getStringExtra("guest");
            if (checkUser.equals("guest")){
                profileName.setVisibility(View.GONE);
                profileAddress.setVisibility(View.GONE);
                profilePhone.setVisibility(View.GONE);
                profileEmail.setVisibility(View.GONE);
                profilePincode.setVisibility(View.GONE);
                profileLocality.setVisibility(View.GONE);
                logoutBtn.setVisibility(View.GONE);
                guestId.setVisibility(View.VISIBLE);
                guestResgister.setVisibility(View.VISIBLE);
                guestRegisterLink.setVisibility(View.VISIBLE);
            }
        } else {
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            userId = mAuth.getCurrentUser().getUid();
            fetchData();
        }

        logoutBtn.setOnClickListener(v -> {
            logOut();
        });
        guestRegisterLink.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ActivityStart.class));
        });
        return view;
    }

//    private void showDialog() {
//
//        EditProfileDialog editProfileDialog = new EditProfileDialog();
//        editProfileDialog.show(getFragmentManager(), "editprofile");
//    }

    private void fetchData() {
        Log.d(TAG, "userId: "+userId);
        DocumentReference docRef = db.collection("Users").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                Log.d(TAG, "fetchData: "+document);
                if (document.exists()) {
                    Glide.with(getContext()).load(document.getString("image")).circleCrop().into(dp);
                    userPhone.setText(document.getString("phone"));
                    userAddress.setText(document.getString("address"));
                    userEmail.setText(document.getString("email"));
                    userLocality.setText(document.getString("locality"));
                    userName.setText(document.getString("name"));
                    userPincode.setText(document.getString("pincode"));

                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void logOut() {
        mAuth.signOut();
        startActivity(new Intent(getContext(), ActivityStart.class));
    }

}