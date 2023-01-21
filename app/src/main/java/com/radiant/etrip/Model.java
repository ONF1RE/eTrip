package com.radiant.etrip;

public class Model {
    Double latitude;
    Double longitude;
    String destination, date, time, carType, seat;

    public Model() {
    }

    public Model(String carType, String date, String destination, Double latitude, Double longitude, String seat, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.carType = carType;
        this.seat = seat;
    }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}

