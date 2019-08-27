package com.sofra.sofra.view.activity.restaurant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sofra.sofra.R;

 import com.sofra.sofra.view.fragment.restaurant.CommissionRestaurntFragment;
import com.sofra.sofra.view.fragment.restaurant.EditProfileRegisterRestaurantFragment;
import com.sofra.sofra.view.fragment.restaurant.MoreRestaurantFragment;
import com.sofra.sofra.view.fragment.restaurant.MyItemRestaurantFragment;
import com.sofra.sofra.view.fragment.restaurant.RequestsRestaurantComponentFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.LoadData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.USER_NAME;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.setSharedPreferencesRestaurant;
import static com.sofra.sofra.helper.HelperMathod.ToolBarRestaurant;
import static com.sofra.sofra.helper.HelperMathod.getStartFragments;

public class MainRestaurant extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    getStartFragments(getSupportFragmentManager(), R.id.mainRestaurantReplaceFragment, new MyItemRestaurantFragment());
                    return true;
                case R.id.navigation_User:
                    getStartFragments(getSupportFragmentManager()
                            , R.id.mainRestaurantReplaceFragment, new EditProfileRegisterRestaurantFragment());
                    return true;
                case R.id.navigation_requests:
                    getStartFragments(getSupportFragmentManager(), R.id.mainRestaurantReplaceFragment, new RequestsRestaurantComponentFragment());

                    return true;
                case R.id.sitting:
                    getStartFragments(getSupportFragmentManager(), R.id.mainRestaurantReplaceFragment, new MoreRestaurantFragment());



                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_restaurant);
        ButterKnife.bind(this);

        BottomNavigationView navView = findViewById(R.id.btn_nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //initialize ShardPreferences
        setSharedPreferencesRestaurant(MainRestaurant.this);
        // initialize toolbar
        ToolBarRestaurant(getSupportFragmentManager(), this, toolbar, LoadData(MainRestaurant.this, USER_NAME));
        toolbar.setNavigationIcon(getDrawable(R.drawable.icon_calculator));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStartFragments(getSupportFragmentManager(), R.id.mainRestaurantReplaceFragment
                        , new CommissionRestaurntFragment());
            }
        });
        getStartFragments(getSupportFragmentManager(), R.id.mainRestaurantReplaceFragment, new MyItemRestaurantFragment());

    }

//
//    // get Notifications Count
//    public void getNotificationsCount() {
//        try {
//            ApiServer apiServer = getClient().create(ApiServer.class);
//            // get  PaginationData  post
//            apiServer.getNotificationsCount(LoadData(HomeNavgation.this, USER_API_TOKEN)).enqueue(new Callback<NotificationsCount>() {
//
//                @Override
//                public void onResponse(Call<NotificationsCount> call, Response<NotificationsCount> response) {
//
////                    Log.d(" notifications 5", response.body().getMsg());
//
//                    if (response.body().getStatus() == 1) {
//
//                        getNotificationsCount = String.valueOf(response.body().getDataItem().getNotificationsCount());
//
//                        textCartItemCount.setText(getNotificationsCount);
//
//
//                    } else {
//                        Toast.makeText(HomeNavgation.this, "Not PaginationData ", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<NotificationsCount> call, Throwable t) {
//                    Toast.makeText(HomeNavgation.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        } catch (Exception e) {
//            e.getMessage();
//        }
//    }
}
