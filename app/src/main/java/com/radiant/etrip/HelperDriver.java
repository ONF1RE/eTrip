package com.radiant.etrip;

public class HelperDriver {
    String destination, date, time, carType, carPlate, seat;
    Double latitude, longitude, distance, carbonSaved;
    int points;

    public HelperDriver() {
    }

    public HelperDriver(String destination, String date, String time, String carType, String carPlate, String seat, Double latitude, Double longitude, Double distance, Double carbonSaved, int points) {
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.carType = carType;
        this.carPlate = carPlate;
        this.seat = seat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.carbonSaved = carbonSaved;
        this.points = points;
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

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getCarbonSaved() {
        return carbonSaved;
    }

    public void setCarbonSaved(Double carbonSaved) {
        this.carbonSaved = carbonSaved;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
