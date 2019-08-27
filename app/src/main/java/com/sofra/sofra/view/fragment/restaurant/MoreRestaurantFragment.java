package com.sofra.sofra.view.fragment.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sofra.sofra.R;
import com.sofra.sofra.adapter.MoreAdapter;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.Generated.GeneratedModelMore;
import com.sofra.sofra.data.model.notifyToken.NotifyToken;
import com.sofra.sofra.view.activity.MainHomeActivity;
import com.sofra.sofra.view.fragment.ContactMeRestaurantFragment;
import com.sofra.sofra.view.fragment.client.AboutRestaurantClientFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.LoadData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.USER_API_TOKEN;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.clean;
import static com.sofra.sofra.helper.HelperMathod.getStartFragments;

public class MoreRestaurantFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.fragmentMoreListViewRestaurant)
    ListView fragmentMoreListViewRestaurant;

    private View view;
    ApiServerRestaurant apiServerRestaurant;

    List<GeneratedModelMore> generatedModelMores = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_more_restaurant, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();


        return view;
    }

    // initializer tools
    private void inti() {
        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);

        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_offer, "Offer"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_connect, "Connect with us"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_about, "About App"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_rate, "Rate And comments"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_logout, "logout"));

        MoreAdapter moreAdapter = new MoreAdapter(getContext(), generatedModelMores);
        fragmentMoreListViewRestaurant.setAdapter(moreAdapter);

        fragmentMoreListViewRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.mainRestaurantReplaceFragment, new OfferRestaurantFragment());
                    }
                }
                else  if (position == 1) {
                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.mainRestaurantReplaceFragment, new ContactMeRestaurantFragment());
                    }
                }
                else  if (position == 2) {
                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.mainRestaurantReplaceFragment, new AboutRestaurantClientFragment());
                    }
                }else if (position == 4) {
                    clean(getActivity());
                    RemoveToken();
                    startActivity(new Intent(getActivity(), MainHomeActivity.class));
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    //send Remove Token
    public void RemoveToken() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Remove Token
        apiServerRestaurant.RemoveToken(refreshedToken,
                LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<NotifyToken>() {
            @Override
            public void onResponse(Call<NotifyToken> call, Response<NotifyToken> response) {

                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(getApplication(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<NotifyToken> call, Throwable t) {

            }
        });

    }
}
