package com.example.carrec2.database.pojo;

import java.util.Date;

//车流量表
public class Flow {
    private int id;
    private Date recordTime;
    private String recordLocation;
    private String carPlate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordLocation() {
        return recordLocation;
    }

    public void setRecordLocation(String recordLocation) {
        this.recordLocation = recordLocation;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public Flow(int id, Date recordTime, String recordLocation, String carPlate) {
        this.id = id;
        this.recordTime = recordTime;
        this.recordLocation = recordLocation;
        this.carPlate = carPlate;
    }

    public Flow(Date recordTime, String recordLocation, String carPlate) {
        this.recordTime = recordTime;
        this.recordLocation = recordLocation;
        this.carPlate = carPlate;
    }

    public Flow() {
    }

    @Override
    public String toString() {
        return "Flow{" +
                "id=" + id +
                ", recordTime=" + recordTime +
                ", recordLocation=" + recordLocation +
                ", carPlate='" + carPlate + '\'' +
                '}';
    }
}
