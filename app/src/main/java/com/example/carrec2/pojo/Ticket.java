package com.example.carrec2.pojo;


import java.util.Date;

//罚单表
public class Ticket {
    private int id;
    private Date recordTime;
    private String recordLocation;
    private String recordMan;
    private int cost;
    private String carType;
    private String carColor;
    private String carPlate;
    private String carLogo;
    private String remark;

    public Ticket(int id, Date recordTime, String recordLocation, String recordMan, int cost, String carType, String carColor, String carPlate, String carLogo, String remark) {
        this.id = id;
        this.recordTime = recordTime;
        this.recordLocation = recordLocation;
        this.recordMan = recordMan;
        this.cost = cost;
        this.carType = carType;
        this.carColor = carColor;
        this.carPlate = carPlate;
        this.carLogo = carLogo;
        this.remark = remark;
    }

    public Ticket() {
    }

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

    public String getRecordMan() {
        return recordMan;
    }

    public void setRecordMan(String recordMan) {
        this.recordMan = recordMan;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarLogo() {
        return carLogo;
    }

    public void setCarLogo(String carLogo) {
        this.carLogo = carLogo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ticket{" +
                "id=" + id +
                ", recordTime=" + recordTime +
                ", recordLocation='" + recordLocation + '\'' +
                ", recordMan='" + recordMan + '\'' +
                ", cost=" + cost +
                ", carType='" + carType + '\'' +
                ", carColor='" + carColor + '\'' +
                ", carPlate='" + carPlate + '\'' +
                ", carLogo='" + carLogo + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
