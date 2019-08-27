package com.sofra.sofra.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sofra.sofra.R;
import com.sofra.sofra.view.activity.client.LoginClientActivity;
import com.sofra.sofra.view.activity.client.MainClient;
import com.sofra.sofra.view.activity.restaurant.LoginRestaurantActivity;
import com.sofra.sofra.view.activity.restaurant.MainRestaurant;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.setSharedPreferencesClient;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.KEY_IS_CHECK_BOX;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.LoadBooleanRestaurant;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.LoadBooleanClient;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.setSharedPreferencesRestaurant;

public class MainHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        // initialize ShardPreferences
        setSharedPreferencesRestaurant(MainHomeActivity.this);
        setSharedPreferencesClient(MainHomeActivity.this);
    }

    @OnClick({R.id.mainOrderFoodBtn, R.id.mainBuyFoodBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mainOrderFoodBtn:
                // check is checkBox is Checked
//                if (LoadBooleanClient(MainHomeActivity.this, KEY_IS_CHECK_BOX)) {
//                    startActivity(new Intent(MainHomeActivity.this, MainClient.class));
//                } else {
//                    startActivity(new Intent(MainHomeActivity.this, LoginClientActivity.class));
//                }
                startActivity(new Intent(MainHomeActivity.this, MainClient.class));

                break;
            case R.id.mainBuyFoodBtn:
                // check is checkBox is Checked
                if (LoadBooleanRestaurant(MainHomeActivity.this, KEY_IS_CHECK_BOX)) {
                    startActivity(new Intent(MainHomeActivity.this, MainRestaurant.class));
                } else {
                    startActivity(new Intent(MainHomeActivity.this, LoginRestaurantActivity.class));
                }
                break;
        }
    }
}
