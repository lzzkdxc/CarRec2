package com.example.carrec2.ui.main_count.count_view;

public class CountCarData {
    private String name;

    private String time;
    private String phone;
    private int pic_Id;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CountCarData(String name, String phone, String time, int pic_Id){
        this.time=time;
        this.name=name;
        this.phone=phone;
        this.pic_Id=pic_Id;
    }
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
    public int getPic_Id(){
        return pic_Id;
    }
}
