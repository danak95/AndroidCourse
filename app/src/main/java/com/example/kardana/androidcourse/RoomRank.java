package com.example.kardana.androidcourse;

/**
 * Created by Dana on 31-Jul-18.
 */

public enum RoomRank {
    ONE_STAR(MainActivity.context.getString(R.string.one_star)),
    TWO_STARS(MainActivity.context.getString(R.string.two_stars)),
    THREE_STARS(MainActivity.context.getString(R.string.three_stars)),
    FOUR_STARS(MainActivity.context.getString(R.string.four_stars)),
    FIVE_STARS(MainActivity.context.getString(R.string.five_stars));

    private String name;

    RoomRank(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
