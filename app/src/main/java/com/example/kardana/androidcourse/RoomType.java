package com.example.kardana.androidcourse;

/**
 * Created by Dana on 28-Jul-18.
 */

public enum RoomType {
    HORROR(MainActivity.context.getString(R.string.horror)),
    VR(MainActivity.context.getString(R.string.vr)),
    ROLE_PLAYING(MainActivity.context.getString(R.string.role_playing)),
    PERFORMANCE(MainActivity.context.getString(R.string.performance)),
    COMEDY(MainActivity.context.getString(R.string.comedy)),
    CULINARY(MainActivity.context.getString(R.string.culinary)),
    WITH_ACTOR(MainActivity.context.getString(R.string.with_actor)),
    WITHOUT_ACTOR(MainActivity.context.getString(R.string.without_actor));

    private String name;

    RoomType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
