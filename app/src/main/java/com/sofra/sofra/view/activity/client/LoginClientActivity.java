package com.sofra.sofra.view.activity.client;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerClient;
import com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant;
import com.sofra.sofra.data.model.loginClient.ClientLogin;
import com.sofra.sofra.data.model.notifyToken.NotifyToken;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.KEY_IS_CHECK_BOX;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.Key_password;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.LoadBooleanClient;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.LoadData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.SaveData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.USER_API_TOKEN;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.USER_EMAIL;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.USER_ID;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.USER_NAME;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.USER_PHONE;
import static com.sofra.sofra.data.local.SharedPreferncesMangerClient.setSharedPreferencesClient;


import static com.sofra.sofra.helper.HelperMathod.getStartFragments;


public class LoginClientActivity extends AppCompatActivity {


    @BindView(R.id.loginActivityEditUserPassword)
    EditText loginFragmentEditUserPassword;
    @BindView(R.id.loginActivityTxtForgetPassword)
    TextView loginFragmentTxtForgetPassword;
    @BindView(R.id.loginActivityBtnLogin)
    Button loginFragmentBtnLogin;
    TextView loginActivityTvRegister;
    Unbinder unbinder;
    @BindView(R.id.loginActivityChkBox)
    CheckBox loginFragmentChkBox;

    ApiServerClient apiServerClient;
    ProgressBar loginActivityProgressBar;
    @BindView(R.id.loginActivityEditUserEmail)
    EditText loginFragmentEditUserEmail;

    private boolean Checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //initializer
        inti();
        // initialize ShardPreferences
        setSharedPreferencesClient(this);
        // Class All OnClick
        ClassAllOnClick();
        // get data all GeneratedDataUser
        getDataUserShrPreferences();

        // class login retrofit
        ClassLogin();
    }


    private void inti() {
        loginActivityProgressBar = findViewById(R.id.loginActivityProgressBar);
        loginActivityTvRegister = findViewById(R.id.loginActivityTvRegister);
    }

    private void ClassAllOnClick() {

        /// on click register
        loginActivityTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getFragmentManager() != null) {
                    startActivity(new Intent(LoginClientActivity.this, RegisterClientActivity.class));
                }

            }
        });

        loginFragmentChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Checked = isChecked;
            }
        });
        loginFragmentTxtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    Intent intent = new Intent(LoginClientActivity.this, ForgetPasswordClientActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // class login retrofit
    private void ClassLogin() {
        loginFragmentBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!loginFragmentEditUserEmail.getText().toString().isEmpty()
                        && !loginFragmentEditUserPassword.getText().toString().isEmpty()) {

                    loginActivityProgressBar.setVisibility(View.VISIBLE);

                    apiServerClient = getRestaurant().create(ApiServerClient.class);

                    apiServerClient.onLogin(loginFragmentEditUserEmail.getText().toString()
                            , loginFragmentEditUserPassword.getText().toString())
                            .enqueue(new Callback<ClientLogin>() {
                                @Override
                                public void onResponse(Call<ClientLogin> call, Response<ClientLogin> response) {
                                    try {

                                          Toast.makeText(LoginClientActivity.this, response.body().getMsg() + "dss", Toast.LENGTH_SHORT).show();

                                        if (response.body().getStatus() == 1) {

                                            // check is checkBox is Checked
                                            if (loginFragmentChkBox.isChecked()) {
                                                // save data login user
                                                // save data login user
                                                ClassSharedPreferences(response.body().getData().getUser().getId()
                                                        , response.body().getData().getApiToken()
                                                        , response.body().getData().getUser().getName(),
                                                        response.body().getData().getUser().getEmail(),
                                                        response.body().getData().getUser().getPhone()
                                                        , loginFragmentEditUserPassword.getText().toString());

                                                loginActivityProgressBar.setVisibility(View.INVISIBLE);

                                            } else {
                                                loginActivityProgressBar.setVisibility(View.INVISIBLE);
                                            }

                                            Intent intent = new Intent(LoginClientActivity.this, MainClient.class);
                                            intent.putExtra("openLogin",1);
                                            startActivity(intent);

                                            RegisterToken();

                                        }
                                    } catch (Exception e) {

                                        Log.d("error", e.getMessage());

                                    }

                                }

                                @Override
                                public void onFailure(Call<ClientLogin> call, Throwable t) {
                                    Toast.makeText(LoginClientActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                } else {

                    if (loginFragmentEditUserEmail.getText().toString().isEmpty()) {
                        loginFragmentEditUserEmail.setError(getResources().getString(R.string.is_not_null));
                    }
                    if (!loginFragmentEditUserPassword.getText().toString().isEmpty()) {
                        loginFragmentEditUserPassword.setError(getResources().getString(R.string.is_not_null));
                    }
                }

            }
        });
    }

    private void getDataUserShrPreferences() {

        loginFragmentEditUserEmail.setText(LoadData(LoginClientActivity.this, USER_EMAIL));
        loginFragmentEditUserPassword.setText(LoadData(LoginClientActivity.this, Key_password));

        // check is checkBox is Checked
        if (LoadBooleanClient(LoginClientActivity.this, KEY_IS_CHECK_BOX)) {
            Log.d("response", "true");
            loginFragmentChkBox.setChecked(true);
        } else {
            loginFragmentChkBox.setChecked(false);
            Log.d("response", "false");

        }
    }

    private void ClassSharedPreferences(int id, String ApiToken, String name, String email, String phone, String password) {

       SaveData(LoginClientActivity.this, USER_API_TOKEN, String.valueOf(ApiToken));
      SaveData(LoginClientActivity.this, USER_ID, String.valueOf(id));
       SaveData(LoginClientActivity.this, USER_NAME, String.valueOf(name));
        SaveData(LoginClientActivity.this, USER_EMAIL, String.valueOf(email));
        SaveData(LoginClientActivity.this, USER_PHONE, String.valueOf(phone));
        SaveData(LoginClientActivity.this, Key_password, String.valueOf(password));
       SaveData(LoginClientActivity.this,  KEY_IS_CHECK_BOX, Checked);

    }


    //send Register Token
    public void RegisterToken() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
         Log.d("USER_API_TOKEN",refreshedToken);

        // get Register Token
        apiServerClient.RegisterToken(refreshedToken, LoadData(LoginClientActivity.this, USER_API_TOKEN), "android").enqueue(new Callback<NotifyToken>() {
            @Override
            public void onResponse(Call<NotifyToken> call, Response<NotifyToken> response) {

                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getApplication(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplication(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<NotifyToken> call, Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
