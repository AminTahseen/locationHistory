package com.masterandroid.backgroundservice;

import com.google.gson.annotations.SerializedName;

public class place {

    @SerializedName("placeId")
    private int placeId;

    @SerializedName("placeLongitude")
    private double placeLongitude;

    @SerializedName("placeLatitude")
    private double placeLatitude;

    @SerializedName("placeAddress")
    private String placeAddress;

    @SerializedName("city")
    private String city;

    public place() { }

    public place(double placeLongitude, double placeLatitude, String placeAddress, String city) {
        this.placeLongitude = placeLongitude;
        this.placeLatitude = placeLatitude;
        this.placeAddress = placeAddress;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }
}
