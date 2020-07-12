package com.example.carrec2;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;

import com.example.carrec2.database.data.CreateTableSQL;
import com.example.carrec2.database.data.DBData;
import com.example.carrec2.database.db.CarDatabase;
import com.example.carrec2.rec.ClassCOLOR;
import com.example.carrec2.rec.ClassCRNN;
import com.example.carrec2.rec.ClassLOGO;
import com.example.carrec2.rec.ClassTYPE;
import com.example.carrec2.rec.ClassYOLO;
import com.example.carrec2.rec.MyUtils;
import com.google.android.material.navigation.NavigationView;

import org.opencv.android.OpenCVLoader;
import org.opencv.dnn.Dnn;
import org.pytorch.Module;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Main2Activity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;

    static {
        OpenCVLoader.initDebug();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_fake, R.id.nav_parking)
                .setDrawerLayout(drawer)
                .build();
        MyUtils.activity=this;
        MyUtils.context=this;
        initLoadAllWeight();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        MyUtils.carDatabase = new CarDatabase(this);
        MyUtils.carDatabase.open();

        MyUtils.carDatabase.db.execSQL("drop table if exists park");
        MyUtils.carDatabase.db.execSQL(CreateTableSQL.table_park_sql);
        System.out.println(CreateTableSQL.table_park_sql);
        //为park插入数据
        for (String sql : DBData.park_data_sql)
            MyUtils.carDatabase.db.execSQL(sql);
    }

    int now_load=1;
    void initLoadAllWeight(){
        try {
            String ss = MyUtils.assetFilePath("demo_latest_plate_JIT_CPU_0607.pt");
            ClassCRNN.module = Module.load(ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String ss = MyUtils.assetFilePath("Car_COLOR_JIT_CPU0617.pt");
            ClassCOLOR.module = Module.load(ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String ss = MyUtils.assetFilePath("Car_TYPE_JIT_CPU0510.pt");
            ClassTYPE.module = Module.load(ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String ss = MyUtils.assetFilePath( "DenseNet_car_logo_JIT0221.pt");
            ClassLOGO.module = Module.load(ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
//            s = MyUtils.assetFilePath( "YOLO_plate_2class_CPU.weights");
//            ss = MyUtils.assetFilePath( "yolov3_2.cfg");
            s_tiny = MyUtils.assetFilePath( "yolo-tiny_plate_2class_0612.weights");
            ss_tiny = MyUtils.assetFilePath( "yolov3_2-tiny.cfg");
//            s_tiny_plate = MyUtils.assetFilePath( "yolo-tiny_plate_1class_0625.weights");
//            ss_tiny_plate = MyUtils.assetFilePath( "yolov3-tiny_1.cfg");
            ClassYOLO.net = Dnn.readNetFromDarknet(ss_tiny, s_tiny);
//            ClassYOLO_Plate.net = Dnn.readNetFromDarknet(ss_tiny_plate, s_tiny_plate);
            now_load=1;
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            ClassCOLOR.bitmap_plate = BitmapFactory.decodeStream(getAssets().open("cam3.png"));
//            ClassCOLOR.go();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public static String s_tiny,ss_tiny,s,ss,ss_tiny_plate,s_tiny_plate;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            // 用户未选择任何文件，直接返回
            return;
        }
        Uri uri = data.getData(); // 获取用户选择文件的URI
        // 通过ContentProvider查询文件路径
        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // 未查询到，说明为普通文件，可直接通过URI获取文件路径
            String path = uri.getPath();
            return;
        }
        if (cursor.moveToFirst()) {
            // 多媒体文件，从数据库中获取文件的真实路径
            String path = cursor.getString(cursor.getColumnIndex("_data"));
        }
        cursor.close();
    }
    public void pickFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        this.startActivityForResult(intent,  1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyUtils.carDatabase.close();
    }
}
