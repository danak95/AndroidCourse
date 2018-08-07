package com.example.kardana.androidcourse.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.Model.User;
import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.RoomType;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dana on 16-Jun-18.
 */

public class RoomMainFragment extends Fragment {

    //For image
    static final int GALLERY_POSITION = 0;
    static final int CAMERA_POSITION = 1;
    static final int REQUEST_IMAGE_GALLERY = 1;
    static final int REQUEST_IMAGE_CAMERA = 2;

    private Bitmap imageBitmap;
    private String imagePath;
    private Room currRoom;
    private User currUser;
    private List<RoomType> types;

    public TextView roomName;
    public TextView roomRank;
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

        roomName = (TextView) view.findViewById(R.id.roomName);
        roomRank = (TextView) view.findViewById(R.id.rankRoom);
        roomAddress = (EditText) view.findViewById(R.id.roomAddress);
        roomDescription = (EditText) view.findViewById(R.id.descriptionRoom);
        roomMinNumPeople = (EditText) view.findViewById(R.id.minNumValue_Room);
        roomMaxNumPeople = (EditText) view.findViewById(R.id.maxNumValue_Room);
        roomImage = (ImageView) view.findViewById(R.id.roomImage);
        roomTypes = (TextView) view.findViewById(R.id.room_types_textView);
        roomSaveBtn = (FloatingActionButton) view.findViewById(R.id.room_SaveChanges_Btn);
        roomAddTypesBtn = (FloatingActionButton) view.findViewById(R.id.room_types_btn);
        roomEditImagrBtn = (FloatingActionButton) view.findViewById(R.id.pickImage_room_btn);
        roomEditBtn = (ImageButton) view.findViewById(R.id.update_room_btn);

        roomAddress.setEnabled(false);
        roomDescription.setEnabled(false);
        roomMinNumPeople.setEnabled(false);
        roomMaxNumPeople.setEnabled(false);

        Model.getInstance().getCurrentUser(new Model.IGetCurrentUserCallback() {
            @Override
            public void onComplete(User user) {
                currUser = user;
            }
        });
        String types = "";

        roomName.setText(currRoom.getName());
        roomRank.setText(Double.toString(currRoom.getRank()));
        roomAddress.setText(currRoom.getAddress());
        roomDescription.setText(currRoom.getDescription());
        roomMinNumPeople.setText(Integer.toString(currRoom.getMinNumOfPeople()));
        roomMaxNumPeople.setText(Integer.toString(currRoom.getMaxNumOfPeople()));
        for (RoomType type : currRoom.getTypes())
        {
            types = types + "," + type.toString();
        }
        roomTypes.setText(types);

        // The user doesn't has the permissions
        if (!currRoom.getOwnerId().equals(currUser.getUserid()))
        {
            roomSaveBtn.setVisibility(View.INVISIBLE);
            roomAddTypesBtn.setVisibility(View.INVISIBLE);
            roomEditImagrBtn.setVisibility(View.INVISIBLE);
            roomEditBtn.setVisibility(View.INVISIBLE);
        }
        else {
            roomEditBtn.setVisibility(View.VISIBLE);
            roomSaveBtn.setVisibility(View.INVISIBLE);
            roomAddTypesBtn.setVisibility(View.INVISIBLE);
            roomEditImagrBtn.setVisibility(View.INVISIBLE);
        }

        Model.getInstance().getImage(currRoom.getImagePath(), new Model.GetImageListener() {
            @Override
            public void onDone(Bitmap imageBitmap) {
                roomImage.setImageBitmap(imageBitmap);
            }
        });
        

        roomEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roomEditBtn.setVisibility(View.INVISIBLE);
                roomSaveBtn.setVisibility(View.VISIBLE);
                roomAddTypesBtn.setVisibility(View.VISIBLE);
                roomEditImagrBtn.setVisibility(View.VISIBLE);
                roomAddress.setEnabled(true);
                roomDescription.setEnabled(true);
                roomMinNumPeople.setEnabled(true);
                roomMaxNumPeople.setEnabled(true);
            }
        });

        roomSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<RoomType> types = new ArrayList<RoomType>();

                if (!selected.equals(null))
                {
                    int index = 0;
                    for (RoomType type: RoomType.values())
                    {
                        if (selected[index])
                            types.add(type);
                        index++;
                    }

                    currRoom.setAddress(roomAddress.getText().toString());
                    currRoom.setDescription(roomDescription.getText().toString());
                    currRoom.setMinNumOfPeople(Integer.parseInt(roomMinNumPeople.getText().toString()));
                    currRoom.setMaxNumOfPeople(Integer.parseInt(roomMaxNumPeople.getText().toString()));
                    currRoom.setTypes(types);

                    Model.getInstance().saveImage(Room.IMAGE_PATH, currRoom.getId(), imageBitmap, new Model.SaveImageListener() {
                        @Override
                        public void onDone(String url) {
                            Model.getInstance().addRoom(currRoom);
                            Toast.makeText(getContext(), "Room's updates saved successfully!", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext(), "Error while saving the updates!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        roomAddTypesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typesDialog();
            }
        });

        roomEditImagrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowImageDialog();
            }
        });


        return view;
    }

    // Types Dialog
    boolean[] selected = new boolean[RoomType.values().length];
    String[] data = new String[RoomType.values().length];

    public void typesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int index = 0;
        for (RoomType type : RoomType.values())
        {
            data[index] = type.getName();
            index++;
        }

        builder.setTitle("בחר סוג חדר");
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "CANCEL", Toast.LENGTH_LONG).show();
            }
        });
        builder.setMultiChoiceItems(data, selected, new
                DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        selected[which] = isChecked;
                    }
                });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String selects = "";
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i]) {
                        selects = selects + " , " + data[i];
                    }
                }
                Toast.makeText(getActivity(), "OK" , Toast.LENGTH_SHORT).show();
                roomTypes.setText(selects);
            }
        });
        builder.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "CANCEL",
                                Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    ///// Images /////

    // This function manages the picking image - opens a dialog for choosing between
    // gallery and camera
    private void ShowImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(R.string.image_edit_title).setItems(R.array.image_array,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == GALLERY_POSITION) {
                            galleryIntent();
                        } else if (which == CAMERA_POSITION) {
                            cameraIntent();
                        }
                    }
                });

        builder.show();
    }

    // This function creates an intent for taking picture by camera
    public void cameraIntent() {
        Intent takePictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);
        }
    }

    // This function creates an intent for taking picture from gallery
    public void galleryIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }

    // This function handling the result of galleryIntent or cameraIntent
    // and updates the user's image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), contentURI);
                    roomImage.setImageBitmap(imageBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == REQUEST_IMAGE_CAMERA) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            roomImage.setImageBitmap(imageBitmap);
        }
    }
}