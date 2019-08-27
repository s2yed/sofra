package com.sofra.sofra.view.fragment.client;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.AdapterListItemsOrder;
import com.sofra.sofra.data.api.ApiServerClient;
import com.sofra.sofra.data.model.showOrder.ShowOrder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;

import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.LoadData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.USER_API_TOKEN;
import static com.sofra.sofra.helper.HelperMathod.LodeImageCircle;

public class DetailsOrderRestaurantClientFragment extends Fragment {


    Unbinder unbinder;

    @BindView(R.id.showOrderRestaurantClientFragmentImgPhotoRestaurant)
    ImageView showOrderRestaurantClientFragmentImgPhotoRestaurant;
    @BindView(R.id.showOrderRestaurantClientFragmentTvNameRestaurant)
    TextView showOrderRestaurantClientFragmentTvNameRestaurant;
    @BindView(R.id.showOrderRestaurantClientFragmentTvDateOrderRestaurant)
    TextView showOrderRestaurantClientFragmentTvDateOrderRestaurant;
    @BindView(R.id.showOrderRestaurantClientFragmentTvAddressRestaurant)
    TextView showOrderRestaurantClientFragmentTvAddressRestaurant;
    @BindView(R.id.showOrderRestaurantClientFragmentTvOrderDetailsRestaurant)
    ListView showOrderRestaurantClientFragmentTvOrderDetailsRestaurant;
    @BindView(R.id.showOrderRestaurantClientFragmentTvPriceRestaurant)
    TextView showOrderRestaurantClientFragmentTvPriceRestaurant;
    @BindView(R.id.showOrderRestaurantClientFragmentTvDeliveryCostRestaurant)
    TextView showOrderRestaurantClientFragmentTvDeliveryCostRestaurant;
    @BindView(R.id.showOrderRestaurantClientFragmentTvTotalRestaurant)
    TextView showOrderRestaurantClientFragmentTvTotalRestaurant;
    @BindView(R.id.showOrderRestaurantClientFragmentTvPayingRestaurant)
    TextView showOrderRestaurantClientFragmentTvPayingRestaurant;
    @BindView(R.id.showOrderRestaurantClientFragmentItemProgressBar)
    ProgressBar showOrderRestaurantClientFragmentItemProgressBar;

    private ApiServerClient apiServerClient;

    private int getId;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.show_order_restaurant_client_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();


        return view;
    }

    // initializer tools
    private void inti() {
        /// get date MyItemRestaurantFragment
        apiServerClient = getRestaurant().create(ApiServerClient.class);

        showOrderRestaurantClientFragmentItemProgressBar.setVisibility(View.INVISIBLE);

        /// get data from Orders Restaurant Client Recycler Adapter or     Confirm Order Details Restaurant Client Fragment
        Bundle getBundle = getArguments();
        if (getBundle != null) {
            getId = getBundle.getInt("getId");
           }

        showOrderRestaurantClient();
    }

    private void showOrderRestaurantClient() {

        showOrderRestaurantClientFragmentItemProgressBar.setVisibility(View.VISIBLE);
//        LoadData(getActivity(),USER_API_TOKEN);

        apiServerClient.myShowOrder(LoadData(getActivity(), USER_API_TOKEN), getId).enqueue(new Callback<ShowOrder>() {
            @Override
            public void onResponse(Call<ShowOrder> call, Response<ShowOrder> response) {
                try {
                    Log.d("response", response.body().getMsg());

                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                        LodeImageCircle(getContext(), response.body().getData().getRestaurant().getPhotoUrl(), showOrderRestaurantClientFragmentImgPhotoRestaurant);

                        showOrderRestaurantClientFragmentTvNameRestaurant.setText(response.body().getData().getRestaurant().getName());
                        showOrderRestaurantClientFragmentTvDateOrderRestaurant.setText(response.body().getData().getUpdatedAt());
                        showOrderRestaurantClientFragmentTvAddressRestaurant.setText(response.body().getData().getAddress());
                        showOrderRestaurantClientFragmentTvPriceRestaurant.setText(response.body().getData().getCost());
                        showOrderRestaurantClientFragmentTvDeliveryCostRestaurant.setText(response.body().getData().getDeliveryCost());
                        showOrderRestaurantClientFragmentTvTotalRestaurant.setText(response.body().getData().getTotal());

                        if (response.body().getData().getPaymentMethodId().equals("1")) {
                            showOrderRestaurantClientFragmentTvPayingRestaurant.setText(getResources().getString(R.string.cach));
                        }else {
                            showOrderRestaurantClientFragmentTvPayingRestaurant.setText(getResources().getString(R.string.on_line));
                        }

                        AdapterListItemsOrder adapterListItemsOrder = new AdapterListItemsOrder(getContext(), response.body().getData().getItems());
                        showOrderRestaurantClientFragmentTvOrderDetailsRestaurant.setAdapter(adapterListItemsOrder);

                        showOrderRestaurantClientFragmentItemProgressBar.setVisibility(View.INVISIBLE);

                    } else {
                        Log.d("response", response.body().getMsg());
                        showOrderRestaurantClientFragmentItemProgressBar.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<ShowOrder> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
