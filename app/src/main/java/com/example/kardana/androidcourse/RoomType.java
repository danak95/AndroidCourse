package com.example.kardana.androidcourse;

/**
 * Created by Dana on 28-Jul-18.
 */

public enum RoomType {
    HORROR(ESCApplication.getContext().getString(R.string.horror)),
    VR(ESCApplication.getContext().getString(R.string.vr)),
    ROLE_PLAYING(ESCApplication.getContext().getString(R.string.role_playing)),
    PERFORMANCE(ESCApplication.getContext().getString(R.string.performance)),
    COMEDY(ESCApplication.getContext().getString(R.string.comedy)),
    CULINARY(ESCApplication.getContext().getString(R.string.culinary)),
    WITH_ACTOR(ESCApplication.getContext().getString(R.string.with_actor)),
    WITHOUT_ACTOR(ESCApplication.getContext().getString(R.string.without_actor));

    private String name;

    RoomType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public static RoomType getRoomTypeByName(String name)
    {
        for(RoomType type : RoomType.values())
        {
            if (type.getName().equals(name))
            {
                return type;
            }
        }

        return null;
    }
}
