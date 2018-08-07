package com.example.kardana.androidcourse.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kardana.androidcourse.FilterByType;
import com.example.kardana.androidcourse.MainActivity;
import com.example.kardana.androidcourse.Model.ModelFirebaseRoom;
import com.example.kardana.androidcourse.Model.RoomsViewModel;
import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.RoomListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.kardana.androidcourse.Model.Room;

/**
 * Created by Dana on 02-Jun-18.
 */

public class HomeFragment extends Fragment {

    private RoomListAdapter roomListAdapter;
    private View view;
    private RoomsViewModel dataModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.home_fragment, container, false);
        roomListAdapter = new RoomListAdapter(view.getContext(),  new ArrayList<Room>());
        ListView listView = view.findViewById(R.id.room_list_view);
        listView.setAdapter(roomListAdapter);
        dataModel = ViewModelProviders.of(getActivity()).get(RoomsViewModel.class);
        dataModel.getData().observe(getActivity(), new Observer<List<Room>>() {
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
    }
}
