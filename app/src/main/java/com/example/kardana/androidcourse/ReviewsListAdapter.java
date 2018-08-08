package com.example.kardana.androidcourse;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kardana.androidcourse.Fragments.RoomMainFragment;
import com.example.kardana.androidcourse.Model.GlobalListener;
import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.Review;
import com.example.kardana.androidcourse.Model.User;
import com.example.kardana.androidcourse.Model.UserAsyncDao;
import com.example.kardana.androidcourse.Model.UserViewModel;

import java.util.List;

public class ReviewsListAdapter extends BaseAdapter {

    private List<Review> data;
    private LayoutInflater inflater;
    private ReviewsListAdapter.ViewHolder holder;
    private FragmentActivity fragmentActivity;

    public ReviewsListAdapter(FragmentActivity activity, Context context, List<Review> data) {
        this.data = data ;
        inflater = LayoutInflater.from(context);
        fragmentActivity = activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final User[] userReview = {new User()};
        Review wantedReview = new Review();
        holder = null;

        if (view == null) {

            holder = new ReviewsListAdapter.ViewHolder();
            wantedReview =  data.get(i);
            holder.item = wantedReview;
            view = inflater.inflate(R.layout.room_reviews_item, null);
            holder.reviewItem = view.findViewById(R.id.review_item);
            holder.reviewUserName = view.findViewById(R.id.review_user_name);
            holder.reviewContent = view.findViewById(R.id.review_contentField);
            holder.reviewDate = view.findViewById(R.id.review_dateField);
            holder.reviewImage = view.findViewById(R.id.review_RoomImage);
            holder.reviewUserImage = view.findViewById(R.id.review_user_image);
            holder.reviewRank = view.findViewById(R.id.review_rank);

            // Get user data
            UserViewModel dataModel = ViewModelProviders.of(fragmentActivity).get(UserViewModel.class);
            dataModel.getData().observe(fragmentActivity, new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> users) {
                    String userid = Model.getInstance().getCurrentUserId();

                    for (User user :  users) {
                        if (user.getUserid().equals(holder.item.getUserId())) {
                            userReview[0] = user;
                            break;
                        }
                    }
                }
            });

            view.setTag(holder);
        } else {
            holder = (ReviewsListAdapter.ViewHolder) view.getTag();
        }

        holder.reviewRank.setText("דירוג: 5/" + String.valueOf(data.get(i).getRank()));
        holder.reviewContent.setText(data.get(i).getContent());
        holder.reviewDate.setText(data.get(i).getDate());

        Model.getInstance().putImageViewUser(userReview[0].getUserid(), userReview[0].getImagePath(), view.getContext(), new GlobalListener<Bitmap>() {
            @Override
            public void onComplete(Bitmap bitmap) {
                Model.getInstance().displayImageView(holder.reviewUserImage, bitmap, 1);
            }
        });

        Model.getInstance().putImageViewReview(wantedReview.getReviewId(), wantedReview.getImagePath(), view.getContext(), new GlobalListener<Bitmap>() {
            @Override
            public void onComplete(Bitmap bitmap) {
                Model.getInstance().displayImageView(holder.reviewImage, bitmap, 1);
            }
        });
        /*Model.getInstance().getUserById(data.get(i).getUserId(), new Model.IGetUserByIdCallback() {
            @Override
            public void onComplete(User user) {
                holder.reviewUserName.setText(user.getName());
                Model.getInstance().getImage(user.getImagePath(), new Model.GetImageListener() {
                    @Override
                    public void onDone(Bitmap imageBitmap) {
                        holder.reviewUserImage.setImageBitmap(imageBitmap);
                    }
                }, null);
            }

            @Override
            public void onCancel() {

            }
        });
        Model.getInstance().getImage(data.get(i).getImagePath(), new Model.GetImageListener() {
            @Override
            public void onDone(Bitmap imageBitmap) {
                String url = holder.reviewImage.getTag().toString();

                if(url.equals(holder.item.getImagePath())) {
                    holder.reviewImage.setImageBitmap(imageBitmap);
                }
            }
        }, null);*/


        return view;
    }
    public void updateReviewsList(List<Review> newlist) {
        data.clear();
        data.addAll(newlist);
        this.notifyDataSetChanged();
    }

    private class ViewHolder {
        ConstraintLayout reviewItem;
        TextView reviewUserName;
        TextView reviewContent;
        TextView reviewDate;
        ImageView reviewImage;
        ImageView reviewUserImage;
        TextView reviewRank;
        Review item;
    }

}
