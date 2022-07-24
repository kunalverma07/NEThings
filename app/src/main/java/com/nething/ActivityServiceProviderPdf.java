package com.nething;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nething.databinding.ActivityServiceProviderPdfBinding;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ActivityServiceProviderPdf extends AppCompatActivity {

    ActivityServiceProviderPdfBinding binding;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private ProgressDialog pd;
    String pdfName, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceProviderPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pd = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference = FirebaseStorage.getInstance().getReference("Documents");
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        binding.btnSelect.setOnClickListener(v -> {
            selectPdf();
        });

    }

    private void selectPdf() {
        Intent i = new Intent();
        i.setType("application/pdf");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select a pdf"), 69);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 69 && resultCode == RESULT_OK && data != null && data.getData() != null ){
            pdfName = data.getDataString();
            binding.pdfLabel.setText(pdfName);
            uploadPdf(data.getData());
        }
    }

    private void uploadPdf(Uri data) {

        pd.setTitle("Uploading please wait...");
        pd.show();
        StorageReference reference = storageReference.child("uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(taskSnapshot -> {
            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
            while (!uri.isComplete());
            Uri pdfUrl = uri.getResult();
            uploadToDatabase(pdfUrl);
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }).addOnProgressListener(snapshot -> {
            double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
            pd.setMessage("Uploaded: "+(int)progress+"%");
        });

    }

    private void uploadToDatabase(Uri pdfUrl) {

        Map<String, Object> pdf = new HashMap<>();
        pdf.put("title", pdfName);
        pdf.put("url", pdfUrl);
        db.collection("PendingRequest").document(uid).update(pdf).addOnCompleteListener(task -> {
            Toast.makeText(this, "Submitted to database.", Toast.LENGTH_SHORT).show();
            binding.btnSubmit.setEnabled(true);
            binding.btnSubmit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            Intent i = new Intent(ActivityServiceProviderPdf.this, HomeActivity.class);
            startActivity(i);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to upload to database.", Toast.LENGTH_SHORT).show();
        });

    }
}