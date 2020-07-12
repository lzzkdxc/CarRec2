package com.example.carrec2.database.data;

public class QuerySQL {
    //查询fake的语句
    //查询所有
    public static String query_fake_all = "select * from fake;";
    //根据车牌查找一个,?为可添加的参数
    public static String query_fake_by_plate = "select * from fake where carPlate = ?";

    //查询flow的语句
    public static String query_flow_all = "select * from flow;";


    //插入入场的语句
    public static String query_park_in = "insert into park(recordTimeIn, carPlate) " +
            "VALUES(?,?);";

    //出场的语句
    public static String query_park_out = "update park set recordTimeOut = ? where carPlate = ?;";
}
