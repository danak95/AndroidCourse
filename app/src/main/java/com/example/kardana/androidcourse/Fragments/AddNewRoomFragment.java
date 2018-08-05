package com.example.kardana.androidcourse.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.Model.User;
import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.RoomType;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewRoomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewRoomFragment extends Fragment {

    // For image
    static final int GALLERY_POSITION = 0;
    static final int CAMERA_POSITION = 1;
    static final int REQUEST_IMAGE_GALLERY = 1;
    static final int REQUEST_IMAGE_CAMERA = 2;

    private Bitmap imageBitmap;
    private String imagePath;
    private Room newRoom;
    private User currUser;
    private List<RoomType> types;

    public EditText newRoomName;
    public EditText newRoomAddress;
    public EditText newRoomDescription;
    public EditText newRoomMinNumPeople;
    public EditText newRoomMaxNumPeople;
    public EditText newRoomUrl;
    public ImageView newRoomImage;
    public FloatingActionButton newRoomSaveBtn;
    public FloatingActionButton newRoomAddImageBtn;
    public Button    newRoomAddTypesBtn;

    private OnFragmentInteractionListener mListener;


    boolean[] selected = new boolean[RoomType.values().length];
    String[] data = new String[RoomType.values().length];
    int index = 0;
    public Dialog typesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        for (RoomType type : RoomType.values())
        {
            data[index] = type.toString();
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
                    if (selected[i]) selects = selects + " " + data[i];
                }
                Toast.makeText(getActivity(), "OK" + selects,
                        Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getActivity(), "CANCEL",
                                Toast.LENGTH_LONG).show();
                    }
                });
        return builder.create();
    }

    public AddNewRoomFragment() {
        // Required empty public constructor
    }


    public static AddNewRoomFragment newInstance() {
        AddNewRoomFragment fragment = new AddNewRoomFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("dev", "Add new room- OnCreateView");
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_new_room_activity, container, false);
        newRoomName = (EditText) view.findViewById(R.id.addRoom_name_field);
        newRoomAddress = (EditText) view.findViewById(R.id.addRoom_address_field);
        newRoomDescription = (EditText) view.findViewById(R.id.addRoom_description_field);
        newRoomMinNumPeople = (EditText) view.findViewById(R.id.addRoom_minNumPeople_field);
        newRoomMaxNumPeople = (EditText) view.findViewById(R.id.addRoom_maxNumPeople_field);
        newRoomUrl = (EditText) view.findViewById(R.id.addRoom_URL_field);
        newRoomImage = (ImageView) view.findViewById(R.id.addRoom_image_field);
        newRoomSaveBtn = (FloatingActionButton) view.findViewById(R.id.addRoom_save_btn);
        newRoomAddImageBtn = (FloatingActionButton) view.findViewById(R.id.addRoom_addImage_btn);
        newRoomAddTypesBtn = (Button) view.findViewById(R.id.addRoom_typesDialog_btn);

        newRoom = new Room();
        newRoom.setName(newRoomName.getText().toString());
        newRoom.setAddress(newRoomAddress.getText().toString());
        newRoom.setDescription(newRoomDescription.getText().toString());
        newRoom.setMinNumOfPeople(Integer.parseInt(newRoomMinNumPeople.getText().toString()));
        newRoom.setMaxNumOfPeople(Integer.parseInt(newRoomMaxNumPeople.getText().toString()));
        newRoom.setRoomSite(newRoomUrl.getText().toString());
        newRoom.setRoomSite("");

        Model.getInstance().getCurrentUser(new Model.IGetCurrentUserCallback() {
            @Override
            public void onComplete(User user) {
                currUser = user;
                newRoom.setOwnerId(currUser.getUserid());
            }
        });

        newRoomSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!selected.equals(null))
                {
                    int index = 0;
                    for (RoomType type: RoomType.values())
                    {
                        if (selected[index])
                            types.add(type);
                        index++;
                    }
                    Model.getInstance().addRoom(newRoom);

                    Model.getInstance().saveImage("Rooms", newRoom.getId(), imageBitmap, new Model.SaveImageListener() {
                        @Override
                        public void onDone(String url) {
                            newRoom.setRoomSite(url);
                        }
                    });
                }
               else
                {
                    Toast.makeText(getContext(), "Error while saving the room!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        newRoomAddTypesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typesDialog();
            }
        });

        newRoomAddImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowImageDialog();
            }
        });

        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


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
                    newRoomImage.setImageBitmap(imageBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == REQUEST_IMAGE_CAMERA) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            newRoomImage.setImageBitmap(imageBitmap);
        }
    }
}
