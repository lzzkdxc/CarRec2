package com.example.carrec2.ui.main_fake.fake;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tech.huqi.smartopencv.SmartOpenCV;
import tech.huqi.smartopencv.core.preview.CameraConfiguration;

import com.example.carrec2.Main2Activity;
import com.example.carrec2.R;
import com.example.carrec2.database.data.QuerySQL;
import com.example.carrec2.database.pojo.Fake;
import com.example.carrec2.rec.ClassCOLOR;
import com.example.carrec2.rec.ClassCRNN;
import com.example.carrec2.rec.ClassLOGO;
import com.example.carrec2.rec.ClassTYPE;
import com.example.carrec2.rec.ClassYOLO;
import com.example.carrec2.rec.MyUtils;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.dnn.Dnn;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class FakeFragment extends Fragment  {

    private FakeViewModel galleryViewModel;
    int is_detect_live=0;
    Button commandIsDetect,commandSingerDetect,commandChangeWeight;
    public FakeFragment() {
        mainActivity = this;
    }
    public static FakeFragment getMainActivity() {
        return mainActivity;
    }
    private static FakeFragment mainActivity;
    private CameraBridgeViewBase mOpenCvCameraView;
    //    String modelConfiguration = "/yolov3_1.cfg";
//    String modelWeights = "/latest_plate.weights";
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =ViewModelProviders.of(this).get(FakeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_car_info, container, false);

        ImageView imageView=root.findViewById(R.id.yolov3cam_out);
        TextView text_type=root.findViewById(R.id.type_out);
        TextView text_crnn=root.findViewById(R.id.crnn_out);
        TextView text_logo=root.findViewById(R.id.logo_out);
        TextView text_color=root.findViewById(R.id.color1_out);
        imageView.setImageBitmap(MyUtils.image_out);
        text_type.setText(MyUtils.type_out);
        text_crnn.setText(MyUtils.crnn_out);
        text_logo.setText(MyUtils.logo_out);
        text_color.setText(MyUtils.color_out);

        List<Fake> fakeOut=MyUtils.carDatabase.getFakeListBySql(QuerySQL.query_fake_by_plate, new String[]{MyUtils.crnn_out});
        TextView text_type2=root.findViewById(R.id.type_database);
        TextView text_crnn2=root.findViewById(R.id.crnn_database);
        TextView text_logo2=root.findViewById(R.id.logo_database);
        TextView text_color2=root.findViewById(R.id.color1_database);
        TextView text_if=root.findViewById(R.id.final_is);
        if(fakeOut!=null){
            String type2=fakeOut.get(0).getCarType();
            String crnn2=fakeOut.get(0).getCarType();
            String logo2=fakeOut.get(0).getCarType();
            String color2=fakeOut.get(0).getCarType();
            text_type2.setText(type2);
            text_crnn2.setText(crnn2);
            text_logo2.setText(logo2);
            text_color2.setText(color2);
            String s="不同！套牌车！";
            int ifif=0;
            if(!MyUtils.type_out.equals(type2)){
                s="车型"+s;
                ifif=1;
            }
            if(!MyUtils.crnn_out.equals(crnn2)){
                s="车牌"+s;
                ifif=1;
            }
            if(!MyUtils.logo_out.equals(logo2)){
                s="车标"+s;
                ifif=1;
            }
            if(!MyUtils.color_out.equals(color2)){
                s="车颜色"+s;
                ifif=1;
            }
            if(ifif==0){
                s="全部信息符合！";
            }
            text_if.setText(s);
            if(ifif==0){
                text_if.setTextColor(Color.GREEN);
            }else{
                text_if.setTextColor(Color.RED);
            }
        }else{
            text_if.setText("查无此车！");
            text_if.setTextColor(Color.RED);
        }
        return root;
    }

}
