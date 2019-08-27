package com.sofra.sofra.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.getOffersClient.DataItemOffers;
import com.sofra.sofra.view.fragment.client.AddItemCartRestaurantClientFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.helper.HelperMathod.LodeImage;
import static com.sofra.sofra.helper.HelperMathod.getStartFragments;


public class GetOffersRestaurantClientRecyclerAdapter extends RecyclerView.Adapter<GetOffersRestaurantClientRecyclerAdapter.ViewHolder> {

    List<DataItemOffers> ordersArrayList;

    Activity context;


    private View itemLayoutView;
    private ViewHolder viewHolder;

    public GetOffersRestaurantClientRecyclerAdapter(List<DataItemOffers> ordersArrayList, Activity context) {
        this.ordersArrayList = ordersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // create a new view layout pending
        itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.
                set_adapter_get_offers_restaurant_client_recycler, null);
        // create ViewHolder
        viewHolder = new ViewHolder(itemLayoutView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.setAdapterNewOfferTxv.setText(ordersArrayList.get(i).getName());


        LodeImage(context, ordersArrayList.get(i).getPhotoUrl(), viewHolder.setAdapterNewOfferImgPhoto);

    }


    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }


    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        @BindView(R.id.setAdapterNewOfferTxv)
        TextView setAdapterNewOfferTxv;
        @BindView(R.id.setAdapterNewOfferImgPhoto)
        ImageView setAdapterNewOfferImgPhoto;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}
