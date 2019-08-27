package com.sofra.sofra.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofra.sofra.R;
import com.sofra.sofra.data.model.Generated.GeneratedItem;

import java.util.List;


import static com.sofra.sofra.helper.HelperMathod.LodeImageCircle;


public class MyItemsRestaurantAdapter extends BaseAdapter {

    public static List<GeneratedItem> requestBloodData;


    Activity context;


    public MyItemsRestaurantAdapter(List<GeneratedItem> postsArrayList, Activity context) {
        requestBloodData = postsArrayList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return requestBloodData.size();
    }

    @Override
    public Object getItem(int position) {
        return requestBloodData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.set_adapter_item_restaurant, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.setAdapterMyItemImgPhoto = convertView.findViewById(R.id.setAdapterMyItemImgPhoto);
            viewHolder.setAdapterNameItemsTxv = convertView.findViewById(R.id.setAdapterNameItemsTxv);
            viewHolder.setAdapterDetailsItemsTxv = convertView.findViewById(R.id.setAdapterDetailsItemsTxv);
            viewHolder.setAdapterPriceItemsTxv = convertView.findViewById(R.id.setAdapterPriceItemsTxv);
            viewHolder.setAdapterPriceOffersItemsTxv = convertView.findViewById(R.id.setAdapterPriceOffersItemsTxv);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setAdapterNameItemsTxv.setText(requestBloodData.get(i).getName());
        viewHolder.setAdapterDetailsItemsTxv.setText(requestBloodData.get(i).getDescription());
        viewHolder.setAdapterPriceItemsTxv.setText(""+requestBloodData.get(i).getPrice());

        if (requestBloodData.get(i).getHasOffer()) {

            viewHolder.setAdapterPriceItemsTxv.setBackground(context.getDrawable(R.drawable.icon_line));
            viewHolder.setAdapterPriceItemsTxv.setTextColor(Color.GRAY);
            viewHolder.setAdapterPriceOffersItemsTxv.setText("" + requestBloodData.get(i).getOfferPrice());

                Log.d("requestBloodData", "" + requestBloodData.get(i).getHasOffer() + "" + requestBloodData.get(i).getId());

        }
        else {
            viewHolder.setAdapterPriceItemsTxv.setBackground(null);
            viewHolder.setAdapterPriceItemsTxv.setTextColor(Color.BLACK);
             viewHolder.setAdapterPriceOffersItemsTxv.setText("");

        }

        // method lode image view post
        LodeImageCircle(context, requestBloodData.get(i).getPhotoUrl(),
                viewHolder.setAdapterMyItemImgPhoto);

        return convertView;
    }


    class ViewHolder {
        ImageView setAdapterMyItemImgPhoto;
        TextView setAdapterNameItemsTxv;
        TextView setAdapterDetailsItemsTxv;
        TextView setAdapterPriceItemsTxv;
        TextView setAdapterPriceOffersItemsTxv;


    }
}
