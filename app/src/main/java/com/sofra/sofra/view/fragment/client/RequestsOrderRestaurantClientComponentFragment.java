package com.sofra.sofra.view.fragment.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sofra.sofra.R;
import com.sofra.sofra.adapter.ViewPagerComponentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RequestsOrderRestaurantClientComponentFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.requestRestaurantFragmentTabLayout)
    TabLayout requestRestaurantFragmentTabLayout;
    @BindView(R.id.requestRestaurantFragmentViewPager)
    ViewPager requestRestaurantFragmentViewPager;
    private View view;


    public OrdersRequestsRestaurantClientFragment requestsRestaurantFragmentAccept;
    public OrdersRequestsRestaurantClientFragment requestsRestaurantFragmentCompleted;
    private ViewPagerComponentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_component_restaurnt, container, false);

        unbinder = ButterKnife.bind(this, view);

        // initializer tools
        inti();


        return view;
    }

    // initializer tools
    private void inti() {
        // Setting ViewPager for each Tabs


        adapter = new ViewPagerComponentAdapter(getChildFragmentManager());


        // start  class requestsRestaurantFragmentAccept and send delivered
        requestsRestaurantFragmentAccept = new OrdersRequestsRestaurantClientFragment();
        Bundle bundleDelivered = new Bundle();
        bundleDelivered.putString("KeyRequest", "current");
        requestsRestaurantFragmentAccept.setArguments(bundleDelivered);


        // start  class requestsRestaurantFragmentCompleted and send rejected
        requestsRestaurantFragmentCompleted = new OrdersRequestsRestaurantClientFragment();
        Bundle bundleRejected = new Bundle();
        bundleRejected.putString("KeyRequest", "completed");
        requestsRestaurantFragmentCompleted.setArguments(bundleRejected);



        requestsRestaurantFragmentAccept.requestsRestaurantComponentFragment = this;
        requestsRestaurantFragmentCompleted.requestsRestaurantComponentFragment = this;

        adapter.addFragment(requestsRestaurantFragmentAccept, getResources().getString(R.string.current_requests));
        adapter.addFragment(requestsRestaurantFragmentCompleted, getResources().getString(R.string.previous_requests));

        requestRestaurantFragmentViewPager.setAdapter(adapter);
        requestRestaurantFragmentTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        requestRestaurantFragmentTabLayout.setupWithViewPager(requestRestaurantFragmentViewPager);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
