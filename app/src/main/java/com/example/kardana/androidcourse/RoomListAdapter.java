package com.example.kardana.androidcourse;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kardana.androidcourse.Fragments.RoomFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.kardana.androidcourse.Model.GlobalListener;
import com.example.kardana.androidcourse.Model.Model;
import com.example.kardana.androidcourse.Model.Review;
import com.example.kardana.androidcourse.Model.Room;

/**
 * Created by Dana on 20-May-18.
 */

public class RoomListAdapter extends BaseAdapter implements Filterable
{

    private List<Room> originalData = null;
    private List<Room> filteredData = null;
    private LayoutInflater inflater;
    private RoomFilter mFilter = new RoomFilter();
    private HashMap<FilterByType,List<String>> constraints;
    private FilterByType filterType;
    private ViewHolder holder;

    public RoomListAdapter(Context context, List<Room> data) {
        this.filteredData = data ;
        this.originalData = data ;
        inflater = LayoutInflater.from(context);
        this.constraints = new HashMap<FilterByType,List<String>>();
    }

    public void setFilterType(FilterByType filterType)
    {
        this.filterType = filterType;
    }

    public void setConstraints(HashMap<FilterByType,List<String>> constraints)
    {
        this.constraints.clear();
        this.constraints.putAll(constraints);
    }

    public int getCount() {
        return filteredData.size();
    }

    public Object getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView( int position, View convertView, ViewGroup parent) {
        Room wantedRoom = new Room();
        holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            wantedRoom =  originalData.get(position);
            convertView = inflater.inflate(R.layout.room_list_item, null);
            holder.roomListItem = convertView.findViewById(R.id.room_list_item);
            holder.roomName = convertView.findViewById(R.id.room_name);
            holder.roomAddress = convertView.findViewById(R.id.room_address);
            holder.roomDescription = convertView.findViewById(R.id.room_description);
            holder.roomImage = convertView.findViewById(R.id.room_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.roomName.setText(filteredData.get(position).getName());
        holder.roomAddress.setText(filteredData.get(position).getAddress());
        holder.roomDescription.setText(filteredData.get(position).getDescription());

        if (wantedRoom.getId() != null) {
            Model.getInstance().putImageViewRoom(wantedRoom.getId(), wantedRoom.getImagePath(), convertView.getContext(), new GlobalListener<Bitmap>() {
                @Override
                public void onComplete(Bitmap bitmap) {
                    Model.getInstance().displayImageView(holder.roomImage, bitmap, 1);

                }
            });
        }

       /*Model.getInstance().getImage(filteredData.get(position).getImagePath(), new Model.GetImageListener() {
            @Override
            public void onDone(Bitmap imageBitmap) {
                // Update Data
                holder.roomImage.setImageBitmap(imageBitmap);
            }
        }, null);*/
        holder.roomListItem.setTag(position);

        holder.roomListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer)view.getTag();
                Room currRoom = (Room)getItem(position);
                Fragment fragment = new RoomFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("curr_room", currRoom);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity)(view.getContext())).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment).addToBackStack(null).commit();
            }
        });



        return convertView;
    }

    public void updateRoomsList(List<Room> newlist) {
        originalData.clear();
        originalData.addAll(newlist);
        this.notifyDataSetChanged();
    }

    private class ViewHolder {
        LinearLayout roomListItem;
        TextView roomName;
        TextView roomAddress;
        TextView roomDescription;
        ImageView roomImage;
        Room item;
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

            if (originalData == null) {
                originalData = new ArrayList<Room>(filteredData); // saves the original data in mOriginalValues
            }

            if (filterType != null && filterType == FilterByType.NAME) {
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = originalData.size();
                    results.values = originalData;

                    filterType = null;
                    return results;
                }

                List<String> nameConstraint = new ArrayList<String>();
                nameConstraint.add(constraint.toString());
                constraints.put(FilterByType.NAME, nameConstraint);
            }

            if (constraints == null || constraints.size() == 0) {

                // set the Original result to return
                results.count = originalData.size();
                results.values = originalData;
            } else {
                //constraint = constraint.toString().toLowerCase();
                filteredArrList = FilterByType.filterByType(originalData, constraints);
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
            filteredData = originalData;
            notifyDataSetChanged();
        }

    }
}
