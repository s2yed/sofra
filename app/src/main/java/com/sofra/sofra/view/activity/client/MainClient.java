package com.sofra.sofra.view.activity.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sofra.sofra.R;
import com.sofra.sofra.helper.HelperMathod;
import com.sofra.sofra.view.fragment.client.AllRestaurantClientFragment;
import com.sofra.sofra.view.fragment.client.CartItemRestaurantClientFragment;
 import com.sofra.sofra.view.fragment.client.MoreRestaurantClientFragment;
import com.sofra.sofra.view.fragment.client.RequestsOrderRestaurantClientComponentFragment;
import com.sofra.sofra.view.fragment.client.UpdateProfileRegisterClientFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.LoadData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.USER_NAME;
import static com.sofra.sofra.helper.HelperMathod.ToolBarClient;
import static com.sofra.sofra.helper.HelperMathod.getStartFragments;

public class MainClient extends AppCompatActivity {

    int checkopenLogin = 0;
    public CartItemRestaurantClientFragment cartItemRestaurantClientFragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getStartFragments(getSupportFragmentManager(), R.id.mainClientReplaceFragment, new AllRestaurantClientFragment());
                    return true;
                case R.id.navigation_requests:
                    getStartFragments(getSupportFragmentManager(), R.id.mainClientReplaceFragment, new RequestsOrderRestaurantClientComponentFragment());

                    return true;
                case R.id.navigation_User:
                    getStartFragments(getSupportFragmentManager(), R.id.mainClientReplaceFragment, new UpdateProfileRegisterClientFragment());

                    return true;
                case R.id.sitting:
                    getStartFragments(getSupportFragmentManager(), R.id.mainClientReplaceFragment, new MoreRestaurantClientFragment());

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        ButterKnife.bind(this);

        BottomNavigationView navView = findViewById(R.id.btn_nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        HelperMathod.ToolBarClient(getSupportFragmentManager(), this, toolbar, LoadData(MainClient.this, USER_NAME));
        toolbar.setNavigationIcon(getDrawable(R.drawable.shopping_cart));

        cartItemRestaurantClientFragment = new CartItemRestaurantClientFragment();
        cartItemRestaurantClientFragment.mainClient = this;

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStartFragments(getSupportFragmentManager(), R.id.mainClientReplaceFragment
                        , cartItemRestaurantClientFragment);
            }
        });

        Intent openLogin = getIntent();
            if (openLogin != null) {
                checkopenLogin  = openLogin.getIntExtra("openLogin",0);
            }

        if (checkopenLogin == 0) {
            getStartFragments(getSupportFragmentManager(), R.id.mainClientReplaceFragment, new AllRestaurantClientFragment());
        } else {
            getStartFragments(getSupportFragmentManager(), R.id.mainClientReplaceFragment, new CartItemRestaurantClientFragment());
            checkopenLogin = 0;
        }


    }

}
