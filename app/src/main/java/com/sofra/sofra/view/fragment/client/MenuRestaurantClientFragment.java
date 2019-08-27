package com.sofra.sofra.view.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.MenuRestaurantClientRecyclerAdapter;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.Generated.GeneratedItem;
import com.sofra.sofra.data.model.myItems.MyItems;
import com.sofra.sofra.helper.OnEndless;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;


public class MenuRestaurantClientFragment extends Fragment {

    MenuRestaurantClientRecyclerAdapter menuRestaurantClientRecyclerAdapter;
    public static List<GeneratedItem> userArrayList;

    Unbinder unbinder;
    @BindView(R.id.fragmentMenuRestaurantClientRvMenuRestaurant)
    RecyclerView fragmentMenuRestaurantClientRvRestaurant;
    @BindView(R.id.fragmentMenuRestaurantClientPbWait)
    ProgressBar fragmentMenuRestaurantClientPbWait;


    private View view;
    private ApiServerRestaurant apiServerRestaurant;
    private int max = 0;

    public int current_page = 1;

    private OnEndless onEndless;
    private int idRestaurant;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu_restaurant_client, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();

        // this is scroll list view
        onEndless();

        return view;
    }

    // initializer tools
    @SuppressLint("RestrictedApi")
    private void inti() {
        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);
        userArrayList = new ArrayList<>();

        // get id restaurant from return Content Component

        Bundle arguments =getArguments();
        if (arguments != null) {
            idRestaurant = arguments.getInt("idRestaurantContentComponent");
            Log.d("idRestaurantMenu", String.valueOf(idRestaurant));

        }
    }

    //   get Item Restaurant
    private void getItemRestaurant(int Page) {
        fragmentMenuRestaurantClientPbWait.setVisibility(View.VISIBLE);
        apiServerRestaurant.getItemRestaurantClient(idRestaurant, Page)
                .enqueue(new Callback<MyItems>() {
                    @Override
                    public void onResponse(Call<MyItems> call, Response<MyItems> response) {
                        try {
                            if (response.body().getStatus() == 1) {

                                max = response.body().getDataItem().getLastPage();

                                userArrayList.addAll(response.body().getDataItem().getData());

                                menuRestaurantClientRecyclerAdapter.notifyDataSetChanged();

                                fragmentMenuRestaurantClientPbWait.setVisibility(View.INVISIBLE);

                            } else {
                                Log.d("response", response.body().getMsg());
                                fragmentMenuRestaurantClientPbWait.setVisibility(View.INVISIBLE);
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }

                    }

                    @Override
                    public void onFailure(Call<MyItems> call, Throwable t) {
                        Log.d("response", t.getMessage());
                    }
                });

    }

    // listener from count items  recyclerView
    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentMenuRestaurantClientRvRestaurant.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {
                    getItemRestaurant(current_page);
                }
            }
        };

        fragmentMenuRestaurantClientRvRestaurant.addOnScrollListener(onEndless);
        menuRestaurantClientRecyclerAdapter = new MenuRestaurantClientRecyclerAdapter(userArrayList, getActivity());
        fragmentMenuRestaurantClientRvRestaurant.setAdapter(menuRestaurantClientRecyclerAdapter);

        getItemRestaurant(1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
