package com.jars.exerciseapp.beans;

import java.util.ArrayList;

public class Circuit {
    private String name;
    private Integer weekId;
    private Integer dayId;
    private ArrayList<String> requiredMaterial;
    private ArrayList<String> optionalMaterial;
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

    public ArrayList<String> getRequiredMaterial() {
        return requiredMaterial;
    }

    public void setRequiredMaterial(ArrayList<String> requiredMaterial) {
        this.requiredMaterial = requiredMaterial;
    }

    public ArrayList<String> getOptionalMaterial() {
        return optionalMaterial;
    }

    public void setOptionalMaterial(ArrayList<String> optionalMaterial) {
        this.optionalMaterial = optionalMaterial;
    }

    public ArrayList<Integer> getImagesInt() {
        return imagesInt;
    }

    public void setImagesInt(ArrayList<Integer> imagesInt) {
        this.imagesInt = imagesInt;
    }
}
