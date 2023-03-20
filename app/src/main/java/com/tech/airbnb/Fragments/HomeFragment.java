package com.tech.airbnb.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.tech.airbnb.FragmentAdapter;
import com.tech.airbnb.R;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText search;
    private TabLayout tabLayout;
    private FragmentAdapter fragmentAdapter;
    private ViewPager viewPager;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        initViews();
        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());

        fragmentAdapter.addFragment(new ExploreFragment(), "Tab 1");
        fragmentAdapter.addFragment(new ExploreFragment(), "Tab 2");
        fragmentAdapter.addFragment(new ExploreFragment(), "Tab 3");
        fragmentAdapter.addFragment(new ExploreFragment(), "Tab 4");
        fragmentAdapter.addFragment(new ExploreFragment(), "Tab 5");


        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void initViews(){
        //recyclerView = view.findViewById(R.id.recyclerView);
        //search = view.findViewById(R.id.search_bar);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
    }
}
