package com.example.kardana.androidcourse.Fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kardana.androidcourse.R;

/**
 * Created by Dana on 16-Jun-18.
 */

public class RoomMainFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.room_main_fragment, container, false);
        ConstraintLayout constraintLayout = view.findViewById(R.id.tab_constraint_layout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.tab_constraint_layout,ConstraintSet.TOP,R.id.tab_layout,ConstraintSet.BOTTOM,10);
        constraintSet.applyTo(constraintLayout);
        return view;
    }
}