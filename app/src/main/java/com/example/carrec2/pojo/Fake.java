package com.example.carrec2.pojo;


//套牌车查询表
public class Fake {

    //主键id
    private int id;
    //车型
    private String carType;
    //车颜色
    private String carColor;
    //车牌
    private String carPlate;
    //车标
    private String carLogo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Fake(int id, String carType, String carColor, String carPlate, String carLogo) {
        this.id = id;
        this.carType = carType;
        this.carColor = carColor;
        this.carPlate = carPlate;
        this.carLogo = carLogo;
    }

    public Fake(String carType, String carColor, String carPlate, String carLogo) {
        this.carType = carType;
        this.carColor = carColor;
        this.carPlate = carPlate;
        this.carLogo = carLogo;
    }

    public Fake() {
    }

    @Override
    public String toString() {
        return "Fake{" +
                "id=" + id +
                ", carType='" + carType + '\'' +
                ", carColor='" + carColor + '\'' +
                ", carPlate='" + carPlate + '\'' +
                ", carLogo='" + carLogo + '\'' +
                '}';
    }
}
