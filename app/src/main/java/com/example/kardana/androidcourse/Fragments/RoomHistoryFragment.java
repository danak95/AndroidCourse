package com.example.kardana.androidcourse.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.RoomListAdapter;

import java.util.ArrayList;
import java.util.List;

import Model.Room;

/**
 * Created by Dana on 08-Jun-18.
 */

public class RoomHistoryFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.room_history_fragment, container, false);
        roomList.add(new Room("123", "1", "1", "1", 4.1));
        roomList.add(new Room("234", "2", "2", "2", 5.2));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        roomListAdapter = new RoomListAdapter(view.getContext(), roomList);
        ListView listView = view.findViewById(R.id.room_list_view);
        listView.setAdapter(roomListAdapter);
        showActionBar();
    }

    private void showActionBar() {
        // get the ToolBar from Main Activity
        final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        // get the ActionBar from Main Activity
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        // inflate the customized Action Bar View
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_actionbar, null);
        final TextView title = view.findViewById(R.id.frag_title);
        title.setText(R.string.nav_room_history);

        if (actionBar != null) {
            // enable the customized view and disable title
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            actionBar.setCustomView(view);
            // remove Burger Icon
            toolbar.setNavigationIcon(null);

            // add click listener to the back arrow icon
            view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // reverse back the show
                    actionBar.setDisplayShowCustomEnabled(false);
                    actionBar.setDisplayShowTitleEnabled(true);
                    //get the Drawer and DrawerToggle from Main Activity
                    // set them back as normal
                    DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                            getActivity(), drawer, toolbar, R.string.navigation_drawer_open,
                            R.string.navigation_drawer_close);
                    // All that to re-synchronize the Drawer State
                    toggle.syncState();
                    // Implement Back Arrow Icon
                    // so it goes back to previous Fragment
                    getActivity().onBackPressed();
                }
            });
        }
    }
}
