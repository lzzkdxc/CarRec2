package com.example.carrec2.database.db;//package com.kgg.democar.db;
//
//import android.app.Activity;
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.text.TextUtils;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CityDatabase {
//    //表字段
//    public static final String KEY_ID = "mid"; //main id for url
//    public static final String KEY_PID = "pid"; // parent id
//    public static final String KEY_NAME = "name";
//    public static final String KEY_WEATHER_ID = "weather_id";
//    public static final String KEY_EN_NAME = "en_name";
//    public static final String KEY_INI_NAME = "ini_name";
//    public static final String KEY_LEVEL = "level";
//    public static final String KEY_LOOK_UP = "key_look_up";
//    //数据库名，表名
//    private static final String DB_NAME = "citydb.db";
//    public static final String CITY_TABLE = "city";
//    //数据库版本号
//    private int version = 1;
//    //数据库助手
//    private CityBaseHelper cityBaseHelper;
//    //数据库
//    private SQLiteDatabase db;
//    //上下文
//    Activity activity;
//
//    public CityDatabase(Activity activity) {
//        this.activity = activity;
////        xhip.com123
//    }
//
//    //打开数据库
//    public void open() {
//        if (db == null || !db.isOpen()) {
//            cityBaseHelper = new CityBaseHelper();
//            db = cityBaseHelper.getWritableDatabase();
//        }
//    }
//
//    //关闭数据库
//    public void close() {
//        if (db != null || db.isOpen()) {
//            db.close();
//        }
//    }
//
//    //插入单个城市数据
//    public long insertData(City city) {
//        ContentValues cv = DBUtil.enCodeCotentValues(city);
//        return db.insert(CITY_TABLE, KEY_WEATHER_ID, cv);
//    }
//
//    //插入城市列表
//    public int insertList(List<City> cityList) {
//        int count = 0;
//        for (int i = 0; i < cityList.size(); i++)
//            if (insertData(cityList.get(i)) > 0)
//                count++;
//        return count;
//    }
//
//    //清除数据库
//    public void clearDatabase() {
//        if (db != null && db.isOpen())
//            cityBaseHelper.resetDB(db);
//    }
//
//    //查询数据库
//    //解析所有游标
//    public List<City> getCityListFromCursor(Cursor cursor) {
//        List<City> list = new ArrayList<>();
//        for (int i = 0; i < cursor.getCount(); i++) {
//            cursor.moveToPosition(i);
//            City city = DBUtil.getCityFromCursor(cursor);
//            list.add(city);
//        }
//        return list;
//    }
//
//    //根据Sql查询
//    private List<City> getCityListBySql(String sql, String args[]) {
//        Cursor cursor = db.rawQuery(sql, args);
//        List<City> list = getCityListFromCursor(cursor);
//        cursor.close();
//        return list;
//    }
//
//    //查询所有省份
//    private List<City> queryAllProvinces() {
//        String sql = String.format("select * from %s where %s=0", CITY_TABLE, KEY_LEVEL);
//        return getCityListBySql(sql, null);
//    }
//
//    //根据ParentId查询
//    private List<City> queryCityListByParent(int parentId, int level) {
//        String sql = String.format("select * from %s where %s=%d and %s=%d", CITY_TABLE, KEY_PID, parentId, KEY_LEVEL, level);
//        return getCityListBySql(sql, null);
//    }
//
//    //模糊查询
//    private List<City> fuzzyQueryCityList(String match) {
//        if (TextUtils.isEmpty(match)){
//            return queryAllProvinces();
//        }
//        String sql = String.format("select * from %s where %s like ?", CITY_TABLE, KEY_LOOK_UP);
//        String[] args = new String[]{"%" + match + "%"};
//        return getCityListBySql(sql, args);
//    }
//
//    public City queryCityById(int id, int level) {
//        String sql = String.format("select * from %s where %s=%d and %s=%d", CITY_TABLE, KEY_LEVEL, level, KEY_ID, id);
//        List<City> list = getCityListBySql(sql, null);
//        if (list != null && list.size() > 0) {
//            return list.get(0);
//        }
//        return null;
//    }
//
//    public interface OnQueryFinished {
//        public void onFinished(List<City> data);
//    }
//
//    public interface LoaderWork {
//        public List<City> queryWork();
//    }
//
//    private void asyncLoder(final OnQueryFinished listener, final LoaderWork work) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final List<City> list = work.queryWork();
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        listener.onFinished(list);
//                    }
//                });
//            }
//        }).start();
//    }
//
//    //异步查询数据
//    //查询所有省份
//    public void queryAllProvincesAsync(final OnQueryFinished listener) {
//        asyncLoder(listener, new LoaderWork() {
//            @Override
//            public List<City> queryWork() {
//                return queryAllProvinces();
//            }
//        });
//    }
//
//    //根据ParentId查询所有城市
//    public void queryCityListByParentIdAsync(final int parentId, final int level, OnQueryFinished listener) {
//        asyncLoder(listener, new LoaderWork() {
//            @Override
//            public List<City> queryWork() {
//                return queryCityListByParent(parentId, level);
//            }
//        });
//    }
//
//    //模糊查询
//    public void fuzzyQueryCityListAsync(final String match, final OnQueryFinished listener) {
//        asyncLoder(listener, new LoaderWork() {
//            @Override
//            public List<City> queryWork() {
//                return fuzzyQueryCityList(match);
//            }
//        });
//    }
//
//
//    //内部sqlhelper类
//    class CityBaseHelper extends SQLiteOpenHelper {
//        public CityBaseHelper() {
//            super(activity, DB_NAME, null, version);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            String sql = String.format("create table if not exists %s" +
//                            "(_id INTEGER PRIMARY KEY AUTOINCREMENT,%s int,%s int,%s text,%s text, %s text,%s int,%s text,%s text)",
//                    CITY_TABLE, KEY_ID, KEY_PID, KEY_NAME, KEY_EN_NAME, KEY_INI_NAME, KEY_LEVEL, KEY_LOOK_UP, KEY_WEATHER_ID);
//            db.execSQL(sql);
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            resetDB(db);
//        }
//
//        private void resetDB(SQLiteDatabase db) {
//            String sql = String.format("drop table if exists %s", CITY_TABLE);
//            db.execSQL(sql);
//            onCreate(db);
//        }
//    }
//
//}
