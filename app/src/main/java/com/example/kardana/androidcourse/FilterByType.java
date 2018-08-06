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
    CATEGORY(ESCApplication.getContext().getString(R.string.category)),
    RANK(ESCApplication.getContext().getString(R.string.rank));

    private String name;
    private static List<Room> originalData = null;
    private static HashMap<String,Room> roomsAfterFilters = new HashMap<String,Room>();

    FilterByType(String name)
    {
        this.name = name;
    }

    private enum RankFilter
    {
        FIVE_STARS(ESCApplication.getContext().getString(R.string.five_stars)),
        FOUR_STARS(ESCApplication.getContext().getString(R.string.four_stars_and_above)),
        THREE_STARS(ESCApplication.getContext().getString(R.string.three_stars_and_above));
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
                    for (String constraint : constraints.get(filterType)) {
                        constraint = constraint.toLowerCase();
                        roomsAfterFilters.putAll(filterByCategory(constraint));
                    }
                    break;
                case RANK:
                    for (String constraint : constraints.get(filterType)) {
                        constraint = constraint.toLowerCase();
                        roomsAfterFilters.putAll(filterByRank(constraint));
                    }
                    break;
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
                filteredArrList.put(roomName,originalData.get(i));
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
                filteredArrList.put(roomName,originalData.get(i));
            }
        }

        return filteredArrList;
    }

    private static HashMap<String,Room> filterByCategory(String constraint)
    {
        HashMap<String,Room> filteredArrList = new HashMap<String,Room>();

        for (int i = 0; i < originalData.size(); i++) {
            for(RoomType roomType : originalData.get(i).getTypes()) {
                if (roomType.getName().equals(constraint.toString())) {
                    String roomName = originalData.get(i).getName();
                    filteredArrList.put(roomName, originalData.get(i));
                }
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
