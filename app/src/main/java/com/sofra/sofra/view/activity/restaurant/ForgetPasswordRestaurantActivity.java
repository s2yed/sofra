package com.sofra.sofra.view.activity.restaurant;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerRestaurant;
 import com.sofra.sofra.data.model.resetPassword.ResetPassword;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.setSharedPreferencesRestaurant;


public class ForgetPasswordRestaurantActivity extends Activity {
    View view;
    @BindView(R.id.forgetPasswordActivityEditUserEmail)
    EditText forgetPasswordActivityEditUserEmail;

    @BindView(R.id.forgetPasswordActivityBtnSend)
    Button forgetPasswordActivityBtnSend;

    Unbinder unbinder;

    ApiServerRestaurant apiServerRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);


        // initialize ShardPreferences
       setSharedPreferencesRestaurant(ForgetPasswordRestaurantActivity.this);

        // class login retrofit
        ForgetPassword();

    }


    // class login retrofit
    private void ForgetPassword() {

        forgetPasswordActivityBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);
                Call<ResetPassword> call = apiServerRestaurant.resetPassword(forgetPasswordActivityEditUserEmail.getText().toString());
                call.enqueue(new Callback<ResetPassword>() {
                    @Override
                    public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {

                        Toast.makeText(ForgetPasswordRestaurantActivity.this, String.valueOf(response.body().getDataCode().getCode()), Toast.LENGTH_SHORT).show();

                        if (response.body().getStatus() == 1) {

                            Intent intent = new Intent(ForgetPasswordRestaurantActivity.this, NewPasswordRestaurantActivity.class);
                            intent.putExtra("getPinCodeForTest", String.valueOf(response.body().getDataCode().getCode()));
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResetPassword> call, Throwable t) {

                    }
                });
            }
        });
    }

}
