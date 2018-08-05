package com.example.kardana.androidcourse;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kardana.androidcourse.Fragments.RoomFragment;
import com.example.kardana.androidcourse.Model.Review;
import com.example.kardana.androidcourse.Model.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReviewsListAdapter {/*

    private List<Review> data = null;
    private LayoutInflater inflater;
    private HashMap<FilterByType,List<String>> constraints;
    private Context context;

    public ReviewsListAdapter(Context context, List<Review> data) {
        this.data = data ;
        inflater = LayoutInflater.from(context);
        this.constraints = new HashMap<FilterByType,List<String>>();
        this.context = context;
    }

    public void setConstraints(HashMap<FilterByType,List<String>> constraints)
    {
        this.constraints.clear();
        this.constraints.putAll(constraints);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ReviewsListAdapter.ViewHolder holder = null;

        if (convertView == null) {

            holder = new ReviewsListAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.room_reviews_item, null);
            holder.reviewsList = convertView.findViewById(R.id.)



            holder.roomListItem = convertView.findViewById(R.id.room_list_item);
            holder.roomName = convertView.findViewById(R.id.room_name);
            holder.roomAddress = convertView.findViewById(R.id.room_address);
            holder.roomDescription = convertView.findViewById(R.id.room_description);
            holder.roomImage = convertView.findViewById(R.id.room_image);
            convertView.setTag(holder);
        } else {
            holder = (RoomListAdapter.ViewHolder) convertView.getTag();
        }
        holder.roomName.setText(filteredData.get(position).getName());
        holder.roomAddress.setText(filteredData.get(position).getAddress());
        holder.roomDescription.setText(filteredData.get(position).getDescription());
        holder.roomImage.setBackgroundResource(R.drawable.ic_menu_camera);

        holder.roomListItem.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Fragment fragment = new RoomFragment();
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack(null).commit();
            }
        });

        return convertView;
    }

    public void updateRoomsList(List<Room> newlist) {
        data.clear();
        data.addAll(newlist);
        this.notifyDataSetChanged();
    }

    private class ViewHolder {
        LinearLayout reviewsList;
        TextView userName;
        TextView reviewDate;
        EditText contnet;
        ImageView reviewImage;
        ImageView userImage;
        ConstraintLayout reviewRank;
    }

    public Filter getFilter() {
        return mFilter;
    }

    public void clearResults()
    {
        mFilter.clearResults();
    }

    private class RoomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
            List<Room> filteredArrList = new ArrayList<Room>();

            if (data == null) {
                data = new ArrayList<Room>(filteredData); // saves the original data in mOriginalValues
            }

            if (filterType != null && filterType == FilterByType.NAME) {
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = data.size();
                    results.values = data;

                    filterType = null;
                    return results;
                }

                List<String> nameConstraint = new ArrayList<String>();
                nameConstraint.add(constraint.toString());
                constraints.put(FilterByType.NAME, nameConstraint);
            }

            if (constraints == null || constraints.size() == 0) {

                // set the Original result to return
                results.count = data.size();
                results.values = data;
            } else {
                //constraint = constraint.toString().toLowerCase();
                filteredArrList = FilterByType.filterByType(data, constraints);
                // set the Filtered result to return
                results.count = filteredArrList.size();
                results.values = filteredArrList;
            }

            filterType = null;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Room>) results.values;
            notifyDataSetChanged();
        }

        public void clearResults()
        {
            filteredData = data;
            notifyDataSetChanged();
        }

    }*/
}
