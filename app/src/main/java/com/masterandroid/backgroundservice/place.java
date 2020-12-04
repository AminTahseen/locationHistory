package com.masterandroid.backgroundservice;

public class place {

    private double placeLatitude;
    private double placeLongitude;
    private String placeAddress;
    private String city;

    public place(double placeLatitude, double placeLongitude, String placeAddress, String city) {
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
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
}
