package com.sofra.sofra.view.fragment.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.GetOffersRestaurantClientRecyclerAdapter;
import com.sofra.sofra.data.api.ApiServerClient;
import com.sofra.sofra.data.model.getOffersClient.DataItemOffers;
import com.sofra.sofra.data.model.getOffersClient.GetOffersClient;
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

public class GetOfferRestaurantClientFragment extends Fragment {

    GetOffersRestaurantClientRecyclerAdapter offerRestaurantAdapter;
    public static List<DataItemOffers> itemOffers = new ArrayList<>();

    Unbinder unbinder;


    @BindView(R.id.getOrderRestaurantClientRvContent)
    RecyclerView getOrderRestaurantClientRvContent;



    private View view;
    private ApiServerClient apiServerClient;

    private int previousTotal = 0;
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    public int current_page = 1;
    ProgressBar orderRestaurantProgressBar;
    private Integer max = 0;
    private OnEndless onEndless;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_get_offer_restaurnt_client, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();


        onEndless();

        return view;
    }

    // initializer tools
    @SuppressLint("RestrictedApi")
    private void inti() {

        apiServerClient = getRestaurant().create(ApiServerClient.class);

        itemOffers.clear();

        orderRestaurantProgressBar = view.findViewById(R.id.getOrderRestaurantProgressBar);


    }

    // get getOffersRestaurant
    private void getOffersRestaurant(int Page) {
        orderRestaurantProgressBar.setVisibility(View.VISIBLE);
        apiServerClient.getOffers(Page)
                .enqueue(new Callback<GetOffersClient>() {
                    @Override
                    public void onResponse(Call<GetOffersClient> call, Response<GetOffersClient> response) {

                        Log.d("response", response.body().getMsg());

                        if (response.body().getStatus() == 1) {

                            max = response.body().getData().getLastPage();

                            itemOffers.addAll(response.body().getData().getData());

                            offerRestaurantAdapter.notifyDataSetChanged();

                            orderRestaurantProgressBar.setVisibility(View.INVISIBLE);

                        } else {
                            Log.d("response", response.body().getMsg());
                            orderRestaurantProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOffersClient> call, Throwable t) {
                        Log.d("response", t.getMessage());
                        orderRestaurantProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }



    // listener from count items  recyclerView
    private void onEndless() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        getOrderRestaurantClientRvContent.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {

                        getOffersRestaurant(current_page);

                }
            }
        };

        getOrderRestaurantClientRvContent.addOnScrollListener(onEndless);
        offerRestaurantAdapter = new GetOffersRestaurantClientRecyclerAdapter(itemOffers, getActivity());
        getOrderRestaurantClientRvContent.setAdapter(offerRestaurantAdapter);

        getOffersRestaurant(1);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
