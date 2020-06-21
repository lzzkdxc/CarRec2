package com.example.carrec2;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.carrec2.rec.ClassCOLOR;
import com.example.carrec2.rec.ClassCRNN;
import com.example.carrec2.rec.ClassLOGO;
import com.example.carrec2.rec.ClassTYPE;
import com.example.carrec2.rec.ClassYOLO;
import com.example.carrec2.rec.MyUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import org.opencv.android.OpenCVLoader;
import org.opencv.dnn.Dnn;
import org.pytorch.Module;

import java.io.IOException;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Main2Activity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;

    static {
        OpenCVLoader.initDebug();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
            s = MyUtils.assetFilePath( "YOLO_plate_2class_CPU.weights");
            ss = MyUtils.assetFilePath( "yolov3_2.cfg");
            s_tiny = MyUtils.assetFilePath( "yolo-tiny_plate_2class_0612.weights");
            ss_tiny = MyUtils.assetFilePath( "yolov3_2-tiny.cfg");
            ClassYOLO.net = Dnn.readNetFromDarknet(ss_tiny, s_tiny);
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
    public static String s_tiny,ss_tiny,s,ss;
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
}
