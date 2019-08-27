package com.sofra.sofra.view.activity.client;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerClient;
import com.sofra.sofra.data.model.resetPassword.ResetPassword;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.setSharedPreferencesClient;
import static com.sofra.sofra.helper.HelperMathod.dismissProgressDialog;
import static com.sofra.sofra.helper.HelperMathod.showProgressDialog;


public class ForgetPasswordClientActivity extends Activity {
    @BindView(R.id.forgetPasswordActivityEditUserEmail)
    EditText forgetPasswordActivityEditUserEmail;

    @BindView(R.id.forgetPasswordActivityBtnSend)
    Button forgetPasswordActivityBtnSend;

    Unbinder unbinder;

    ApiServerClient apiServerClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);


        // initialize ShardPreferences
        setSharedPreferencesClient(ForgetPasswordClientActivity.this);

        // class login retrofit
        ForgetPassword();

    }


    // class login retrofit
    private void ForgetPassword() {

        forgetPasswordActivityBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProgressDialog(ForgetPasswordClientActivity.this,"please wait");

                apiServerClient = getRestaurant().create(ApiServerClient.class);
                apiServerClient.resetPasswordClient(forgetPasswordActivityEditUserEmail.getText().toString()).enqueue(new Callback<ResetPassword>() {
                    @Override
                    public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                        dismissProgressDialog();

                        if (response.body().getStatus() == 1) {

                            Intent intent = new Intent(
                                    ForgetPasswordClientActivity.this, NewPasswordClientActivity.class);
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
