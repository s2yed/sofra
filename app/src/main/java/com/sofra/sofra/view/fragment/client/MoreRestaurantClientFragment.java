package com.sofra.sofra.view.fragment.client;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.MoreAdapter;
import com.sofra.sofra.data.api.ApiServerClient;
import com.sofra.sofra.data.model.Generated.GeneratedModelMore;
import com.sofra.sofra.helper.CustomDialogCloseClass;
import com.sofra.sofra.view.fragment.ContactMeRestaurantFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sofra.sofra.data.api.RetrofitClient.getRestaurant;
import static com.sofra.sofra.helper.HelperMathod.getStartFragments;


public class MoreRestaurantClientFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.fragmentMoreListViewRestaurant)
    ListView fragmentMoreListViewRestaurant;

    private View view;
    ApiServerClient apiServerClient;

    List<GeneratedModelMore> generatedModelMores = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_more_restaurant, container, false);

        unbinder = ButterKnife.bind(this, view);
        // initializer tools
        inti();


        return view;
    }

    // initializer tools
    private void inti() {
        apiServerClient = getRestaurant().create(ApiServerClient.class);

        generatedModelMores.clear();

        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_offer, "Offer"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_connect, "Connect with us"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_about, "About App"));
        generatedModelMores.add(new GeneratedModelMore(R.drawable.icon_logout, "logout"));

        MoreAdapter moreAdapter = new MoreAdapter(getContext(), generatedModelMores);
        fragmentMoreListViewRestaurant.setAdapter(moreAdapter);

        fragmentMoreListViewRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.mainClientReplaceFragment, new GetOfferRestaurantClientFragment());
                    }
                }else  if (position == 1) {
                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.mainClientReplaceFragment, new ContactMeRestaurantFragment());
                    }
                }
                else  if (position == 2) {
                    if (getFragmentManager() != null) {
                        getStartFragments(getFragmentManager(), R.id.mainClientReplaceFragment, new AboutRestaurantClientFragment());
                    }
                }
                else if (position == 3) {
                    CustomDialogCloseClass cdd=new CustomDialogCloseClass(getActivity());
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    cdd.show();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
