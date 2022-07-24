package com.nething;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditProfileDialog extends AppCompatDialogFragment  {

    private EditText editName;
    private EditText editAddress;
    private EditText editPhone;
    private EditText editPincode;
    private EditText editLocality;
    private EditProfileListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.editprofile_dialog, null);

        builder.setView(view)
                .setTitle("Edit")
                .setNegativeButton("Cancel", (dialog, which) -> {

                }).setPositiveButton("Update", (dialog, which) -> {
                    String name = editName.getText().toString();
                    String address = editAddress.getText().toString();
                    String phone = editPhone.getText().toString();
                    String pincode = editPincode.getText().toString();
                    String locality = editLocality.getText().toString();
                    listener.applyTexts(name, address, phone, pincode, locality);
                });

        editName = view.findViewById(R.id.edit_user_name);
        editAddress = view.findViewById(R.id.edit_user_address);
        editPhone = view.findViewById(R.id.edit_user_phone);
        editPincode = view.findViewById(R.id.edit_user_pincode);
        editLocality = view.findViewById(R.id.edit_user_locality);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (EditProfileListener) context;
        } catch (Exception e){
            throw new ClassCastException(context.toString()+"hahaha") ;
        }

    }

  //  @Override
    public void applyTexts(String name, String address, String phone, String pincode, String locality) {

        editName.setText(name);
        editAddress.setText(address);
        editPhone.setText(phone);
        editPincode.setText(pincode);
        editLocality.setText(locality);
    }

    public interface EditProfileListener{
         void applyTexts(String name, String address, String phone, String pincode, String locality);
    }

}
