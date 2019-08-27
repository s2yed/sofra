package com.sofra.sofra.view.fragment.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.OrdersRestaurantClientRecyclerAdapter;
import com.sofra.sofra.data.api.ApiServerClient;
import com.sofra.sofra.data.model.myOrders.Datum;
import com.sofra.sofra.data.model.myOrders.MyOrders;
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

public class OrdersRequestsRestaurantClientFragment extends Fragment {


    public RequestsOrderRestaurantClientComponentFragment requestsRestaurantComponentFragment;
    Unbinder unbinder;
    @BindView(R.id.RequestsRestaurantClientFragmentRequestRecyclerView)
    RecyclerView RequestsRestaurantClientFragmentRequestRecyclerView;

    @BindView(R.id.RequestsRestaurantClientFragmentRequestSwipeRefresh)
    SwipeRefreshLayout RequestsRestaurantClientFragmentRequestSwipeRefresh;

    public OrdersRestaurantClientRecyclerAdapter restaurantClientRecyclerAdapter;
    private View view;
    private OnEndless onEndless;

    public ArrayList<Datum> ordersArrayList = new ArrayList<>();
    private int max;
    private ApiServerClient apiServerClient;
    private String KeyRequest;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_requests_restaurnt_client, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();

        // listener from count items  recyclerView
        onEndless();

        SwipeRefresh();
        return view;
    }

    // initializer tools
    private void inti() {
        /// get date MyItemRestaurantFragment
        apiServerClient = getRestaurant().create(ApiServerClient.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            KeyRequest = bundle.getString("KeyRequest");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ordersArrayList.clear();
    }

    // listener from count items  recyclerView
    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RequestsRestaurantClientFragmentRequestRecyclerView.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 10) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {
                    geNewOrder(current_page);
                }
            }
        };

        RequestsRestaurantClientFragmentRequestRecyclerView.addOnScrollListener(onEndless);

        restaurantClientRecyclerAdapter =
                new OrdersRestaurantClientRecyclerAdapter(ordersArrayList, getActivity(), requestsRestaurantComponentFragment, KeyRequest);
        RequestsRestaurantClientFragmentRequestRecyclerView.setAdapter(restaurantClientRecyclerAdapter);


        geNewOrder(1);

    }


    // ge New Order
    private void geNewOrder(int current_page) {
         RequestsRestaurantClientFragmentRequestSwipeRefresh.setRefreshing(true);
          //delivered pending
        apiServerClient.getMyOrders(LoadData(getActivity(), USER_API_TOKEN), KeyRequest, current_page)
                .enqueue(new Callback<MyOrders>() {
                    @Override
                    public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                        try {
                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            if (response.body().getStatus() == 1) {

                                max = response.body().getData().getLastPage();

                                // add All
                                ordersArrayList.addAll(response.body().getData().getData());

                                restaurantClientRecyclerAdapter.notifyDataSetChanged();

                                //  set Visibility INVISIBLE
                                RequestsRestaurantClientFragmentRequestSwipeRefresh.setRefreshing(false);

                            } else {

                                Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                RequestsRestaurantClientFragmentRequestSwipeRefresh.setRefreshing(false);

                            }
                        } catch (Exception e) {

                            e.getMessage();
                        }


                    }

                    @Override
                    public void onFailure(Call<MyOrders> call, Throwable t) {
                        RequestsRestaurantClientFragmentRequestSwipeRefresh.setRefreshing(false);

                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //  swipeRefresh All list
    private void SwipeRefresh() {

        RequestsRestaurantClientFragmentRequestSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ordersArrayList.clear();
                geNewOrder(1);


            }
        });
    }
}
