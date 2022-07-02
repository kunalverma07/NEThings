package com.nething;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class myAdapterServiceProvider extends RecyclerView.Adapter<myAdapterServiceProvider.MyViewHolder> {

    Context context;
    ArrayList<ServiceProviders> userArrayList;

    public myAdapterServiceProvider(Context context, ArrayList<ServiceProviders> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public myAdapterServiceProvider.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_service_providers,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapterServiceProvider.MyViewHolder holder, int position) {

        ServiceProviders user = userArrayList.get(position);
        holder.name.setText(user.name);
        holder.designation.setText(user.designation);
        holder.locality.setText(user.locality);
        //Glide.with(context).load(user.Image ).into(holder.Image);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, designation, locality;
        ImageView dp;

         public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_userprofilename);
            designation = itemView.findViewById(R.id.tv_userprofiledesignation);
            locality = itemView.findViewById(R.id.tv_userprofilespecialiasation);
            dp = itemView.findViewById(R.id.userImage);

        }
    }
}
