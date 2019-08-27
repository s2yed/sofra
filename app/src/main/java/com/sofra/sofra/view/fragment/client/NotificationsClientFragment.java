package com.sofra.sofra.view.fragment.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.NotificationRecyclerAdapter;
import com.sofra.sofra.data.api.ApiServerClient;
import com.sofra.sofra.data.model.notifications.DataNotify;
import com.sofra.sofra.data.model.notifications.Notifications;
 import com.sofra.sofra.helper.OnEndless;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.LoadData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.USER_API_TOKEN;

public class NotificationsClientFragment extends Fragment {

    @BindView(R.id.notificationsFragmentShowPostRecyclerView)
    RecyclerView notificationsFragmentShowPostRecyclerView;
    ProgressBar notificationsFragmentFavouriteFragmentProgBar;
    Unbinder unbinder;


    private NotificationRecyclerAdapter notificationAdapterRecycler;

    private ApiServerClient apiServer;
    private View view;
    private ArrayList<DataNotify> notificationsArrayList;
    private OnEndless onEndless;
    private Integer max;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notifications, container, false);

        unbinder = ButterKnife.bind(this, view);

        inti();

        onEndless();

        getNotifications(1);

        return view;

    }

    // initialize tools
    private void inti() {
        notificationsFragmentFavouriteFragmentProgBar = view.findViewById(R.id.notificationsFragmentFavouriteFragmentProgBar);
        notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

        notificationsArrayList = new ArrayList<>();
        apiServer = getRestaurant().create(ApiServerClient.class);


    }

    // listener from count items  recyclerView
    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        notificationsFragmentShowPostRecyclerView.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {

                    getNotifications(current_page);

                }
            }
        };

        notificationsFragmentShowPostRecyclerView.addOnScrollListener(onEndless);
        notificationAdapterRecycler = new NotificationRecyclerAdapter(notificationsArrayList, getActivity());
        notificationsFragmentShowPostRecyclerView.setAdapter(notificationAdapterRecycler);

        getNotifications(1);

    }

    // get all  post
    private void getNotifications(int i) {
        try {
            // get  PaginationData  post
            apiServer.getNotifications(LoadData(getActivity(), USER_API_TOKEN),i).enqueue(new Callback<Notifications>() {
                @Override
                public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                    try {
                        notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.VISIBLE);

                        if (response.body().getStatus() == 1) {

                            max = response.body().getData().getLastPage();

                            notificationsArrayList.addAll(response.body().getData().getData());

                            notificationAdapterRecycler.notifyDataSetChanged();

                            notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

                        } else {

                            notificationsFragmentFavouriteFragmentProgBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(getContext(), "Not PaginationData ", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        e.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<Notifications> call, Throwable t) {
                    Log.d("Throwable", t.getMessage());

                }
            });

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
