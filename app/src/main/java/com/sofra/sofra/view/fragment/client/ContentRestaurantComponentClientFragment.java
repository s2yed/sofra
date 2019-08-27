package com.sofra.sofra.view.fragment.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.ViewPagerComponentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.sofra.sofra.adapter.ShowRestaurantClientRecyclerAdapter.Name;
import static com.sofra.sofra.adapter.ShowRestaurantClientRecyclerAdapter.PhotoUrl;
 import static com.sofra.sofra.helper.HelperMathod.LodeImageCircle;

public class ContentRestaurantComponentClientFragment extends Fragment {


    @BindView(R.id.RestaurantFragmentIvComponent)
    ImageView RestaurantFragmentIvComponent;
    @BindView(R.id.RestaurantFragmentTvComponent)
    TextView RestaurantFragmentTvComponent;
    Unbinder unbinder;
    private View view;
    public TabLayout RestaurantFragmentTabLayoutComponent;
    public ViewPager requestRestaurantFragmentViewPager;
    private ViewPagerComponentAdapter adapter;


    private MenuRestaurantClientFragment menuRestaurantClientFragment;
    private InformationRestaurantClientFragment informationRestaurantClientFragment;
    private ReadCommentRestaurantClientFragment readCommentRestaurantClientFragment;
    private int idRestaurant;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_component_restaurnt_client, container, false);

        unbinder = ButterKnife.bind(this, view);

        // initializer tools
        inti();

        return view;
    }

    // initializer tools
    private void inti() {
        // Setting ViewPager for each Tabs
        requestRestaurantFragmentViewPager = view.findViewById(R.id.requestRestaurantFragmentViewPager);
        RestaurantFragmentTabLayoutComponent = view.findViewById(R.id.RestaurantFragmentTabLayoutComponent);

        LodeImageCircle(getContext(), PhotoUrl, RestaurantFragmentIvComponent);
        RestaurantFragmentTvComponent.setText(Name);
        adapter = new ViewPagerComponentAdapter(getChildFragmentManager());

        // get id restaurant from return show adapter or cart item

        Bundle arguments = getArguments();
        if (arguments != null) {
            idRestaurant = arguments.getInt("IdRestaurantFromCartOrShowAdapter");
            Log.d("idRestaurantContent", String.valueOf(idRestaurant));

        }
        // send id restaurant from return show adapter or cart item
        Bundle bundle =new Bundle();
        bundle.putInt("idRestaurantContentComponent",idRestaurant);

        menuRestaurantClientFragment =  new MenuRestaurantClientFragment();
        menuRestaurantClientFragment.setArguments(bundle);

        readCommentRestaurantClientFragment =new ReadCommentRestaurantClientFragment();
        readCommentRestaurantClientFragment.setArguments(bundle);

        informationRestaurantClientFragment =  new InformationRestaurantClientFragment();
        informationRestaurantClientFragment.setArguments(bundle);




        adapter.addFragment(menuRestaurantClientFragment, getActivity().getResources().getString(R.string.menu));
        adapter.addFragment(readCommentRestaurantClientFragment, getActivity().getResources().getString(R.string.comment));
        adapter.addFragment(informationRestaurantClientFragment, getActivity().getString(R.string.information));

        requestRestaurantFragmentViewPager.setAdapter(adapter);
        RestaurantFragmentTabLayoutComponent.setTabGravity(TabLayout.GRAVITY_FILL);
        RestaurantFragmentTabLayoutComponent.setTabTextColors(getResources().getColor(R.color.red50), getResources().getColor(R.color.white));
        RestaurantFragmentTabLayoutComponent.setupWithViewPager(requestRestaurantFragmentViewPager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
