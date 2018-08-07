package com.example.kardana.androidcourse.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.R;

/**
 * Created by Dana on 16-Jun-18.
 */

public class RoomFragment extends Fragment {
    public static final String ARGS_TITLE = "TITLE";
    private FragmentStatePagerAdapter adapterViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.room_fragment, container, false);
        final ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpPager);
        adapterViewPager = new RoomPagerAdapter(getChildFragmentManager());
        vpPager.setOffscreenPageLimit(3);
        vpPager.setAdapter(adapterViewPager);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(vpPager);
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tabSelected)
            {
                vpPager.setCurrentItem(tabSelected.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tabSelected){}

            @Override
            public void onTabReselected(TabLayout.Tab tabSelected){

            }
        });
        return view;
    }

    private class RoomPagerAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_OF_TABS = 3;
        private String[] titles = new String[NUM_OF_TABS];

        public RoomPagerAdapter(FragmentManager fm) {
            super(fm);
            titles[0] = getActivity().getString(R.string.main);
            titles[1] = getActivity().getString(R.string.reviews);
            titles[2] = getActivity().getString(R.string.orders);
        }

        @Override
        public Fragment getItem(int position) {
            String bla = "bla";
            switch (position) {
                case 0:
                    RoomMainFragment roomMain = new RoomMainFragment();
                    return roomMain;
                case 1:
                    RoomReviewsFragment roomReviews = new RoomReviewsFragment();
                    return roomReviews;
                case 2:
                    RoomOrdersFragment roomOrders = new RoomOrdersFragment();
                    return roomOrders;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_OF_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}