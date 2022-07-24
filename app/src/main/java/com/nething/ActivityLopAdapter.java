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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ActivityLopAdapter extends RecyclerView.Adapter<ActivityLopAdapter.MyViewHolder> {

    Context context;
    ArrayList<ServiceProviders> list;

    public ActivityLopAdapter(Context context, ArrayList<ServiceProviders> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_activity_log,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//homework
        ServiceProviders user = list.get(position);
        holder.callname.setText(user.name);
        holder.calldesignation.setText(user.service_type);
        Glide.with(context).load(user.image).circleCrop().into(holder.callImage);
        holder.calldate.setText(user.date);
        holder.calltime.setText(user.time);




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView callImage,callLogImage;
        TextView callname,calldesignation,calldate,calltime;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            callImage = itemView.findViewById(R.id.callLogImage);
           // callLogImage=itemView.findViewById(R.id.callLog_call);
            callname=itemView.findViewById(R.id.callLog_name);
            calldesignation=itemView.findViewById(R.id.callLog_designation);
            calldate=itemView.findViewById(R.id.callLog_date);
            calltime=itemView.findViewById(R.id.callLog_time);

        }
    }
}
