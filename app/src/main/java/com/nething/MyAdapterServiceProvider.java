package com.nething;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapterServiceProvider extends RecyclerView.Adapter<MyAdapterServiceProvider.MyViewHolder> {

    Context context;
    ArrayList<ServiceProviders> userArrayList;

    public MyAdapterServiceProvider(Context context, ArrayList<ServiceProviders> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapterServiceProvider.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_service_providers,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterServiceProvider.MyViewHolder holder, int position) {

        ServiceProviders user = userArrayList.get(position);
        Glide.with(context).load(user.image).circleCrop().into(holder.dp);
        holder.name.setText(user.name);
        holder.designation.setText(user.service_type);
        holder.locality.setText(user.locality);
        holder.cv.setOnClickListener(v -> {
            Intent i = new Intent(context, ServiceProviderProfile.class);
            i.putExtra("image", user.image);
            i.putExtra("name", user.name);
            i.putExtra("address", user.address);
            i.putExtra("phone",user.phone);
            i.putExtra("email",user.email);
            i.putExtra("pincode",user.pincode);
            i.putExtra("locality",user.locality);
            i.putExtra("charging_fee",user.charging_fee);
            i.putExtra("service_type",user.service_type);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView name, designation, locality;
        ImageView dp;

         public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            cv = itemView.findViewById(R.id.cv_service_provider);
            name = itemView.findViewById(R.id.tv_userprofilename);
            designation = itemView.findViewById(R.id.tv_userprofiledesignation);
            locality = itemView.findViewById(R.id.tv_userprofilespecialiasation);
            dp = itemView.findViewById(R.id.userImage);

        }
    }
}
