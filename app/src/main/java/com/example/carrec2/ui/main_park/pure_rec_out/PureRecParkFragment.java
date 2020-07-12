package com.example.carrec2.ui.main_park.pure_rec_out;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.carrec2.R;
import com.example.carrec2.pojo.Parking;
import com.example.carrec2.rec.ClassCOLOR;
import com.example.carrec2.rec.ClassCRNN;
import com.example.carrec2.rec.ClassLOGO;
import com.example.carrec2.rec.ClassTYPE;
import com.example.carrec2.rec.ClassYOLO;
import com.example.carrec2.rec.ClassYOLO_Plate;
import com.example.carrec2.rec.MyUtils;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import tech.huqi.smartopencv.SmartOpenCV;
import tech.huqi.smartopencv.core.preview.CameraConfiguration;


public class PureRecParkFragment extends Fragment implements View.OnTouchListener, CameraBridgeViewBase.CvCameraViewListener2 {

    private PureRecParkViewModel galleryViewModel;
    private Button commandIsDetect,commandLoad;
    private CameraBridgeViewBase mOpenCvCameraView;
    //    String modelConfiguration = "/yolov3_1.cfg";
//    String modelWeights = "/latest_plate.weights";
    Mat dst= new Mat();
    Mat dsst= new Mat();
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(PureRecParkViewModel.class);
        root = inflater.inflate(R.layout.fragment_pure_rec, container, false);
        initSmartOpenCV();
        ClassYOLO.init();

        ClassCRNN.textView = root.findViewById(R.id.car_auto_recog_plate);
        ClassLOGO.textView = root.findViewById(R.id.car_auto_recog_logo);
        ClassTYPE.textView = root.findViewById(R.id.car_auto_recog_type);
        ClassCOLOR.textView = root.findViewById(R.id.car_auto_recog_color);

        commandIsDetect=root.findViewById(R.id.button_pure_rec);
        commandIsDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassYOLO.Go(dst);
                ClassYOLO_Plate.Go(dst);
            }
        });

        commandLoad=root.findViewById(R.id.button_load_pic);
        commandLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 2);
            }
        });
        return root;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(MyUtils.context.getContentResolver(), uri);
                    Utils.bitmapToMat(bitmap,dsst);
//                    Imgproc.cvtColor(dsst, dst, Imgproc.COLOR_RGBA2BGR);  //模拟器
                    Imgproc.cvtColor(dsst, dst, Imgproc.COLOR_RGBA2RGB); //手机
                    ClassYOLO.Go(dst);
                    ClassYOLO_Plate.Go(dst);
                    Parking parking=new Parking(MyUtils.crnn_out);
                    MyUtils.carDatabase.altParkData(parking);
                    Toast toast= Toast.makeText(MyUtils.context,MyUtils.crnn_out+"  出场", Toast.LENGTH_SHORT);
                    MyUtils.showMyToast(toast,3000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void initSmartOpenCV(){
        mOpenCvCameraView = root.findViewById(R.id.yolov3cam);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
//        mOpenCvCameraView.setCvCameraViewListener(getActivity());
        mOpenCvCameraView.enableView();
//        mOpenCvCameraView.setOnTouchListener(getContext());
        mOpenCvCameraView.setMaxFrameSize(1300, 1300);
        SmartOpenCV.getInstance().init(mOpenCvCameraView, new CameraConfiguration.Builder()
                .debug(true)
                .cameraIndex(0)      // 设置摄像头索引,主要用于多摄像头设备，优先级低于frontCamera
                .keepScreenOn(true) // 是否保持屏幕常亮
//                .frontCamera(true)   // 是否使用前置摄像头  //经测试，这个frontCamera是有问题的，不能用
                .openCvDefaultDrawStrategy(false)      // 是否使用OpenCV默认的预览图像绘制策略
                .openCvDefaultPreviewCalculator(false) // 是否使用OpenCV默认的预览帧大小计算策略
                .landscape(false)     // 是否横屏显示--------------------------------------------------------------------------
                .enableFpsMeter(false) // 开启预览帧率的显示
                .usbCamera(false)     // 是否使用USB摄像头，当设备接入的是USB摄像头时将其设置为true
                .maxFrameSize(1300, 1300)     // 设置预览帧的最大大小
                .cvCameraViewListener(this) // 设置OpenCV回调监听器
//                .previewSizeCalculator(new IPreviewSizeCalculator() { // 自定义预览帧大小计算策略
//                    @Override
//                    public Size calculateCameraFrameSize(List<Size> supportedSizes, int surfaceWidth, int surfaceHeight) {
//                        // 若需要根据自己的具体业务场景改写览帧大小，覆写该方法逻辑
//                        return new Size(1080,1920);
//                    }
//                })
//                .drawStrategy(new IDrawStrategy() { // 自定义绘制策略
//                    @Override
//                    public void drawBitmap(Canvas canvas, Bitmap frameBitmap, int surfaceWidth, int surfaceHeight) {
//                        // 若需根据自己的具体业务场景绘制预览帧图像，覆写该方法逻辑
//                    }
//                })
                .build());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }

    //    Bitmap bitmap_small;
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Imgproc.cvtColor(inputFrame.rgba(), dst, Imgproc.COLOR_RGBA2RGB); //手机
//        Imgproc.cvtColor(inputFrame.rgba(), dst, Imgproc.COLOR_RGBA2BGR);  //模拟器


        return dst;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }
}
