package com.example.android.mogifortourists;

/**
 * Created by dkots on 08/11/2017.
 */

public class Location {
    private int titleStringId;
    private int textStringId;
    private int timeStringId;
    private int adressStringId;
    private int imageResourceId;

    public Location (int title,int text,int when,int where,int image){
        titleStringId = title;
        textStringId = text;
        timeStringId = when;
        adressStringId = where;
        imageResourceId = image;
    }
    public int getTitleStringId(){
        return titleStringId;
    }

    public int getTextStringId(){
        return textStringId;
    }

    public int getTimeStringId(){ return timeStringId; }

    public int getAdressStringId(){
        return adressStringId;
    }

    public int getImageResourceId(){ return imageResourceId; }
}
