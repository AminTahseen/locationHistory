package com.masterandroid.backgroundservice;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class place {

    private String userId;

    private double placeLatitude;

    private double placeLongitude;

    @SerializedName("formatted_address")
    private String placeAddress;

    @SerializedName("name")
    private String placeName;

    @SerializedName("types")
    private ArrayList<String> placeType;

    public place(String userId, double placeLatitude, double placeLongitude, String placeAddress, String placeName, ArrayList<String> placeType) {
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.placeAddress = placeAddress;
        this.placeName = placeName;
        this.placeType = placeType;
        this.userId=userId;
    }

    public place(double placeLatitude, double placeLongitude, String placeAddress) {
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.placeAddress = placeAddress;
    }

    public place() { }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getPlaceLatitude() {
        return placeLatitude;
    }

    public void setPlaceLatitude(double placeLatitude) {
        this.placeLatitude = placeLatitude;
    }

    public double getPlaceLongitude() {
        return placeLongitude;
    }

    public void setPlaceLongitude(double placeLongitude) {
        this.placeLongitude = placeLongitude;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public ArrayList<String> getPlaceType() { return placeType; }

    public void setPlaceType(ArrayList<String> placeType) { this.placeType = placeType; }


    @Override
    public String toString() {
        return "place{" +
                "placeLatitude=" + placeLatitude +
                ", placeLongitude=" + placeLongitude +
                ", placeAddress='" + placeAddress + '\'' +
                ", placeName='" + placeName + '\'' +
                ", placeType=" + placeType +
                '}';
    }

}
