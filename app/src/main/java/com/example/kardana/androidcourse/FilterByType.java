package com.example.kardana.androidcourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.kardana.androidcourse.Model.Room;

/**
 * Created by Dana on 31-May-18.
 */

public enum FilterByType {
    NAME(""),
    CATEGORY(MainActivity.context.getString(R.string.category)),
    RANK(MainActivity.context.getString(R.string.rank)),
    LOCATION(MainActivity.context.getString(R.string.location));

    private String name;
    private static List<Room> originalData = null;
    private static HashMap<String,Room> roomsAfterFilters = new HashMap<String,Room>();

    FilterByType(String name)
    {
        this.name = name;
    }

    private enum RankFilter
    {
        FIVE_STARS(MainActivity.context.getString(R.string.five_stars)),
        FOUR_STARS(MainActivity.context.getString(R.string.four_stars)),
        THREE_STARS(MainActivity.context.getString(R.string.three_stars));
        private String filter;

        RankFilter(String filter)
        {
            this.filter = filter;
        }

        private String getString()
        {
            return filter;
        }

        private double getDouble()
        {
            switch (this)
            {
                case FIVE_STARS:
                    return 5;
                case FOUR_STARS:
                    return 4;
                case THREE_STARS:
                    return 3;
            }

            return 0;
        }
    }

    public static FilterByType getTypeByName(String name)
    {
        for (FilterByType type : FilterByType.values()) {
            if (type.name.compareTo(name) == 0)
            {
                return type;
            }
        }
        return null;

    }

    public static List<Room> filterByType(List<Room> data, HashMap<FilterByType, List<String>> constraints)
    {
        originalData = data;
        roomsAfterFilters.clear();

        for (FilterByType filterType : constraints.keySet()) {
            switch (filterType) {
                case NAME:
                    for (String constraint : constraints.get(filterType)) {
                        constraint = constraint.toLowerCase();
                        roomsAfterFilters.putAll(filterByName(constraint));
                    }
                    break;
                case CATEGORY:
                    return null;
                case RANK:
                    for (String constraint : constraints.get(filterType)) {
                        constraint = constraint.toLowerCase();
                        roomsAfterFilters.putAll(filterByRank(constraint));
                    }
                    break;
                case LOCATION:
                    return null;
            }
        }

        List<Room> results = new ArrayList<Room>();
        results.addAll(roomsAfterFilters.values());

        return results;
    }

    private static HashMap<String,Room> filterByName(String constraint)
    {
        HashMap<String,Room> filteredArrList = new HashMap<String,Room>();

        for (int i = 0; i < originalData.size(); i++) {
            String data =  originalData.get(i).getName();
            if (data.toLowerCase().contains(constraint.toString())) {
                String roomName=originalData.get(i).getName();
                String roomAddress=originalData.get(i).getAddress();
                String roomDescription=originalData.get(i).getDescription();
                double roomRank=originalData.get(i).getRank();
                filteredArrList.put(roomName,new Room(roomName,roomAddress,roomDescription,roomRank));
            }
        }

        return filteredArrList;
    }

    private static HashMap<String,Room> filterByRank(String constraint)
    {
        HashMap<String,Room> filteredArrList = new HashMap<String,Room>();

        for (int i = 0; i < originalData.size(); i++) {
            double data =  originalData.get(i).getRank();
            if (data >= getRankByFilter(constraint.toString())) {
                String roomName=originalData.get(i).getName();
                String roomAddress=originalData.get(i).getAddress();
                String roomDescription=originalData.get(i).getDescription();
                double roomRank=originalData.get(i).getRank();
                filteredArrList.put(roomName,new Room(roomName,roomAddress,roomDescription,roomRank));
            }
        }

        return filteredArrList;
    }

    private static double getRankByFilter(String filter)
    {
        for (RankFilter rankFilter : RankFilter.values()) {
            if (rankFilter.getString() == filter)
            {
                return rankFilter.getDouble();
            }
        }

        return 0;
    }
}
