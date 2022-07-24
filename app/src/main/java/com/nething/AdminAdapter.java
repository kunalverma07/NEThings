package com.nething;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.MyViewHolder> {

    Context context;
    ArrayList<ServiceProviders> userArrayList;

    public AdminAdapter(Context context, ArrayList<ServiceProviders> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_pending_request,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        ServiceProviders user = userArrayList.get(position);
        Glide.with(context).load(user.image).into(holder.requestImage);
        holder.requestName.setText(user.name);
        holder.requestCategory.setText(user.service_type);
        holder.rejectId.setOnClickListener(v -> {
            db.collection("PendingRequest").document(user.uid).delete()
                    .addOnSuccessListener(unused -> Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    });
        });
        holder.acceptId.setOnClickListener(v -> {
            Map<String, Object> map = new HashMap<>();
            map.put("address", user.address);
            map.put("charging_fee", user.charging_fee);
            map.put("district",user.district);
            map.put("email",user.email);
            map.put("image",user.image);
            map.put("locality", user.locality);
            map.put("name", user.name);
            map.put("phone",user.phone);
            map.put("pincode", user.pincode);
            map.put("service_type", user.service_type);
            map.put("uid", user.uid);
            db.collection(user.service_type).document(user.uid).set(map).addOnSuccessListener(unused -> {
                db.collection("PendingRequest").document(user.uid).delete()
                        .addOnSuccessListener(unused1 -> Toast.makeText(context, "Done.... please logout and sign in again", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> {
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        });
                Toast.makeText(context, "Successfull", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            });
        });
        holder.documentId.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("application/pdf");
            intent.setData(Uri.parse(user.url));
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView requestName,requestCategory;
        ImageView requestImage;
        Button documentId,acceptId,rejectId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            requestName = itemView.findViewById(R.id.requests_name);
            requestCategory = itemView.findViewById(R.id.requests_category);
            requestImage = itemView.findViewById(R.id.requests_dp);
            documentId = itemView.findViewById(R.id.document_id);
            acceptId = itemView.findViewById(R.id.accept_id);
            rejectId = itemView.findViewById(R.id.reject_id);
        }

    }
}
