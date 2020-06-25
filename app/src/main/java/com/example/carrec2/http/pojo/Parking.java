package com.example.carrec2.http.pojo;

import java.util.Date;

//停车场记录表
public class Parking {
    private int id;
    private Date recordTimeIn;
    private Date recordTimeOut;
    private double cost;
    private String carPlate;


    public Parking(int id, Date recordTimeIn, Date recordTimeOut, double cost, String car_plate) {
        this.id = id;
        this.recordTimeIn = recordTimeIn;
        this.recordTimeOut = recordTimeOut;
        this.cost = cost;
        this.carPlate = car_plate;
    }

    public Parking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRecordTimeIn() {
        return recordTimeIn;
    }

    public void setRecordTimeIn(Date recordTimeIn) {
        this.recordTimeIn = recordTimeIn;
    }

    public Date getRecordTimeOut() {
        return recordTimeOut;
    }

    public void setRecordTimeOut(Date recordTimeOut) {
        this.recordTimeOut = recordTimeOut;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    @Override
    public String toString() {
        return "Parking{" +
                "id=" + id +
                ", recordTimeIn=" + recordTimeIn +
                ", recordTimeOut=" + recordTimeOut +
                ", cost=" + cost +
                ", carPlate='" + carPlate + '\'' +
                '}';
    }
}
