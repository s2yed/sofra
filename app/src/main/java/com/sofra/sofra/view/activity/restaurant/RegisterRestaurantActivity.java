package com.sofra.sofra.view.activity.restaurant;

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
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.SpinnerAdapter;
import com.sofra.sofra.data.api.ApiServerRestaurant;
import com.sofra.sofra.data.model.Generated.GeneratedModelSpinner;
import com.sofra.sofra.data.model.categories.Categories;
import com.sofra.sofra.data.model.cities.Cities;
import com.sofra.sofra.data.model.regions.Regions;
import com.sofra.sofra.data.model.restaurantRegister.RestauranttRegister;
import com.sofra.sofra.helper.MultiSelectionSpinner;
import com.sofra.sofra.view.activity.MainHomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.helper.HelperMathod.checkCorrespondPassword;
import static com.sofra.sofra.helper.HelperMathod.checkLengthPassword;
import static com.sofra.sofra.helper.HelperMathod.convertFileToMultipart;
import static com.sofra.sofra.helper.HelperMathod.convertToRequestBody;

public class RegisterRestaurantActivity extends Activity {


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
    ImageView registerRestaurantActivityImgNew;
    @BindView(R.id.registerRestaurantActivityEditName)
    TextInputEditText registerRestaurantActivityEditName;
    @BindView(R.id.registerRestaurantActivityEditEmail)
    TextInputEditText registerRestaurantActivityEditEmail;
    @BindView(R.id.registerRestaurantEditPhone)
    TextInputEditText registerRestaurantEditPhone;
    @BindView(R.id.registerRestaurantActivitySpinnerCite)
    Spinner registerRestaurantActivitySpinnerCite;
    @BindView(R.id.registerRestaurantActivityRegionsSpinner)
    Spinner registerRestaurantActivityRegionsSpinner;
    @BindView(R.id.registerRestaurantActivityEditPassword)
    TextInputEditText registerRestaurantActivityEditPassword;
    @BindView(R.id.registerRestaurantActivityEmphasisEditPassword)
    TextInputEditText registerRestaurantActivityEmphasisEditPassword;

    Button registerRestaurantActivityBtnContinues, registerRestaurantActivityBtnSave;

    /// tools continues
    MultiSelectionSpinner registerRestaurantActivityCategoriesSpinner;
    EditText registerRestaurantActivityEditMinimumOrder, registerRestaurantCategoriesActivityEditDeliveryCost, registerRestaurantCategoriesEditPhoneWhatsApp;

    private int idCity;
    private int idRegions;

     private String filePath;
    List<String> items = new ArrayList<>();
    List<Integer> itemsId = new ArrayList<>();
    private ProgressBar progressBarRestaurantCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_restaureant);

        ButterKnife.bind(this);

        // initialize tools
        inti();

        getDataCity();
        // on Click Btn
        onClickBtn();


    }

    // initialize tools
    private void inti() {

        progressBar = findViewById(R.id.progressBarRestaurant);
        registerRestaurantActivityBtnContinues = findViewById(R.id.registerRestaurantActivityBtnContinues);
        registerRestaurantActivityImgNew = findViewById(R.id.registerRestaurantActivityImgNew);
        progressBar.setVisibility(View.INVISIBLE);

        apiServerRestaurant = getRestaurant().create(ApiServerRestaurant.class);

        generatedModelSpinnerArrayListRegions = new ArrayList<>();
        generatedModelSpinnerArrayListCity = new ArrayList<>();
    }

    public void getDataGetRegions(int idCity) {
        // get  PaginationData getRegions
        apiServerRestaurant.getRegions(idCity).enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {
                Log.d("response", response.body().getMsg());

                if (response.body().getStatus() == 1) {
                    // clear list regions
                    generatedModelSpinnerArrayListRegions.clear();

                    generatedModelSpinnerArrayListRegions.add(new GeneratedModelSpinner(
                            0, getResources().getString(R.string.regions)));

                    for (int i = 0; i < response.body().getDataPagination().getData().size(); i++) {
                        regionsGeneratedModelSpinner =
                                new GeneratedModelSpinner(response.body().getDataPagination().getData().get(i).getId(),
                                        response.body().getDataPagination().getData().get(i).getName());
                        generatedModelSpinnerArrayListRegions.add(regionsGeneratedModelSpinner);
                    }

                    regionsSpinnerAdapter = new SpinnerAdapter(RegisterRestaurantActivity.this, generatedModelSpinnerArrayListRegions);
                    registerRestaurantActivityRegionsSpinner.setAdapter(regionsSpinnerAdapter);
                    registerRestaurantActivityRegionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    // get  PaginationData cities
    private void getDataCity() {
        progressBar.setVisibility(View.VISIBLE);

        apiServerRestaurant.getCities().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

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

                    spinnerAdapter = new SpinnerAdapter(RegisterRestaurantActivity.this, generatedModelSpinnerArrayListCity);
                    registerRestaurantActivitySpinnerCite.setAdapter(spinnerAdapter);
                    registerRestaurantActivitySpinnerCite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }

    // on click btn
    public void onClickBtn() {

        /// this is save all data register
        registerRestaurantActivityBtnContinues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkLengthPassword(registerRestaurantActivityEditPassword.getText().toString())
                        && checkCorrespondPassword(registerRestaurantActivityEditPassword.getText().toString()
                        , registerRestaurantActivityEmphasisEditPassword.getText().toString())) {

                    setContentView(R.layout.fragment_register_continues_restaureant);
                    // initialize tools Continues

                    intiContinues();

                    // DataCode Categories
                    getDataCategories();

                } else {
                    if (!checkLengthPassword(registerRestaurantActivityEditPassword.getText().toString())) {
                        registerRestaurantActivityEditPassword.setError(getResources().getString(R.string.It_should_be_the_largest6));
                    }
                    if (!checkCorrespondPassword(registerRestaurantActivityEditPassword.getText().toString(), registerRestaurantActivityEmphasisEditPassword.getText().toString())) {
                        registerRestaurantActivityEmphasisEditPassword.setError(getResources().getString(R.string.number_does_not_correspond));
                    }
                }
            }
        });

        registerRestaurantActivityImgNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionForReadExtertalStorage()) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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


    // get DataCode categories
    private void getDataCategories() {

        progressBarRestaurantCategories.setVisibility(View.VISIBLE);

        apiServerRestaurant.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {

                if (response.body().getStatus() == 1) {


                    for (int i = 0; i < response.body().getData().size(); i++) {

                        itemsId.add(response.body().getData().get(i).getId());
                        items.add(response.body().getData().get(i).getName());
                        registerRestaurantActivityCategoriesSpinner.setItems(items, itemsId);
                    }

                    progressBarRestaurantCategories.setVisibility(View.INVISIBLE);
                } else {
                    Log.d("response", response.body().getMsg());
                    progressBarRestaurantCategories.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Log.d("response", t.getMessage());
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

            //data.getDataCode return the content URI for the selected Image
            Uri uri = data.getData();

            filePath = getRealPathFromURIPath(uri, RegisterRestaurantActivity.this);

            // Set the Image in ImageView after decoding the String
            registerRestaurantActivityImgNew.setImageBitmap(BitmapFactory.decodeFile(filePath)
            );

            Toast.makeText(this, "Something went wrong" + filePath, Toast.LENGTH_LONG).show();

        }

    }

    // initialize tools Continues
    private void intiContinues() {

        progressBarRestaurantCategories = findViewById(R.id.progressBarRestaurantCategories);
        registerRestaurantActivityBtnSave = findViewById(R.id.registerRestaurantActivityBtnSave);
        registerRestaurantActivityCategoriesSpinner = findViewById(R.id.registerRestaurantActivityCategoriesSpinner);
        registerRestaurantActivityEditMinimumOrder = findViewById(R.id.registerRestaurantActivityEditMinimumOrder);
        registerRestaurantCategoriesActivityEditDeliveryCost = findViewById(R.id.registerRestaurantCategoriesActivityEditDeliveryCost);
        registerRestaurantCategoriesEditPhoneWhatsApp = findViewById(R.id.registerRestaurantCategoriesEditPhoneWhatsApp);

        progressBarRestaurantCategories.setVisibility(View.INVISIBLE);


        //
        registerRestaurantActivityBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < registerRestaurantActivityCategoriesSpinner.getSelectedId().size(); i++) {
                    Log.d("AsString", String.valueOf(registerRestaurantActivityCategoriesSpinner.getSelectedId().get(i)));
                }
                // save data
                apiServerRestaurant.getRestaurantRegister(
                        convertToRequestBody(registerRestaurantActivityEditName.getText().toString())
                        , convertToRequestBody(registerRestaurantActivityEditEmail.getText().toString())
                        , convertToRequestBody(registerRestaurantActivityEditPassword.getText().toString()),
                        convertToRequestBody(registerRestaurantActivityEmphasisEditPassword.getText().toString())
                        , convertToRequestBody(registerRestaurantEditPhone.getText().toString())
                        , convertToRequestBody(registerRestaurantCategoriesEditPhoneWhatsApp.getText().toString())
                        , convertToRequestBody(String.valueOf(idRegions)), registerRestaurantActivityCategoriesSpinner.getSelectedId()
                        , convertToRequestBody(registerRestaurantActivityEditMinimumOrder.getText().toString())
                        , convertToRequestBody(registerRestaurantCategoriesActivityEditDeliveryCost.getText().toString()),
                        convertFileToMultipart(filePath, "photo"))
                        .enqueue(new Callback<RestauranttRegister>() {
                            @Override
                            public void onResponse(Call<RestauranttRegister> call, Response<RestauranttRegister> response) {

                                progressBarRestaurantCategories.setVisibility(View.VISIBLE);

                                Log.d("response", response.body().getMsg());

                                Toast.makeText(RegisterRestaurantActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                if (response.body().getStatus() == 1) {

                                    progressBarRestaurantCategories.setVisibility(View.VISIBLE);

                                    Intent intent = new Intent(RegisterRestaurantActivity.this, MainHomeActivity.class);
                                    startActivity(intent);

                                } else {
                                    progressBarRestaurantCategories.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onFailure(Call<RestauranttRegister> call, Throwable t) {
                                Log.d("onFailure", t.getMessage());

                            }
                        });

            }
        });

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
            int result = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions(RegisterRestaurantActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
