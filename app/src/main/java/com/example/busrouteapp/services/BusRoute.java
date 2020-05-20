package com.example.busrouteapp.services;

public class BusRoute {

    private String routeId;
    private String routeNumber;
    private String firstStop;
    private String secondStop;


    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public void setFirstStop(String firstStop) {
        this.firstStop = firstStop;
    }

    public void setSecondStop(String secondStop) {
        this.secondStop = secondStop;
    }


    public String getRouteId() {
        return routeId;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public String getFirstStop() {
        return firstStop;
    }

    public String getSecondStop() {
        return secondStop;
    }

}
