package com.example.carrec2.database.db;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.carrec2.database.data.CreateTableSQL;
import com.example.carrec2.database.data.DBData;
import com.example.carrec2.database.data.QuerySQL;
import com.example.carrec2.database.pojo.Fake;
import com.example.carrec2.database.pojo.Flow;
import com.example.carrec2.database.util.CarDBUtil;

import java.util.ArrayList;
import java.util.List;

public class CarDatabase {
    //fake表字段
    public static final String FAKE_KEY_ID = "id";
    public static final String FAKE_KEY_TYPE = "carType";
    public static final String FAKE_KEY_COLOR = "carColor";
    public static final String FAKE_KEY_PLATE = "carPlate";
    public static final String FAKE_KEY_LOGO = "carLogo";

    //flow表字段
    public static final String FLOW_KEY_ID = "id";
    public static final String FLOW_KEY_RECORDTIME = "recordTime";
    public static final String FLOW_KEY_LOCATION = "recordLocation";
    public static final String FLOW_KEY_PLATE = "carPlate";


    //数据库名，表名
    private static final String DB_NAME = "car.db";
    public static final String FLOW_TABLE = "flow";
    public static final String PARKING_TABLE = "parking";
    public static final String TICKET_TABLE = "ticket";
    public static final String FAKE_TABLE = "fake";

    //数据库版本号
    private int version = 1;
    //数据库助手
    private CarDatabase.CarBaseHelper carBaseHelper;
    //数据库
    private SQLiteDatabase db;
    //上下文
    Activity activity;

    public CarDatabase(Activity activity) {
        this.activity = activity;
    }

    //打开数据库
    public void open() {
        if (db == null || !db.isOpen()) {
            carBaseHelper = new CarDatabase.CarBaseHelper();
            db = carBaseHelper.getWritableDatabase();
        }
    }

    //关闭数据库
    public void close() {
        if (db != null || db.isOpen()) {
            db.close();
        }
    }

    //插入一条Fake数据
    public long insertFakeData(Fake fake) {
        ContentValues cv = CarDBUtil.enCodeFakeCotentValues(fake);
        return db.insert(FAKE_TABLE, null, cv);
    }

    //查询所有fake数据
    public List<Fake> getAllFake(){
        return getFakeListBySql(QuerySQL.query_fake_all, null);
    }

    //根据Sql查询fake数据
    public List<Fake> getFakeListBySql(String sql, String args[]) {
        Cursor cursor = db.rawQuery(sql, args);
        List<Fake> list = getFakeListFromCursor(cursor);
        cursor.close();
        return list;
    }

    //根据Sql查询flow数据
    public List<Flow> getFlowListBySql(String sql, String args[]) {
        Cursor cursor = db.rawQuery(sql, args);
        List<Flow> list = getFlowListFromCursor(cursor);
        cursor.close();
        return list;
    }

    //插入一条flow数据
    public long insertFlowData(Flow flow) {
        ContentValues cv = CarDBUtil.enCodeFlowCotentValues(flow);
        return db.insert(FLOW_TABLE, null, cv);
    }

    //查询数据库
    //解析fake所有游标
    public List<Fake> getFakeListFromCursor(Cursor cursor) {
        List<Fake> list = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Fake fake = CarDBUtil.getFakeFromCursor(cursor);
            list.add(fake);
        }
        return list;
    }

    //解析Flow所有游标
    public List<Flow> getFlowListFromCursor(Cursor cursor) {
        List<Flow> list = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            Flow flow = CarDBUtil.getFlowFromCursor(cursor);
            list.add(flow);
        }
        return list;
    }

    //内部类，databaseHleper
    class CarBaseHelper extends SQLiteOpenHelper {

        //创建car数据库
        public CarBaseHelper() {
            super(activity, DB_NAME, null, version);
        }

        //建表插数据
        @Override
        public void onCreate(SQLiteDatabase db) {
            //创建fake表
            db.execSQL(CreateTableSQL.table_fake_sql);

            //为fake表插入数据
            for (String sql : DBData.fake_data_sql)
                db.execSQL(sql);

            //创建flow表
            db.execSQL(CreateTableSQL.table_flow_sql);

            //为flow插入数据
            for (String sql : DBData.flow_data_sql)
                db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            resetDB(db);
        }

        private void resetDB(SQLiteDatabase db) {
            String fake = String.format("drop if exists %s", FAKE_TABLE);
            String flow = String.format("drop if exists %s", FLOW_TABLE);
            db.execSQL(fake);
            db.execSQL(flow);
            onCreate(db);
        }
    }
}
