package com.sofra.sofra.adapter;

import android.annotation.SuppressLint;
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
import com.sofra.sofra.data.model.Generated.GeneratedItem;
import com.sofra.sofra.view.fragment.client.AddItemCartRestaurantClientFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.helper.HelperMathod.LodeImage;
import static com.sofra.sofra.helper.HelperMathod.getStartFragments;


public class MenuRestaurantClientRecyclerAdapter extends RecyclerView.Adapter<MenuRestaurantClientRecyclerAdapter.ViewHolder> {

    List<GeneratedItem> ordersArrayList;

    Activity context;



    private ApiServerRestaurant apiServerRestaurant;
    String keyRequest;
    private View itemLayoutView;
    private ViewHolder viewHolder;

    public MenuRestaurantClientRecyclerAdapter(List<GeneratedItem> ordersArrayList, Activity context) {
        this.ordersArrayList = ordersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // create a new view layout pending
        itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_adapter_menu_restaurant_client_recycler, null);
        // create ViewHolder
        viewHolder = new ViewHolder(itemLayoutView);


        /// get date MyItemRestaurantFragment
        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);


        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.setAdapterMenuRestaurantClientTxvNameItems.setText(ordersArrayList.get(i).getName());
        viewHolder.setAdapterMenuRestaurantClientTxvDetailsItems.setText(ordersArrayList.get(i).getDescription());
        viewHolder.setAdapterMenuRestaurantClientTxvPriceItems.setText(""+ordersArrayList.get(i).getPrice());

        if (ordersArrayList.get(i).getHasOffer()) {

            viewHolder.setAdapterMenuRestaurantClientTxvPriceItems.setBackground(context.getDrawable(R.drawable.icon_line));
            viewHolder.setAdapterMenuRestaurantClientTxvPriceItems.setTextColor(Color.GRAY);
            viewHolder.setAdapterMenuRestaurantClientTxvOffersPriceItems.setText( ordersArrayList.get(i).getOfferPrice());
            viewHolder.setAdapterMenuRestaurantClientTxvOffersPriceItems.setTextColor(Color.RED);

            Log.d("requestBloodData", "" + ordersArrayList.get(i).getHasOffer() + "" + ordersArrayList.get(i).getId());
        }
        else {
            viewHolder.setAdapterMenuRestaurantClientTxvPriceItems.setBackground(null);
            viewHolder.setAdapterMenuRestaurantClientTxvPriceItems.setTextColor(Color.BLACK);
            viewHolder.setAdapterMenuRestaurantClientTxvOffersPriceItems.setText("");
        }

        LodeImage(context, ordersArrayList.get(i).getPhotoUrl(), viewHolder.setAdapterMenuRestaurantClientIvPhotoItems);

        viewHolder.setAdapterMenuRestaurantClientCvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("getIdItem",ordersArrayList.get(i).getId());
                bundle.putString("getName",ordersArrayList.get(i).getName());
                bundle.putString("getDescription",ordersArrayList.get(i).getDescription());
                bundle.putString("getPrice",""+ordersArrayList.get(i).getPrice());
                bundle.putString("getPreparingTime",ordersArrayList.get(i).getPreparingTime());
                bundle.putString("getPhotoUrl",ordersArrayList.get(i).getPhotoUrl());
                Fragment fragment = new AddItemCartRestaurantClientFragment();
                fragment.setArguments(bundle);
                getStartFragments(((FragmentActivity) context).getSupportFragmentManager(), R.id.mainClientReplaceFragment, fragment);

            }
        });
    }


    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }


    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        @BindView(R.id.setAdapterMenuRestaurantClientIvPhotoItems)
        ImageView setAdapterMenuRestaurantClientIvPhotoItems;
        @BindView(R.id.setAdapterMenuRestaurantClientCvContainer)
        CardView setAdapterMenuRestaurantClientCvContainer;
        @BindView(R.id.setAdapterMenuRestaurantClientTxvNameItems)
        TextView setAdapterMenuRestaurantClientTxvNameItems;
        @BindView(R.id.setAdapterMenuRestaurantClientTxvDetailsItems)
        TextView setAdapterMenuRestaurantClientTxvDetailsItems;
        @BindView(R.id.setAdapterMenuRestaurantClientTxvPriceItems)
        TextView setAdapterMenuRestaurantClientTxvPriceItems;
        @BindView(R.id.setAdapterMenuRestaurantClientTxvOffersPriceItems)
        TextView setAdapterMenuRestaurantClientTxvOffersPriceItems;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}
