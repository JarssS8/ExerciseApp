package com.jars.exerciseapp.beans;

public class Tutorial {
    private Integer image;
    private String header;
    private String description;

    public Tutorial(Integer image, String header, String description){
        this.image=image;
        this.header=header;
        this.description=description;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
