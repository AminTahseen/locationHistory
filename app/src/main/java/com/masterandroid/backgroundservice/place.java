package com.masterandroid.backgroundservice;

public class place {
    private String key;
    private String place_name;
    private double place_lat;
    private double place_lon;

    public place(String key, String place_name, double place_lat, double place_lon) {
        this.key = key;
        this.place_name = place_name;
        this.place_lat = place_lat;
        this.place_lon = place_lon;
    }

    public place() { }

    public place(String place_name, double place_lat, double place_lon) {
        this.place_name = place_name;
        this.place_lat = place_lat;
        this.place_lon = place_lon;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public double getPlace_lat() {
        return place_lat;
    }

    public void setPlace_lat(double place_lat) {
        this.place_lat = place_lat;
    }

    public double getPlace_lon() {
        return place_lon;
    }

    public void setPlace_lon(double place_lon) {
        this.place_lon = place_lon;
    }

    @Override
    public String toString() {
        return "place{" +
                "key='" + key + '\'' +
                ", place_name='" + place_name + '\'' +
                ", place_lat=" + place_lat +
                ", place_lon=" + place_lon +
                '}'+"\n";
    }
}
