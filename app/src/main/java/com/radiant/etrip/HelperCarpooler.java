package com.radiant.etrip;

public class HelperCarpooler {
    String startPoint, endPoint, date, time, carType;
    Double distance, carbonSaved;
    int points;

    public HelperCarpooler() {
    }

    public HelperCarpooler(String startPoint, String endPoint, String date, String time, String carType, Double distance, Double carbonSaved, int points) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.date = date;
        this.time = time;
        this.carType = carType;
        this.distance = distance;
        this.carbonSaved =carbonSaved;
        this.points = points;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
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
}
