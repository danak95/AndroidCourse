package com.example.kardana.androidcourse.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.kardana.androidcourse.AddNewMemberActivity;
import com.example.kardana.androidcourse.Model.User;
import com.example.kardana.androidcourse.R;

import java.io.IOException;

public class MemberProfileFragment extends Fragment {
    private static final String MEMBER_ID= "MEMBER_ID";
    static final int GALLERY_POSITION = 0;
    static final int CAMERA_POSITION = 1;
    static final int REQUEST_IMAGE_GALLERY = 1;
    static final int REQUEST_IMAGE_CAMERA = 2;
    private ImageView memberImage;
    private Bitmap imageBitmap;

    private String memberID;
    private User user;
    private OnFragmentInteractionListener mListener;

    public Switch memberGender;
    public EditText memberPhone;
    public EditText memberEmail;
    public EditText memberPassword;
    public Button   changeImageBtn;
    public ImageButton   saveBtn;
    public ImageButton   cancelBtn;
    public ImageButton   updateBtn;

    public MemberProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userID Parameter 1.
     * @return A new instance of fragment MusicPostDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberProfileFragment newInstance(String userID) {
        MemberProfileFragment fragment = new MemberProfileFragment();
        Bundle args = new Bundle();
        args.putString(MEMBER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            memberID = getArguments().getString(MEMBER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("dev", "OnCreateView");
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_member_profile_, container, false);
        memberGender = (Switch) view.findViewById(R.id.gender_switch);
        memberPhone = view.findViewById(R.id.phone_field);
        memberEmail = view.findViewById(R.id.member_email_field);
        memberPassword = view.findViewById(R.id.member_password_field);
        memberImage = view.findViewById(R.id.member_image);
        saveBtn = (ImageButton) view.findViewById(R.id.save_member_btn);
        updateBtn = (ImageButton) view.findViewById(R.id.update_member_btn);
        cancelBtn = (ImageButton) view.findViewById(R.id.cancel_member_btn);
        changeImageBtn = (Button) view.findViewById(R.id.change_image_btn);

        memberGender.setChecked(true);
        memberPhone.setText("0501234567");
        memberEmail.setText("Check1@gmail.com");
        memberPassword.setText("HelloWorld");

        memberGender.setEnabled(false);
        memberPhone.setEnabled(false);
        memberEmail.setEnabled(false);
        memberPassword.setEnabled(false);
        saveBtn.setEnabled(false);
        cancelBtn.setEnabled(false);
        changeImageBtn.setEnabled(false);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBtn.setEnabled(true);
                cancelBtn.setEnabled(true);
                changeImageBtn.setEnabled(true);
                memberGender.setEnabled(true);
                memberPhone.setEnabled(true);
                memberEmail.setEnabled(true);
                memberPassword.setEnabled(true);
            }
        }
        );

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = new User();
                if (memberGender.isChecked())
                {
                    user.setGender("Male");
                }
                else
                {
                    user.setGender("Female");
                }
                user.setPhone(memberPhone.getText().toString());
                user.setEmail(memberEmail.getText().toString());
                user.setPassword(memberPassword.getText().toString());
                Toast.makeText(view.getContext(), "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberGender.setChecked(true);
                memberPhone.setText("0501234567");
                memberEmail.setText("Check1@gmail.com");
                memberPassword.setText("HelloWorld");
            }
        });

        changeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowImageDialog();
            }
        });

        return view;
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
                    memberImage.setImageBitmap(imageBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == REQUEST_IMAGE_CAMERA) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            memberImage.setImageBitmap(imageBitmap);
        }
    }


    public void onClick(View v) {
        mListener.onFragmentInteraction(memberID);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }
}
