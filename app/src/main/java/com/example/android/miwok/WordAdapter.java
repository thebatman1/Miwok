package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 2/24/2017.
 */

public class WordAdapter extends ArrayAdapter<Words> {


    private int colorResourceId;

    public WordAdapter(Context context , ArrayList<Words> arrayList , int colorResourceId) {
        super(context , 0 , arrayList);
        this.colorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item , parent , false);

            holder = new ViewHolder();
            holder.miwok = (TextView) convertView.findViewById(R.id.tv1);
            holder.english = (TextView) convertView.findViewById(R.id.tv2);
            holder.image = (ImageView) convertView.findViewById(R.id.li_image);

            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Words words = getItem(position);
        holder.miwok.setText(words.getMiwok());
        holder.english.setText(words.getEnglish());
        if(words.hasImage()) {
            holder.image.setImageResource(words.getImage_id());
            holder.image.setVisibility(View.VISIBLE);
        }
        else {
            holder.image.setVisibility(View.GONE);
        }

        View container = convertView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext() , colorResourceId);
        container.setBackgroundColor(color);

        return convertView;

    }

    public static class ViewHolder {
        TextView english , miwok;
        ImageView image;
    }
}
