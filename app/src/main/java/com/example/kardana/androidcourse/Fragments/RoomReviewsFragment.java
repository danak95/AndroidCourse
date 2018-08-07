package com.example.kardana.androidcourse.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kardana.androidcourse.FilterByType;
import com.example.kardana.androidcourse.MainActivity;
import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.Review;
import com.example.kardana.androidcourse.Model.ReviewViewModel;
import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.Model.RoomsViewModel;
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
    private Room currRoom = null;
    private ReviewViewModel dataModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.room_reviews_fragment, container, false);
        FloatingActionButton addReviewButton = view.findViewById(R.id.floatingAddReviewButton);

        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewsListAdapter = new ReviewsListAdapter(view.getContext(), reviewList);
        ListView listView = view.findViewById(R.id.reviews_list);
        listView.setAdapter(reviewsListAdapter);
        currRoom = this.getParentFragment().getArguments().getParcelable("curr_room");

        dataModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        dataModel.getData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                List<Review> filteredReviews = new ArrayList<>();

                for (Review review : reviews)
                {
                    if (review.getRoomId().equals(currRoom.getId()))
                    {
                        filteredReviews.add(review);
                    }
                }
                reviewsListAdapter.updateReviewsList(filteredReviews);
                reviewsListAdapter.notifyDataSetChanged();
            }
        });


    }

}
