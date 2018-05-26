package com.example.kardana.androidcourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.Room;

/**
 * Created by Dana on 20-May-18.
 */

public class RoomListAdapter extends BaseAdapter implements Filterable
{

    private List<Room> originalData = null;
    private List<Room> filteredData = null;
    private LayoutInflater inflater;
    private ItemFilter mFilter = new ItemFilter();

    public RoomListAdapter(Context context, List<Room> data) {
        this.filteredData = data ;
        this.originalData = data ;
        inflater = LayoutInflater.from(context);
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

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.room_list_item, null);
            holder.roomListItem = (LinearLayout)convertView.findViewById(R.id.room_list_item);
            holder.roomName = (TextView) convertView.findViewById(R.id.room_name);
            holder.roomAddress = (TextView) convertView.findViewById(R.id.room_address);
            holder.roomDescription = (TextView) convertView.findViewById(R.id.room_description);
            holder.roomImage = (ImageView) convertView.findViewById(R.id.room_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.roomName.setText(filteredData.get(position).getName());
        holder.roomAddress.setText(filteredData.get(position).getAddress());
        holder.roomDescription.setText(filteredData.get(position).getDescription());
        holder.roomImage.setBackgroundResource(R.drawable.ic_menu_camera);
//        holder.roomListItem.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View v) {
//
//                Toast.makeText(MainActivity.this, mDisplayedValues.get(position).name, Toast.LENGTH_SHORT).show();
//            }
//        });

        return convertView;
    }

    private class ViewHolder {
        LinearLayout roomListItem;
        TextView roomName;
        TextView roomAddress;
        TextView roomDescription;
        ImageView roomImage;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
            ArrayList<Room> FilteredArrList = new ArrayList<Room>();

            if (originalData == null) {
                originalData = new ArrayList<Room>(filteredData); // saves the original data in mOriginalValues
            }

            if (constraint == null || constraint.length() == 0) {

                // set the Original result to return
                results.count = originalData.size();
                results.values = originalData;
            } else {
                constraint = constraint.toString().toLowerCase();
                for (int i = 0; i < originalData.size(); i++) {
                    String data = originalData.get(i).getName();
                    if (data.toLowerCase().contains(constraint.toString())) {
                        FilteredArrList.add(new Room(originalData.get(i).getName(),originalData.get(i).getAddress(),originalData.get(i).getDescription()));
                    }
                }
                // set the Filtered result to return
                results.count = FilteredArrList.size();
                results.values = FilteredArrList;
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Room>) results.values;
            notifyDataSetChanged();
        }

    }
}
