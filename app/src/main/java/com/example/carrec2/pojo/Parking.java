package com.example.carrec2.pojo;

//停车场记录表
public class Parking {
    private int id;
    private String recordTimeIn;
    private String recordTimeOut;
    private int cost;
    private String carPlate;


    public Parking(int id, String recordTimeIn, String recordTimeOut, int cost, String car_plate) {
        this.id = id;
        this.recordTimeIn = recordTimeIn;
        this.recordTimeOut = recordTimeOut;
        this.cost = cost;
        this.carPlate = car_plate;
    }

    public Parking(String car_plate) {
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

    public String getRecordTimeIn() {
        return recordTimeIn;
    }

    public void setRecordTimeIn(String recordTimeIn) {
        this.recordTimeIn = recordTimeIn;
    }

    public String getRecordTimeOut() {
        return recordTimeOut;
    }

    public void setRecordTimeOut(String recordTimeOut) {
        this.recordTimeOut = recordTimeOut;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
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
