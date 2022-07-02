package com.nething;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    Context context;
    String[] title;
    int[] img;

    LayoutInflater inflater;

    public GridAdapter(Context context, String[] title, int[] img) {
        this.context = context;
        this.title = title;
        this.img = img;
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = inflater.inflate(R.layout.item_category, null);
        }

        ImageView imageView = view.findViewById(R.id.imageviewId);
        TextView textView = view.findViewById(R.id.textviewId);

        imageView.setImageResource(img[i]);
        textView.setText(title[i]);

        return view;
    }
}
