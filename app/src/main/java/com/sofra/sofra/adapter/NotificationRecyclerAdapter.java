package com.sofra.sofra.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.notifications.DataNotify;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;


public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder> {

    ArrayList<DataNotify> postsArrayList;

    private ApiServerRestaurant apiServer;
    Activity context;
    private boolean numFavorite = true;
    private int postion;

    public NotificationRecyclerAdapter(ArrayList<DataNotify> postsArrayList, Activity context) {
        this.postsArrayList = postsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.set_adapter_recycler_notifcation, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        apiServer = getRestaurant().create(ApiServerRestaurant.class);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        // set title
        viewHolder.notificationAdapterTitleTxt.setText(postsArrayList.get(i).getTitle());
        viewHolder.notificationAdapterTimeTxt.setText(postsArrayList.get(i).getUpdatedAt());
//
//        if (postsArrayList.get(i).getPivotNotify().getIsRead().equals("0")) {
//            viewHolder.notification_adapter.setImageResource(R.drawable.icon_notify);
//        } else {
//            viewHolder.notification_adapter.setImageResource(R.drawable.icon_un_notify);
//        }

        viewHolder.notificationAdapterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//
//                bundle.putString("getDonationRequestId", postsArrayList.get(i).getDonationRequestId());
//                bundle.putInt("returnResult", 1);
//                Fragment fragment = new ContentDonationFragment();
//                fragment.setArguments(bundle);
//                getStartFragments(((FragmentActivity) context).getSupportFragmentManager(), R.id.mainRestaurantReplaceFragment
//                        , fragment);
             }
        });
    }


    @Override
    public int getItemCount() {
        return postsArrayList.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_adapter)
        ImageView notification_adapter;
        @BindView(R.id.notificationAdapterTimeTxt)
        TextView notificationAdapterTimeTxt;
        @BindView(R.id.notificationAdapterTitleTxt)
        TextView notificationAdapterTitleTxt;
        @BindView(R.id.notificationAdapterLayout)
        LinearLayout notificationAdapterLayout;

        View view;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }
    }

}
