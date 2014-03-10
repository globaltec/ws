package com.globaltec.fleetcontrol.rest;

public class CheckIn {

    private String title;
    private double lat;
    private double lng;

    public CheckIn() {

    }

    public CheckIn(String title, double lat, double lng) {
        this.title = title;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
