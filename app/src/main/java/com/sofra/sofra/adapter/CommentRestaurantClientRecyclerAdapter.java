package com.sofra.sofra.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.showReviews.DataComment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.helper.HelperMathod.LodeImageCircle;


public class CommentRestaurantClientRecyclerAdapter extends RecyclerView.Adapter<CommentRestaurantClientRecyclerAdapter.ViewHolder> {

    List<DataComment> dataCommentList;

    Activity context;

    private ApiServerRestaurant apiServerRestaurant;
    String keyRequest;
    private View itemLayoutView;
    private ViewHolder viewHolder;

    public CommentRestaurantClientRecyclerAdapter(List<DataComment> dataCommentList, Activity context) {
        this.dataCommentList = dataCommentList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // create a new view layout pending
        itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.set_adapter_comment_restaurant_client_recycler, null);
        // create ViewHolder
        viewHolder = new ViewHolder(itemLayoutView);


        /// get date MyItemRestaurantFragment
        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.setAdapterCommentRestaurantClientTVNameUser.setText(dataCommentList.get(i).getClient().getName());
        viewHolder.setAdapterCommentRestaurantClientTvComment.setText(dataCommentList.get(i).getComment());

        if (dataCommentList.get(i).getRate().equals("1")) {
            viewHolder.setAdapterCommentRestaurantClientImgPhotoStatus.setImageResource(R.drawable.angry_active1);
        } else if (dataCommentList.get(i).getRate().equals("2")) {
            viewHolder.setAdapterCommentRestaurantClientImgPhotoStatus.setImageResource(R.drawable.sad_active2);

        } else if (dataCommentList.get(i).getRate().equals("3")) {
            viewHolder.setAdapterCommentRestaurantClientImgPhotoStatus.setImageResource(R.drawable.smiling_active3);

        } else if (dataCommentList.get(i).getRate().equals("4")) {
            viewHolder.setAdapterCommentRestaurantClientImgPhotoStatus.setImageResource(R.drawable.smiley_active4);

        } else if (dataCommentList.get(i).getRate().equals("5")) {
            viewHolder.setAdapterCommentRestaurantClientImgPhotoStatus.setImageResource(R.drawable.love_active5);

        }


    }


    @Override
    public int getItemCount() {
        return dataCommentList.size();
    }


    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        @BindView(R.id.setAdapterCommentRestaurantClientTVNameUser)
        TextView setAdapterCommentRestaurantClientTVNameUser;
        @BindView(R.id.setAdapterCommentRestaurantClientTvComment)
        TextView setAdapterCommentRestaurantClientTvComment;
        @BindView(R.id.setAdapterCommentRestaurantClientImgPhotoStatus)
        ImageView setAdapterCommentRestaurantClientImgPhotoStatus;


        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}
