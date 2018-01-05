package com.example.android.mogifortourists;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dkots on 08/11/2017.
 */

public class ItemAdapter extends ArrayAdapter<Location> {

    private Context mContext;

    public ItemAdapter(Activity context, ArrayList<Location> word) {
        super(context, 0, word);
        mContext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_layout, parent, false);
        }


        Location currentItem = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.item_title_text_view);
        titleTextView.setText(mContext.getResources().getString(currentItem.getTitleStringId())+"");

        String text = mContext.getResources().getString(currentItem.getTextStringId()).substring
                (0,90);
        TextView textTextView = (TextView) listItemView.findViewById(R.id.item_text_text_view);
        textTextView.setText(text+"[...]");

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.item_image_view);
        imageView.setImageResource(currentItem.getImageResourceId());

        return listItemView;
    }
}
