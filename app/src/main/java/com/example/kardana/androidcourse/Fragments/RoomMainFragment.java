package com.example.kardana.androidcourse.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.Model.User;
import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.RoomType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dana on 16-Jun-18.
 */

public class RoomMainFragment extends Fragment {

    private Bitmap imageBitmap;
    private String imagePath;
    private Room currRoom;
    private User currUser;
    private List<RoomType> types;

    public TextView roomName;
    public EditText roomRank;
    public EditText roomAddress;
    public EditText roomDescription;
    public EditText roomMinNumPeople;
    public EditText roomMaxNumPeople;
    public ImageView roomImage;
    public TextView  roomTypes;
    public FloatingActionButton roomSaveBtn;
    public FloatingActionButton roomAddTypesBtn;
    public FloatingActionButton roomEditImagrBtn;
    public ImageButton roomEditBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.room_main_fragment, container, false);
        currRoom = getParentFragment().getArguments().getParcelable("curr_room");

       /* roomName = (TextView) view.findViewById(R.id.roomName);
        roomRank = (EditText) view.findViewById(R.id.rankRoom);
        roomAddress = (EditText) view.findViewById(R.id.addressRoom);
        roomDescription = (EditText) view.findViewById(R.id.descriptionRoom);
        roomMinNumPeople = (EditText) view.findViewById(R.id.minNumValue_Room);
        roomMaxNumPeople = (EditText) view.findViewById(R.id.maxNumValue_Room);
        roomImage = (ImageView) view.findViewById(R.id.roomImage);
        roomTypes = (EditText) view.findViewById(R.id.room_types_textView);
        roomSaveBtn = (FloatingActionButton) view.findViewById(R.id.room_SaveChanges_Btn);
*/
        return view;
    }
}