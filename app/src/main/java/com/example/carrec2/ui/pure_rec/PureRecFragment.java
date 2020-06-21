package com.example.carrec2.ui.pure_rec;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carrec2.R;
import com.example.carrec2.rec.ClassCOLOR;
import com.example.carrec2.rec.ClassCRNN;
import com.example.carrec2.rec.ClassLOGO;
import com.example.carrec2.rec.ClassTYPE;
import com.example.carrec2.rec.ClassYOLO;
import com.example.carrec2.rec.MyUtils;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.dnn.Dnn;
import org.opencv.imgproc.Imgproc;
import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import tech.huqi.smartopencv.SmartOpenCV;
import tech.huqi.smartopencv.core.preview.CameraConfiguration;

public class PureRecFragment extends Fragment implements View.OnTouchListener, CameraBridgeViewBase.CvCameraViewListener2 {

    private PureRecViewModel galleryViewModel;
    int is_detect_live=0;
    Button commandIsDetect,commandSingerDetect;
    private CameraBridgeViewBase mOpenCvCameraView;
    //    String modelConfiguration = "/yolov3_1.cfg";
//    String modelWeights = "/latest_plate.weights";
    Mat dst= new Mat();
    View root;
    public static int top_is_hander=1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(PureRecViewModel.class);
        root = inflater.inflate(R.layout.fragment_pure_rec, container, false);
        initSmartOpenCV();
        ClassYOLO.init();

        ClassCRNN.textView = root.findViewById(R.id.car_auto_recog_plate);
        ClassLOGO.textView = root.findViewById(R.id.car_auto_recog_logo);
        ClassTYPE.textView = root.findViewById(R.id.car_auto_recog_type);
        ClassCOLOR.textView = root.findViewById(R.id.car_auto_recog_color);
        if(top_is_hander==1){
            ClassCRNN.textView.setVisibility(View.GONE);
            ClassLOGO.textView.setVisibility(View.GONE);
            ClassTYPE.textView.setVisibility(View.GONE);
            ClassCOLOR.textView.setVisibility(View.GONE);
        }

        commandIsDetect=root.findViewById(R.id.button_pure_rec);
        commandIsDetect.setBackgroundColor(Color.GREEN);
        commandIsDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(top_is_hander==0){//自动
                    if(is_detect_live==0){
                        is_detect_live=1;
                        commandIsDetect.setBackgroundColor(Color.RED);
                        commandIsDetect.setText("停止");
                    }else{
                        is_detect_live=0;
                        commandIsDetect.setBackgroundColor(Color.GREEN);
                        commandIsDetect.setText("实时识别");
                    }
                }else{//手动
                    ClassYOLO.Go(dst);
                    NavController navController = Navigation.findNavController(getView());
                    navController.navigate(R.id.nav_car_info);

                }
            }
        });
        return root;
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
//        Imgproc.cvtColor(inputFrame.rgba(), dst, Imgproc.COLOR_RGBA2RGB); //手机
        Imgproc.cvtColor(inputFrame.rgba(), dst, Imgproc.COLOR_RGBA2BGR);  //模拟器

        int w = dst.width(), h = dst.height();
        MyUtils.image_rec = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst, MyUtils.image_rec);

        if(is_detect_live==1){
            return ClassYOLO.Go(dst);
        }else{
            return dst;
        }
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
