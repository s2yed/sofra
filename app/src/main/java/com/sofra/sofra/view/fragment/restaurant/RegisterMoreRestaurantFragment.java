package com.sofra.sofra.view.fragment.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.categories.Categories;
import com.sofra.sofra.data.model.restaurantRegister.RestauranttRegister;
import com.sofra.sofra.helper.MultiSelectionSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.LoadData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.USER_API_TOKEN;
import static com.sofra.sofra.helper.HelperMathod.convertFileToMultipart;
import static com.sofra.sofra.helper.HelperMathod.convertToRequestBody;

public class RegisterMoreRestaurantFragment extends Fragment {

    Unbinder unbinder;

    ApiServerRestaurant apiServerRestaurant;
    @BindView(R.id.registerMoreRestaurantActivityEditMinimumOrder)
    TextInputEditText registerMoreRestaurantActivityEditMinimumOrder;
    @BindView(R.id.registerMoreRestaurantCategoriesActivityEditDeliveryCost)
    TextInputEditText registerMoreRestaurantCategoriesActivityEditDeliveryCost;
    @BindView(R.id.registerMoreRestaurantCategoriesEditPhoneWhatsApp)
    TextInputEditText registerMoreRestaurantCategoriesEditPhoneWhatsApp;
    @BindView(R.id.registerMoreRestaurantActivityCategoriesSpinner)
    MultiSelectionSpinner registerMoreRestaurantActivityCategoriesSpinner;
    @BindView(R.id.registerMoreRestaurantActivityBtnSave)
    Button registerMoreRestaurantActivityBtnSave;
    @BindView(R.id.progressBarMoreRestaurantCategories)
    ProgressBar progressBarMoreRestaurantCategories;
    @BindView(R.id.registerMoreRestaurantSwitchState)
    Switch registerMoreRestaurantSwitchState;
    @BindView(R.id.scrollView2)
    ScrollView scrollView2;


    private int idRegions;

    private String filePath;
    List<String> items = new ArrayList<>();
    List<Integer> itemsId = new ArrayList<>();
    private View view;
    private String KeyName, KeyEmail, KeyPhone, KeyPassword, KeyPasswordEmphasise;
    private String getAvailability, getDeliveryCost, getMinimumCharger, getWhatsapp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_register_more_continues_restaureant, container, false);
        unbinder = ButterKnife.bind(this, view);

        // initializer tools
        inti();

        // DataCode Categories
        getDataCategories();


        return view;
    }


    // initialize tools
    private void inti() {

        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            KeyName = bundle.getString("Name");
            KeyEmail = bundle.getString("Email");
            KeyPhone = bundle.getString("Phone");
            idRegions = bundle.getInt("idRegions");
            KeyPassword = bundle.getString("Password");
            KeyPasswordEmphasise = bundle.getString("PasswordEmphasis");
            filePath = bundle.getString("filePath");
            getAvailability = bundle.getString("getAvailability");
            getMinimumCharger = bundle.getString("getMinimumCharger");
            getDeliveryCost = bundle.getString("getDeliveryCost");
            getWhatsapp = bundle.getString("getWhatsapp");

            registerMoreRestaurantActivityEditMinimumOrder.setText(getMinimumCharger);
            registerMoreRestaurantCategoriesActivityEditDeliveryCost.setText(getDeliveryCost);
            registerMoreRestaurantCategoriesEditPhoneWhatsApp.setText(getWhatsapp);
            if (getAvailability.equals("open")) {

                registerMoreRestaurantSwitchState.setChecked(true);
            } else {
                registerMoreRestaurantSwitchState.setChecked(true);

            }
        }

        registerMoreRestaurantSwitchState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getAvailability = "open";
                } else {
                    getAvailability = "Close";
                }
            }
        });

    }

    // get DataCode categories
    private void getDataCategories() {

        progressBarMoreRestaurantCategories.setVisibility(View.VISIBLE);

        apiServerRestaurant.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {

                if (response.body().getStatus() == 1) {

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        itemsId.add(response.body().getData().get(i).getId());
                        items.add(response.body().getData().get(i).getName());
                        registerMoreRestaurantActivityCategoriesSpinner.setItems(items, itemsId);
                    }

                    progressBarMoreRestaurantCategories.setVisibility(View.INVISIBLE);
                } else {
                    Log.d("response", response.body().getMsg());
                    progressBarMoreRestaurantCategories.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.registerMoreRestaurantActivityBtnSave)
    public void onViewClicked() {
        progressBarMoreRestaurantCategories.setVisibility(View.INVISIBLE);
        for (int i = 0; i < registerMoreRestaurantActivityCategoriesSpinner.getSelectedId().size(); i++) {
            Log.d("AsString", String.valueOf(registerMoreRestaurantActivityCategoriesSpinner.getSelectedId().get(i)));
        }
        // save data
        apiServerRestaurant.getEditRestaurantRegister(
                convertToRequestBody(KeyName)
                , convertToRequestBody(KeyEmail)
                , convertToRequestBody(KeyPassword)
                , convertToRequestBody(KeyPasswordEmphasise)
                , convertToRequestBody(KeyPhone)
                , convertToRequestBody(registerMoreRestaurantCategoriesEditPhoneWhatsApp.getText().toString())
                , convertToRequestBody(String.valueOf(idRegions)), registerMoreRestaurantActivityCategoriesSpinner.getSelectedId()
                , convertToRequestBody(registerMoreRestaurantActivityEditMinimumOrder.getText().toString())
                , convertToRequestBody(registerMoreRestaurantCategoriesActivityEditDeliveryCost.getText().toString())
                , convertFileToMultipart(filePath, "photo")
                , convertToRequestBody(LoadData(getActivity(), USER_API_TOKEN))
                , convertToRequestBody(getAvailability))
                .enqueue(new Callback<RestauranttRegister>() {
                    @Override
                    public void onResponse(Call<RestauranttRegister> call, Response<RestauranttRegister> response) {

                        progressBarMoreRestaurantCategories.setVisibility(View.VISIBLE);

                        Log.d("response", response.body().getMsg());

                        if (response.body().getStatus() == 1) {

                            progressBarMoreRestaurantCategories.setVisibility(View.VISIBLE);

                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        } else {
                            progressBarMoreRestaurantCategories.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RestauranttRegister> call, Throwable t) {
                        Log.d("onFailure", t.getMessage());

                    }
                });
    }
}
