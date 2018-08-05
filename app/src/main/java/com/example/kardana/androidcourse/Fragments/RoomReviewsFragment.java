package com.example.kardana.androidcourse.Fragments;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kardana.androidcourse.FilterByType;
import com.example.kardana.androidcourse.Model.Review;
import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.ReviewsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dana on 16-Jun-18.
 */

public class RoomReviewsFragment extends Fragment {/*
    private ReviewsListAdapter reviewsListAdapter;
    private ListView reviewsListView;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.room_reviews_item, container, false);
        reviewsListAdapter = new ReviewsListAdapter(view.getContext(),  new ArrayList<Review>());
        ListView listView = view.findViewById(R.id.re);
        listView.setAdapter(roomListAdapter);
//        model.addRoom(new Room("123", "1", "1", "1", 4.1));
//        model.addRoom(new Room("234", "2", "2", "2", 5.2));

        model.getAllRooms().observe(this, new Observer<List<Room>>() {
            @Override
            public void onChanged(@Nullable List<Room> rooms) {
                roomListAdapter.updateRoomsList(rooms);
                roomListAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    public void setRoomListAdapterFilter(FilterByType filter)
    {
        roomListAdapter.setFilterType(filter);
    }

    public void filter(HashMap<FilterByType, List<String>> filters)
    {
        roomListAdapter.setConstraints(filters);
        roomListAdapter.getFilter().filter("");
    }

    public void onQueryTextChanged(String newText)
    {
        roomListAdapter.setFilterType(FilterByType.NAME);
        roomListAdapter.getFilter().filter(newText);
    }

    public void onBackPressed()
    {
        roomListAdapter.clearResults();
    }*/
}
