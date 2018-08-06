package com.example.kardana.androidcourse.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.R;

/**
 * Created by Dana on 16-Jun-18.
 */

public class RoomOrdersFragment extends Fragment {
    private Room currRoom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.room_orders_fragment, container, false);
        WebView roomWebView = (WebView) view.findViewById(R.id.room_WebView);
        roomWebView.setWebViewClient(new WebViewClient());
      //  currRoom = savedInstanceState.getParcelable("curr_room");
        roomWebView.loadUrl(currRoom.getRoomSite());
        return view;
    }
}