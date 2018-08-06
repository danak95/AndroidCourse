package com.example.kardana.androidcourse.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
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
        currRoom = getParentFragment().getArguments().getParcelable("curr_room");
        WebView roomWebView = (WebView) view.findViewById(R.id.room_WebView);
        WebSettings settings = roomWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        roomWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        roomWebView.setScrollbarFadingEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            roomWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            roomWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        roomWebView.setWebViewClient(new WebViewClient());
        roomWebView.loadUrl(currRoom.getRoomSite());
        return view;
    }
}