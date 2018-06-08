package com.example.kardana.androidcourse;

/**
 * Created by Dana on 30-May-18.
 */

public class ExpandedMenuItem {
    private String name = "";
    private boolean isChecked = false;
    private ExpandedMenuHeader group;

    public ExpandedMenuItem(String name, ExpandedMenuHeader group)
    {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked)
    {
        this.isChecked=isChecked;
    }

    public ExpandedMenuHeader getGroup()
    {
        return this.group;
    }

    public void setGroup(ExpandedMenuHeader group)
    {
        this.group = group;
    }

}
