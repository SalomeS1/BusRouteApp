package com.example.busrouteapp.services;

public class BusStop {

    private String StopId;
    private String Name;
    private String Lon;
    private String Lat;

    public String getBusStopId() {
        return StopId;
    }

    public String getName() {
        return Name;
    }

    public String getLon() {
        return Lon;
    }

    public String getLat() {
        return Lat;
    }


    public void setBusStopId(String busStopId) {
        this.StopId = busStopId;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setLon(String lon) {
        this.Lon = lon;
    }

    public void setLat(String lat) {
        this.Lat = lat;
    }
}
