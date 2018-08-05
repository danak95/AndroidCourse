package com.example.kardana.androidcourse.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kardana.androidcourse.MainActivity;
import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.RoomListAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.kardana.androidcourse.Model.Room;

/**
 * Created by Dana on 16-Jun-18.
 */

public class WishlistFragment extends Fragment {

    private ListView roomListView;
    private List<Room> roomList= new ArrayList<Room>();
    private RoomListAdapter roomListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wishlist_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roomListAdapter = new RoomListAdapter(view.getContext(), roomList);
        ListView listView = view.findViewById(R.id.room_list_view);
        listView.setAdapter(roomListAdapter);
        ((MainActivity)getActivity()).showActionBar(R.string.nav_wish_list);
    }
}
