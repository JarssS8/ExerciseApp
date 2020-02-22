package com.jars.exerciseapp.beans;

import java.util.ArrayList;

public class Circuit {
    private String name;
    private Integer weekId;
    private Integer dayId;
    private ArrayList<Integer> imagesInt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeekId() {
        return weekId;
    }

    public void setWeekId(Integer weekId) {
        this.weekId = weekId;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public ArrayList<Integer> getImagesInt() {
        return imagesInt;
    }

    public void setImagesInt(ArrayList<Integer> imagesInt) {
        this.imagesInt = imagesInt;
    }
}
