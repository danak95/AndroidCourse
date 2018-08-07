package com.example.kardana.androidcourse;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.Review;
import com.example.kardana.androidcourse.Model.Room;
import com.example.kardana.androidcourse.Model.User;

import java.util.List;

public class ReviewsListAdapter extends BaseAdapter {

    private List<Review> data = null;
    private LayoutInflater inflater;
    private ReviewsListAdapter.ViewHolder holder;

    public ReviewsListAdapter(Context context, List<Review> data) {
        this.data = data ;
        inflater = LayoutInflater.from(context);
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
        holder = null;

        if (view == null) {

            holder = new ReviewsListAdapter.ViewHolder();
            view = inflater.inflate(R.layout.room_reviews_item, null);
            holder.reviewItem = view.findViewById(R.id.review_item);
            holder.reviewUserName = view.findViewById(R.id.review_user_name);
            holder.reviewContent = view.findViewById(R.id.review_contentField);
            holder.reviewDate = view.findViewById(R.id.review_dateField);
            holder.reviewImage = view.findViewById(R.id.review_RoomImage);
            holder.reviewUserImage = view.findViewById(R.id.review_user_image);
            holder.reviewRank = view.findViewById(R.id.review_rank);
            view.setTag(holder);
        } else {
            holder = (ReviewsListAdapter.ViewHolder) view.getTag();
        }

        holder.reviewRank.setText(holder.reviewRank.getText() + String.valueOf(data.get(i).getRank()));
        holder.reviewContent.setText(data.get(i).getContent());
        holder.reviewDate.setText(data.get(i).getDate());
        Model.getInstance().getUserById(data.get(i).getUserId(), new Model.IGetUserByIdCallback() {
            @Override
            public void onComplete(User user) {
                holder.reviewUserName.setText(user.getName());
                Model.getInstance().getImage(user.getImagePath(), new Model.GetImageListener() {
                    @Override
                    public void onDone(Bitmap imageBitmap) {
                        holder.reviewUserImage.setImageBitmap(imageBitmap);
                    }
                });
            }

            @Override
            public void onCancel() {

            }
        });
        Model.getInstance().getImage(data.get(i).getImagePath(), new Model.GetImageListener() {
            @Override
            public void onDone(Bitmap imageBitmap) {
                holder.reviewImage.setImageBitmap(imageBitmap);
            }
        });


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
    }

}
