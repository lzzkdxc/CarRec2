package com.example.carrec2.database.data;

public class DBData {
    public static String [] fake_data_sql = new String[] {
            "insert into fake(carType, carColor, carPlate, carLogo) VALUES ('轿车','白色','津GG9046','本田');",
            "insert into fake(carType, carColor, carPlate, carLogo) VALUES ('卡车','红色','赣E73515','丰田');",
            "insert into fake(carType, carColor, carPlate, carLogo) VALUES ('SUV','蓝色','宁FU1150','大众');"};

    public static String [] flow_data_sql = new String[] {
            "insert into flow(recordTime, recordLocation, carPlate) VALUES('2018-10-19 21:16:48','温瑞大道','苏E6D901');",
            "insert into flow(recordTime, recordLocation, carPlate) VALUES('2019-04-22 21:15:17','茶山商务中心','新B1US9G');",
            "insert into flow(recordTime, recordLocation, carPlate) VALUES('2018-08-09 04:26:03','金凤路','黑J4BV38');"};
}
