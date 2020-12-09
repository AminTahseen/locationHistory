package com.masterandroid.backgroundservice;

import com.google.gson.annotations.SerializedName;

public class place {
    private double placeLatitude;
    private double placeLongitude;

    @SerializedName("formatted_address")
    private String placeAddress;
    @SerializedName("name")
    private String placeName;

    private String city;

    public place(double placeLatitude, double placeLongitude, String placeAddress, String city, String placeName) {
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.placeAddress = placeAddress;
        this.placeName=placeName;
        this.city = city;
    }

    public place(double placeLatitude, double placeLongitude, String placeAddress, String city) {
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.placeAddress = placeAddress;
        this.city = city;
    }

    public place(){

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
