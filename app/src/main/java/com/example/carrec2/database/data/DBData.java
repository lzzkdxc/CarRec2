package com.example.carrec2.database.data;

public class DBData {
    public static String [] fake_data_sql = new String[] {
            "insert into fake(carType, carColor, carPlate, carLogo) VALUES ('轿车','白色','津GG9046','本田');",
            "insert into fake(carType, carColor, carPlate, carLogo) VALUES ('卡车','红色','赣E73515','丰田');",
            "insert into fake(carType, carColor, carPlate, carLogo) VALUES ('轿车','黑色','浙C83761','大众');",
            "insert into fake(carType, carColor, carPlate, carLogo) VALUES ('轿车','银灰色','浙C09XL6','大众');",
            "insert into fake(carType, carColor, carPlate, carLogo) VALUES ('轿车','白色','浙C7KF95','大众');"};

    public static String [] flow_data_sql = new String[] {
            "insert into flow(recordTime, recordLocation, carPlate) VALUES('2018-10-19 21:16:48','温瑞大道','苏E6D901');",
            "insert into flow(recordTime, recordLocation, carPlate) VALUES('2019-04-22 21:15:17','茶山商务中心','新B1US9G');",
            "insert into flow(recordTime, recordLocation, carPlate) VALUES('2018-08-09 04:26:03','金凤路','黑J4BV38');"};

    public static String [] park_data_sql = new String[] {
            "insert into park(recordTimeIn, recordTimeOut, carPlate) VALUES('2020-06-25 05:16:48','2020-06-25 05:16:48','苏E6D901');",
            "insert into park(recordTimeIn, recordTimeOut, carPlate) VALUES('2020-06-25 05:16:48','2020-06-25 06:16:48','新B1US9G');",
            "insert into park(recordTimeIn, recordTimeOut, carPlate) VALUES('2020-06-25 05:16:48','2020-06-25 07:16:48','黑J4BV38');",
            "insert into park(recordTimeIn, recordTimeOut, carPlate) VALUES('2020-06-25 05:16:48','2020-06-25 08:16:48','津GG9046');",
            "insert into park(recordTimeIn, recordTimeOut, carPlate) VALUES('2020-06-25 05:16:48','2020-06-25 08:16:48','赣E73515');",
            "insert into park(recordTimeIn, recordTimeOut, carPlate) VALUES('2020-06-25 05:16:48','2020-06-25 09:16:48','宁FU1150');"};
}
