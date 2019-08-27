package com.sofra.sofra.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sofra.sofra.R;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.addContact.AddContact;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;

public class ContactMeRestaurantFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.contactMeRestaurantFragmentEditName)
    EditText contactMeRestaurantFragmentEditName;
    @BindView(R.id.contactMeRestaurantFragmentEditEmail)
    EditText contactMeRestaurantFragmentEditEmail;
    @BindView(R.id.contactMeRestaurantFragmentEditPhone)
    EditText contactMeRestaurantFragmentEditPhone;
    @BindView(R.id.contactMeRestaurantFragmentEditMassage)
    EditText contactMeRestaurantFragmentEditMassage;
    @BindView(R.id.contactMeRestaurantFragmentBtnSend)
    Button contactMeRestaurantFragmentBtnSend;
    @BindView(R.id.contactMeRestaurantFragmentItemProgressBar)
    ProgressBar contactMeRestaurantFragmentItemProgressBar;

    private ApiServerRestaurant apiServerRestaurant;

    private View view;

    private String filePath;
    private String  type = "suggestion";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contact_me_restaurnt, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();


        return view;
    }

    // initializer tools
    private void inti() {
        /// get date MyItemRestaurantFragment
        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);

        contactMeRestaurantFragmentItemProgressBar.setVisibility(View.INVISIBLE);
    }


    // get Add Contact
    private void AddContact() {
        contactMeRestaurantFragmentItemProgressBar.setVisibility(View.VISIBLE);
//        LoadData(getActivity(),USER_API_TOKEN);

        apiServerRestaurant.addContact(contactMeRestaurantFragmentEditName.getText().toString(),
                contactMeRestaurantFragmentEditEmail.getText().toString(), contactMeRestaurantFragmentEditPhone.getText().toString()
                , type, contactMeRestaurantFragmentEditMassage.getText().toString()).enqueue(new Callback<AddContact>() {
            @Override
            public void onResponse(Call<AddContact> call, Response<AddContact> response) {
                try {
                    Log.d("response", response.body().getMsg());
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

                        contactMeRestaurantFragmentItemProgressBar.setVisibility(View.INVISIBLE);

                        clearText();
                    } else {
                        Log.d("response", response.body().getMsg());
                        contactMeRestaurantFragmentItemProgressBar.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<AddContact> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }

    private void clearText() {
        contactMeRestaurantFragmentEditName.getText().clear();
        contactMeRestaurantFragmentEditEmail.getText().clear();
        contactMeRestaurantFragmentEditPhone.getText().clear();
        contactMeRestaurantFragmentEditMassage.getText().clear();
     }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.contactMeRestaurantFragmentRbComplaint, R.id.contactMeRestaurantFragmentRbSuggestion, R.id.contactMeRestaurantFragmentRbEnquiry, R.id.contactMeRestaurantFragmentBtnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contactMeRestaurantFragmentRbComplaint:
                type = "complaint";
                Toast.makeText(getActivity(),type, Toast.LENGTH_LONG).show();

                break;
            case R.id.contactMeRestaurantFragmentRbSuggestion:
                type = "suggestion";
                Toast.makeText(getActivity(),type, Toast.LENGTH_LONG).show();

                break;
            case R.id.contactMeRestaurantFragmentRbEnquiry:
                type = "inquiry";
                Toast.makeText(getActivity(),type, Toast.LENGTH_LONG).show();

                break;
            case R.id.contactMeRestaurantFragmentBtnSend:
                AddContact();
                break;
        }
    }
}


