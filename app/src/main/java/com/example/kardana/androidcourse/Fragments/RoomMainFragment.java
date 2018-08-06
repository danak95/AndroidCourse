package com.example.kardana.androidcourse.Fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.R;

import java.io.Serializable;

/**
 * Created by Dana on 16-Jun-18.
 */

public class RoomMainFragment extends Fragment {

    private static Room currRoom;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.room_main_fragment, container, false);
        currRoom = (Room) getArguments().getSerializable("curr_room");
        return view;
    }
}