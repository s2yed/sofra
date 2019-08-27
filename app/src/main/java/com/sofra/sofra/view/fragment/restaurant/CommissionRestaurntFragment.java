package com.sofra.sofra.view.fragment.restaurant;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.commissions.Commissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.LoadData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.USER_API_TOKEN;

public class CommissionRestaurntFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.fragmentCommissionRestaurantTvBuyRestaurant)
    TextView fragmentCommissionRestaurantTvBuyRestaurant;
    @BindView(R.id.fragmentCommissionRestaurantTvCommissionApps)
    TextView fragmentCommissionRestaurantTvCommissionApps;
    @BindView(R.id.fragmentCommissionRestaurantTvPaidUp)
    TextView fragmentCommissionRestaurantTvPaidUp;
    @BindView(R.id.fragmentCommissionRestaurantTvRemaining)
    TextView fragmentCommissionRestaurantTvRemaining;

    private ApiServerRestaurant apiServerRestaurant;

    private View view;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_commission_restaurant, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();


        return view;
    }

    // initializer tools
    private void inti() {

        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);
        myShowOrder();
    }
    // get   myShowOrder
    private void myShowOrder() {
 //        LoadData(getActivity(),USER_API_TOKEN);
        apiServerRestaurant.myShowOrder(LoadData(getActivity(), USER_API_TOKEN)
               ).enqueue(new Callback<Commissions>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Commissions> call, Response<Commissions> response) {
                Log.d("response", response.body().getMsg());

                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                    fragmentCommissionRestaurantTvBuyRestaurant.setText("مبيعات المطعم"+response.body().getData().getTotal());
                    fragmentCommissionRestaurantTvCommissionApps.setText("عمولة التطبيق"+response.body().getData().getCommissions());
                    fragmentCommissionRestaurantTvPaidUp.setText("ماتم سددادة"+response.body().getData().getPayments());

                    double Remaining =   response.body().getData().getCommissions() - response.body().getData().getPayments() ;

                    fragmentCommissionRestaurantTvRemaining.setText("المتبقي"+ Remaining);

                } else {
                    Log.d("response", response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<Commissions> call, Throwable t) {
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
