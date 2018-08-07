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
import com.example.kardana.androidcourse.MainActivity;
import com.example.kardana.androidcourse.Model.Review;
import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.ReviewsListAdapter;
import com.example.kardana.androidcourse.RoomListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Dana on 16-Jun-18.
 */

public class RoomReviewsFragment extends Fragment {
    private ReviewsListAdapter reviewsListAdapter;
    private List<Review> reviewList= new ArrayList<Review>();
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.room_reviews_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewsListAdapter = new ReviewsListAdapter(view.getContext(), reviewList);
        ListView listView = view.findViewById(R.id.reviews_list);
        listView.setAdapter(reviewsListAdapter);
    }

}
