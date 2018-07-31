package com.example.kardana.androidcourse.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kardana.androidcourse.FilterByType;
import com.example.kardana.androidcourse.Model.ModelFirebaseRoom;
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
    private ListView roomListView;
    private List<Room> roomList= new ArrayList<Room>();
    private ModelFirebaseRoom modelFirebaseRoom = new ModelFirebaseRoom();
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_fragment, container, false);
        modelFirebaseRoom.addRoom(new Room("123", "1", "1", "1", 4.1));
        modelFirebaseRoom.addRoom(new Room("234", "2", "2", "2", 5.2));

//        roomList.add(new Room("123", "1", "1", "1", 4.1));
//        roomList.add(new Room("234", "2", "2", "2", 5.2));
//        roomList.add(new Room("fdsfsd", "3", "3", "3", 2));
//        roomList.add(new Room("deadasr", "4", "4", "4", 3.5));
//        roomList.add(new Room("dfds", "5", "5", "5", 1));
//        roomList.add(new Room("היוש", "6", "6", "6", 2.5));
//        roomList.add(new Room("כגדךכגד", "7", "7", "7", 4.5));
//        roomList.add(new Room("פליז תעבוד", "8", "8", "8", 5.5));
        modelFirebaseRoom.getAllRooms(new ModelFirebaseRoom.IGetAllRoomsCallback() {
            @Override
            public void onComplete(ArrayList<Room> rooms) {
                roomListAdapter = new RoomListAdapter(view.getContext(), rooms);
                ListView listView = view.findViewById(R.id.room_list_view);
                listView.setAdapter(roomListAdapter);
            }

            @Override
            public void onCancel() {

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
