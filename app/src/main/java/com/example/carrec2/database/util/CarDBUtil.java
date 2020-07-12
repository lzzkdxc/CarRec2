package com.example.carrec2.database.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.carrec2.database.db.CarDatabase;
import com.example.carrec2.pojo.Fake;
import com.example.carrec2.pojo.Flow;
import com.example.carrec2.pojo.Parking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarDBUtil {
    //封装Fake的数据,便于操作
    public static ContentValues enCodeFakeCotentValues(Fake fake) {
        ContentValues cv = new ContentValues();
        cv.put(CarDatabase.FAKE_KEY_TYPE, fake.getCarType());
        cv.put(CarDatabase.FAKE_KEY_COLOR, fake.getCarColor());
        cv.put(CarDatabase.FAKE_KEY_PLATE, fake.getCarPlate());
        cv.put(CarDatabase.FAKE_KEY_LOGO, fake.getCarLogo());
        return cv;
    }

    //解析Fake的单个游标
    public static Fake getFakeFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(CarDatabase.FAKE_KEY_ID));
        String type = c.getString(c.getColumnIndex(CarDatabase.FAKE_KEY_TYPE));
        String color = c.getString(c.getColumnIndex(CarDatabase.FAKE_KEY_COLOR));
        String plate = c.getString(c.getColumnIndex(CarDatabase.FAKE_KEY_PLATE));
        String logo = c.getString(c.getColumnIndex(CarDatabase.FAKE_KEY_LOGO));

        Fake fake = new Fake(id, type, color, plate, logo);
        return fake;
    }

    //解析Park的单个游标
    public static Parking getParkFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex("id"));
        String recordTimeIn = c.getString(c.getColumnIndex("recordTimeIn"));
        String recordTimeOut = c.getString(c.getColumnIndex("recordTimeOut"));
        String carPlate = c.getString(c.getColumnIndex("carPlate"));

        return new Parking(id, recordTimeIn, recordTimeOut, 0, carPlate);
    }
    //封装Flow的数据,便于操作
    public static ContentValues enCodeFlowCotentValues(Flow flow) {
        ContentValues cv = new ContentValues();

        //将时间戳转换为字符时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String recordTimem = sdf.format(flow.getRecordTime().getTime());

        cv.put(CarDatabase.FLOW_KEY_RECORDTIME, recordTimem);
        cv.put(CarDatabase.FLOW_KEY_LOCATION, flow.getRecordLocation());
        cv.put(CarDatabase.FLOW_KEY_PLATE, flow.getCarPlate());
        return cv;
    }

    //封装Park入场的数据,便于操作
    public static ContentValues enCodeParkInValues(Parking parking) {
        ContentValues cv = new ContentValues();

        //将时间戳转换为字符时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String recordTimem = (sdf.format(new Date()));

        cv.put("recordTimeIn", recordTimem);
        cv.put("carPlate", parking.getCarPlate());
        return cv;
    }


    //封装Flow出场的数据,便于操作
    public static ContentValues enCodeParkOutValues(Parking parking) {
        ContentValues cv = new ContentValues();
        cv.put("carPlate", parking.getCarPlate());
        return cv;
    }
    //解析Flow的单个游标
    public static Flow getFlowFromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(CarDatabase.FAKE_KEY_ID));
        String location = c.getString(c.getColumnIndex(CarDatabase.FLOW_KEY_LOCATION));
        String plate = c.getString(c.getColumnIndex(CarDatabase.FLOW_KEY_PLATE));
        long time = c.getLong(c.getColumnIndex(CarDatabase.FLOW_KEY_RECORDTIME));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String recordTimem = sdf.format(time);
        Date recordDate = null;
        try {
            recordDate = sdf.parse(recordTimem);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Flow flow = new Flow(id, recordDate, location, plate);
        return flow;
    }
}
