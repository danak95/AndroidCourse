package com.example.kardana.androidcourse.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.Review;
import com.example.kardana.androidcourse.Model.ReviewViewModel;
import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.Model.User;
import com.example.kardana.androidcourse.Model.UserViewModel;
import com.example.kardana.androidcourse.R;
import com.example.kardana.androidcourse.ReviewsListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dana on 16-Jun-18.
 */

public class RoomReviewsFragment extends Fragment {
    //For image
    static final int GALLERY_POSITION = 0;
    static final int CAMERA_POSITION = 1;
    static final int REQUEST_IMAGE_GALLERY = 1;
    static final int REQUEST_IMAGE_CAMERA = 2;

    private ReviewsListAdapter reviewsListAdapter;
    private List<Review> reviewList= new ArrayList<Review>();
    private View view;
    private Review newReview;
    private User currUser = null;
    private Room currRoom = null;
    private ReviewViewModel dataModel;

    private ImageView image;
    private Bitmap    reviewImageBitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.room_reviews_fragment, container, false);
        FloatingActionButton addReviewButton = view.findViewById(R.id.floatingAddReviewButton);

        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newReview = new Review();
                ShowAddReview(view);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewsListAdapter = new ReviewsListAdapter(view.getContext(), reviewList);
        ListView listView = view.findViewById(R.id.reviews_list);
        listView.setAdapter(reviewsListAdapter);
        currRoom = this.getParentFragment().getArguments().getParcelable("curr_room");

        dataModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        dataModel.getData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                List<Review> filteredReviews = new ArrayList<>();

                for (Review review : reviews)
                {
                    if (review.getRoomId().equals(currRoom.getId()))
                    {
                        filteredReviews.add(review);
                    }
                }
                reviewsListAdapter.updateReviewsList(filteredReviews);
                reviewsListAdapter.notifyDataSetChanged();
            }
        });

        // Get current user
        UserViewModel userDataModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userDataModel.getData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                String userid = Model.getInstance().getCurrentUserId();

                for (User user :  users) {
                    if (user.getUserid().equals(userid)) {
                        currUser = user;
                        break;
                    }
                }
            }
        });


    }

    public void ShowAddReview(View view)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.add_review_dialog, null);
        final EditText comment = v.findViewById(R.id.addReview_comment_field);
        //RadioGroup group = v.findViewById(R.id.)
        RadioButton star1 = v.findViewById(R.id.one_star_newReview);
        RadioButton star2 = v.findViewById(R.id.two_star_newReview);
        RadioButton star3 = v.findViewById(R.id.three_star_newReview);
        RadioButton star4 = v.findViewById(R.id.four_star_newReview);
        RadioButton star5 = v.findViewById(R.id.five_star_newReview);
        image = v.findViewById(R.id.review_imageView);
        ImageButton imageButton = v.findViewById(R.id.addReview_image_btn);
        builder.setView(v);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowImageDialog();
                newReview.setContent(comment.getText().toString());
                //newReview

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        // Create the AlertDialog object and return it
        builder.show();
    }



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
                    reviewImageBitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), contentURI);
                    image.setImageBitmap(reviewImageBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this.getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == REQUEST_IMAGE_CAMERA) {
            reviewImageBitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(reviewImageBitmap);
        }
    }

}
