package com.sofra.sofra.view.fragment.restaurant;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.SpinnerAdapter;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.Generated.GeneratedModelSpinner;
import com.sofra.sofra.data.model.cities.Cities;
import com.sofra.sofra.data.model.profile.Profile;
import com.sofra.sofra.data.model.regions.Regions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.Key_password;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.LoadData;
import static com.sofra.sofra.data.local.SharedPreferncesMangerRestaurant.USER_API_TOKEN;
import static com.sofra.sofra.helper.HelperMathod.LodeImageCircle;
import static com.sofra.sofra.helper.HelperMathod.checkCorrespondPassword;
import static com.sofra.sofra.helper.HelperMathod.checkLengthPassword;

import static com.sofra.sofra.helper.HelperMathod.getStartFragments;

public class EditProfileRegisterRestaurantFragment extends Fragment {


    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 0x3;
    Unbinder unbinder;

    ProgressBar progressBar;


    ApiServerRestaurant apiServerRestaurant;
    // value adapter city
    SpinnerAdapter spinnerAdapter;
    ArrayList<GeneratedModelSpinner> generatedModelSpinnerArrayListCity;
    GeneratedModelSpinner cityGeneratedModelSpinner;

    // value adapter Regions
    SpinnerAdapter regionsSpinnerAdapter;
    ArrayList<GeneratedModelSpinner> generatedModelSpinnerArrayListRegions;
    GeneratedModelSpinner regionsGeneratedModelSpinner;

    ImageView registerMoreRestaurantActivityImgNew;
    @BindView(R.id.registerMoreRestaurantActivityEditName)
    TextInputEditText registerMoreRestaurantActivityEditName;
    @BindView(R.id.registerMoreRestaurantActivityEditEmail)
    TextInputEditText registerMoreRestaurantActivityEditEmail;
    @BindView(R.id.registerMoreRestaurantEditPhone)
    TextInputEditText registerMoreRestaurantEditPhone;
    @BindView(R.id.registerMoreRestaurantActivitySpinnerCite)
    Spinner registerMoreRestaurantActivitySpinnerCite;
    @BindView(R.id.registerMoreRestaurantActivityRegionsSpinner)
    Spinner registerMoreRestaurantActivityRegionsSpinner;
    @BindView(R.id.registerMoreRestaurantActivityEditPassword)
    TextInputEditText registerMoreRestaurantActivityEditPassword;
    @BindView(R.id.registerMoreRestaurantActivityEmphasisEditPassword)
    TextInputEditText registerMoreRestaurantActivityEmphasisEditPassword;

    Button registerMoreRestaurantActivityBtnContinues;

    private int idCity;
    private int idRegions;

    private String filePath;

    private View view;
    private String getAvailability,getDeliveryCost,getMinimumCharger,getWhatsapp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register_more_restaureant, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();

        // get DataItem City
        getDataCity();
        // on Click Btn
        onClickBtn();

        // get DataItem Profile
        getDataProfile();
        return view;
    }

    // initialize tools
    private void inti() {

        progressBar = view.findViewById(R.id.progressBarRestaurant);
        registerMoreRestaurantActivityBtnContinues = view.findViewById(R.id.registerMoreRestaurantActivityBtnContinues);
        registerMoreRestaurantActivityImgNew = view.findViewById(R.id.registerMoreRestaurantActivityImgNew);
        progressBar.setVisibility(View.INVISIBLE);

        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);

        generatedModelSpinnerArrayListRegions = new ArrayList<>();
        generatedModelSpinnerArrayListCity = new ArrayList<>();
    }

    // get DataItem Profile
    public void getDataProfile() {
//        LoadData(getActivity(), USER_API_TOKEN)
        apiServerRestaurant.getProfile(LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Log.d("response", response.body().getMsg());
                Toast.makeText(getContext(),  response.body().getMsg(), Toast.LENGTH_LONG).show();

                if (response.body().getStatus() == 1) {
                    registerMoreRestaurantActivityEditName.setText(response.body().getData().getUser().getName());
                    registerMoreRestaurantActivityEditEmail.setText(response.body().getData().getUser().getEmail());
                    registerMoreRestaurantEditPhone.setText(response.body().getData().getUser().getPhone());
                    registerMoreRestaurantActivityEditPassword.setText(LoadData(getActivity(), Key_password));
                    registerMoreRestaurantActivityEmphasisEditPassword.setText(LoadData(getActivity(), Key_password));
                    idRegions = Integer.parseInt(response.body().getData().getUser().getRegionId());
                    idCity = Integer.parseInt(response.body().getData().getUser().getRegion().getCityId());
                    getAvailability = response.body().getData().getUser().getAvailability();
                    getDeliveryCost = response.body().getData().getUser().getDeliveryCost();
                    getMinimumCharger = response.body().getData().getUser().getMinimumCharger();
                    getWhatsapp = response.body().getData().getUser().getWhatsapp();

                    LodeImageCircle(getActivity(), response.body().getData().getUser().getPhotoUrl(), registerMoreRestaurantActivityImgNew);

                    for (int i = 0; i < generatedModelSpinnerArrayListCity.size(); i++) {
                        if (generatedModelSpinnerArrayListCity.get(i).getId() == idCity) {
                            registerMoreRestaurantActivitySpinnerCite.setSelection(i);
                        }
                    }



                } else {
                    Log.d("response", response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }

    /// get DataItem  Regions
    public void getDataGetRegions(int idCity) {
        // get  PaginationData getRegions
        apiServerRestaurant.getRegions(idCity).enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                Log.d("response", response.body().getMsg());

                if (response.body().getStatus() == 1) {
                    // clear list regions
                    generatedModelSpinnerArrayListRegions.clear();

                    generatedModelSpinnerArrayListRegions.add(new GeneratedModelSpinner(0,
                            getResources().getString(R.string.regions)));

                    for (int i = 0; i < response.body().getDataPagination().getData().size(); i++) {
                        regionsGeneratedModelSpinner =
                                new GeneratedModelSpinner(response.body().getDataPagination().getData().get(i).getId(),
                                        response.body().getDataPagination().getData().get(i).getName());
                        generatedModelSpinnerArrayListRegions.add(regionsGeneratedModelSpinner);
                    }

                    regionsSpinnerAdapter = new SpinnerAdapter(getContext(), generatedModelSpinnerArrayListRegions);
                    registerMoreRestaurantActivityRegionsSpinner.setAdapter(regionsSpinnerAdapter);
                    registerMoreRestaurantActivityRegionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                idRegions = generatedModelSpinnerArrayListRegions.get(position).getId();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    for (int i = 0; i < generatedModelSpinnerArrayListRegions.size(); i++) {
                        if (generatedModelSpinnerArrayListRegions.get(i).getId() == idRegions) {
                            registerMoreRestaurantActivityRegionsSpinner.setSelection(i);
                        }
                    }

                } else {
                    Log.d("response", response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }

    // get  DataItem cities
    private void getDataCity() {
        progressBar.setVisibility(View.VISIBLE);

        apiServerRestaurant.getCities().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

                try {
                    if (response.body().getStatus() == 1) {

                        progressBar.setVisibility(View.INVISIBLE);
                        // clear list
                        generatedModelSpinnerArrayListCity.clear();
                        // add new data from list
                        generatedModelSpinnerArrayListCity.add(new GeneratedModelSpinner(0, getResources().getString(R.string.city)));

                        for (int i = 0; i < response.body().getData().getData().size(); i++) {

                            cityGeneratedModelSpinner = new GeneratedModelSpinner(response.body().getData().getData().get(i).getId(),
                                    response.body().getData().getData().get(i).getName());

                            generatedModelSpinnerArrayListCity.add(cityGeneratedModelSpinner);
                        }

                        spinnerAdapter = new SpinnerAdapter(getContext(), generatedModelSpinnerArrayListCity);
                        registerMoreRestaurantActivitySpinnerCite.setAdapter(spinnerAdapter);
                        registerMoreRestaurantActivitySpinnerCite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    idCity = generatedModelSpinnerArrayListCity.get(position).getId();
                                    getDataGetRegions(idCity);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });


                    } else {
                        Log.d("response", response.body().getMsg());
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                }catch (Exception e){
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }

    // on click btn
    public void onClickBtn() {

        /// this is save all data registerMore
        registerMoreRestaurantActivityBtnContinues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkLengthPassword(registerMoreRestaurantActivityEditPassword.getText().toString())
                        && checkCorrespondPassword(registerMoreRestaurantActivityEditPassword.getText().toString()
                        , registerMoreRestaurantActivityEmphasisEditPassword.getText().toString())) {

                    Bundle bundle = new Bundle();
                    bundle.putString("Name", registerMoreRestaurantActivityEditName.getText().toString());
                    bundle.putString("Email", registerMoreRestaurantActivityEditEmail.getText().toString());
                    bundle.putString("Phone", registerMoreRestaurantEditPhone.getText().toString());
                    bundle.putInt("idRegions", idRegions);
                    bundle.putString("Password", registerMoreRestaurantActivityEditPassword.getText().toString());
                    bundle.putString("PasswordEmphasis", registerMoreRestaurantActivityEmphasisEditPassword.getText().toString());
                    bundle.putString("filePath", filePath);
                    bundle.putString("getAvailability", getAvailability);
                    bundle.putString("getDeliveryCost", getDeliveryCost);
                    bundle.putString("getMinimumCharger", getMinimumCharger);
                    bundle.putString("getWhatsapp", getWhatsapp);


                    // open
                    Fragment fragment = new RegisterMoreRestaurantFragment();
                    fragment.setArguments(bundle);

                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.mainRestaurantReplaceFragment, fragment);
                    }

                } else {
                    if (!checkLengthPassword(registerMoreRestaurantActivityEditPassword.getText().toString())) {
                        registerMoreRestaurantActivityEditPassword.setError(getResources().getString(R.string.It_should_be_the_largest6));
                    }
                    if (!checkCorrespondPassword(registerMoreRestaurantActivityEditPassword.getText().toString(), registerMoreRestaurantActivityEmphasisEditPassword.getText().toString())) {
                        registerMoreRestaurantActivityEmphasisEditPassword.setError(getResources().getString(R.string.number_does_not_correspond));
                    }
                }
            }
        });

        registerMoreRestaurantActivityImgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionForReadExtertalStorage()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 0);
                } else {
                    try {
                        requestPermissionForReadExtertalStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

            //data.getDataCode return the content URI for the selected Image
            Uri uri = data.getData();

            filePath = getRealPathFromURIPath(uri, getActivity());

            // Set the Image in ImageView after decoding the String
            registerMoreRestaurantActivityImgNew.setImageBitmap(BitmapFactory.decodeFile(filePath)
            );

            Toast.makeText(getContext(), "Something went wrong" + filePath, Toast.LENGTH_LONG).show();

        }

    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public boolean checkPermissionForReadExtertalStorage() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
