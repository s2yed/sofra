package com.sofra.sofra.adapter;

import android.app.Activity;
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
import android.widget.RatingBar;
import android.widget.TextView;

import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.Generated.GeneratedDataUser;
import com.sofra.sofra.view.fragment.client.ContentRestaurantComponentClientFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.helper.HelperMathod.LodeImageCircle;
import static com.sofra.sofra.helper.HelperMathod.getStartFragments;


public class ShowRestaurantClientRecyclerAdapter extends RecyclerView.Adapter<ShowRestaurantClientRecyclerAdapter.ViewHolder> {

    List<GeneratedDataUser> ordersArrayList;

    Activity context;

    private ApiServerRestaurant apiServerRestaurant;
    private View itemLayoutView;
    private ViewHolder viewHolder;
    public static Integer idRestaurant;
    public static String PhotoUrl;
    public static  String Name;

    public ShowRestaurantClientRecyclerAdapter(List<GeneratedDataUser> ordersArrayList, Activity context) {
        this.ordersArrayList = ordersArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // create a new view layout pending
        itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.set_adapter_show_restaurant_client_recycler, null);
        // create ViewHolder
        viewHolder = new ViewHolder(itemLayoutView);


        /// get date MyItemRestaurantFragment
        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.setAdapterShowRestaurantClientTVNameRestaurant.setText(ordersArrayList.get(i).getName());
        viewHolder.setAdapterShowRestaurantClientTvMinimumRequest.setText(ordersArrayList.get(i).getMinimumCharger());
        viewHolder.setAdapterShowRestaurantClientTvDeliveryCost.setText(ordersArrayList.get(i).getDeliveryCost());
        viewHolder.setAdapterShowRestaurantClientRatingBarRestaurant.setRating(ordersArrayList.get(i).getRate());

        if (ordersArrayList.get(i).getAvailability().equals("open")) {

            viewHolder.setAdapterShowRestaurantClientImgPhotoOnline.setImageResource(R.drawable.icons_online);
            viewHolder.setAdapterShowRestaurantClientTvOnline.setText("Open");
        } else {
            viewHolder.setAdapterShowRestaurantClientImgPhotoOnline.setImageResource(R.drawable.icons_offline);
            viewHolder.setAdapterShowRestaurantClientTvOnline.setText("Close");

        }
        LodeImageCircle(context, ordersArrayList.get(i).getPhotoUrl(), viewHolder.setAdapterShowRestaurantClientImgPhotoRestaurant);

        viewHolder.setAdapterItemsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                idRestaurant = ordersArrayList.get(i).getId();
                PhotoUrl = ordersArrayList.get(i).getPhotoUrl();
                Name = ordersArrayList.get(i).getName();

                Bundle bundle = new Bundle();
                bundle.putInt("IdRestaurantFromCartOrShowAdapter", ordersArrayList.get(i).getId());


                Fragment fragment = new ContentRestaurantComponentClientFragment();
                Log.d("idRestaurantAdap", String.valueOf(ordersArrayList.get(i).getId()));

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
        @BindView(R.id.setAdapterShowRestaurantClientTVNameRestaurant)
        TextView setAdapterShowRestaurantClientTVNameRestaurant;
        @BindView(R.id.setAdapterShowRestaurantClientRatingBarRestaurant)
        RatingBar setAdapterShowRestaurantClientRatingBarRestaurant;
        @BindView(R.id.setAdapterShowRestaurantClientTvMinimumRequest)
        TextView setAdapterShowRestaurantClientTvMinimumRequest;
        @BindView(R.id.setAdapterShowRestaurantClientTvDeliveryCost)
        TextView setAdapterShowRestaurantClientTvDeliveryCost;

        @BindView(R.id.setAdapterShowRestaurantClientImgPhotoRestaurant)
        ImageView setAdapterShowRestaurantClientImgPhotoRestaurant;
        @BindView(R.id.setAdapterShowRestaurantClientImgPhotoOnline)
        ImageView setAdapterShowRestaurantClientImgPhotoOnline;
        @BindView(R.id.setAdapterShowRestaurantClientTvOnline)
        TextView setAdapterShowRestaurantClientTvOnline;
        @BindView(R.id.setAdapterItemsCardView)
        CardView setAdapterItemsCardView;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}
