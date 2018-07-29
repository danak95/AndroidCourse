package com.example.kardana.androidcourse;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.User;

import java.io.IOException;


public class AddNewMemberActivity extends AppCompatActivity {
    static final int GALLERY_POSITION = 0;
    static final int CAMERA_POSITION = 1;
    static final int REQUEST_IMAGE_GALLERY = 1;
    static final int REQUEST_IMAGE_CAMERA = 2;
    private User newMember;
    private ImageView avatar;
    private Bitmap imageBitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);
        avatar = this.findViewById(R.id.new_member_image);
        newMember = new User();
        FloatingActionButton save = this.findViewById(R.id.add_user_btn);
        final AddNewMemberActivity v = this;
        final User[] newUser = new User[1];

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save image
                if (imageBitmap != null) {
                    Model.instance.saveImage(imageBitmap, new Model.SaveImageListener() {
                        @Override
                        public void onDone(String url) {

                        }
                    });

                    // Save new user to Firebase
                    newMember.setUserid("2");
                    newMember.setName(((EditText) v.findViewById(R.id.name_field)).getText().toString());
                    newMember.setBirthDate(((EditText) v.findViewById(R.id.birthdate_field)).getText().toString());
                    if (((Switch)v.findViewById(R.id.newMember_gender)).isChecked())
                    {
                        newMember.setGender("Male");
                    }
                    else
                    {
                        newMember.setGender("Female");
                    }
                    newMember.setPhone(((EditText)v.findViewById(R.id.phone_field)).getText().toString());
                    newMember.setEmail(((EditText) v.findViewById(R.id.email_field_login)).getText().toString());
                    newMember.setPassword(((EditText) v.findViewById(R.id.password_field_login)).getText().toString());
                    Model.instance.AddNewMember(newMember, new Model.IAddNewUser()
                    {
                        @Override
                        public void onComplete(User user) {
                            newUser[0] =user;
                            if (newUser[0] != null)
                            {
                                Toast.makeText(AddNewMemberActivity.this, "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
                                Intent main_intent = new Intent(v, MainActivity.class);
                                startActivity(main_intent);
                            }
                        }
                    });
                }
            }
        });

        // Edit image button- opens a dialog for picking image from gallery / camera
        Button editImage = this.findViewById(R.id.image_btn);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowImageDialog();
            }
        });
    }

    // This function manages the picking image - opens a dialog for choosing between
    // gallery and camera
    private void ShowImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
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
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == REQUEST_IMAGE_GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    avatar.setImageBitmap(imageBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddNewMemberActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == REQUEST_IMAGE_CAMERA) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(imageBitmap);
        }
    }
}