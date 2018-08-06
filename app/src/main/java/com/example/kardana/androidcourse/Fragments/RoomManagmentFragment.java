package com.example.kardana.androidcourse.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kardana.androidcourse.MainActivity;
import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.Model.RoomsViewModel;
import com.example.kardana.androidcourse.Model.User;
import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.RoomListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dana on 05-Aug-18.
 */

public class RoomManagmentFragment extends Fragment {

    private List<Room> roomList= new ArrayList<Room>();
    private RoomListAdapter roomListAdapter;
    private User currUser;
    private RoomsViewModel dataModel;
    private RoomManagmentFragment roomManagmentFragment = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.room_managment_fragment, container, false);
        FloatingActionButton addRoomButton = view.findViewById(R.id.addRoomButton);
        addRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddNewRoomFragment();
                FragmentManager fragmentManager = ((MainActivity)(view.getContext())).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roomListAdapter = new RoomListAdapter(view.getContext(), roomList);
        ListView listView = view.findViewById(R.id.room_list_view);
        listView.setAdapter(roomListAdapter);
        ((MainActivity)getActivity()).showActionBar(R.string.nav_manage_rooms);

        Model.getInstance().getCurrentUser(new Model.IGetCurrentUserCallback() {
            @Override
            public void onComplete(User user) {
                currUser = user;
                dataModel = ViewModelProviders.of(roomManagmentFragment).get(RoomsViewModel.class);
                dataModel.getData().observe(roomManagmentFragment, new Observer<List<Room>>() {
                    @Override
                    public void onChanged(@Nullable List<Room> rooms) {
                        List<Room> roomsOfOwner = new ArrayList<Room>();

                        for (Room currRoom : rooms)
                        {
                            if (currRoom.getOwnerId().equals(currUser.getUserid()))
                            {
                                roomsOfOwner.add(currRoom);
                            }
                        }
                        roomListAdapter.updateRoomsList(roomsOfOwner);
                        roomListAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}