package com.example.carrec2.ui.main_ticket.ticket;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carrec2.R;
import com.example.carrec2.http.HttpUtil;
import com.example.carrec2.http.pojo.Ticket;
import com.example.carrec2.rec.ClassCOLOR;
import com.example.carrec2.rec.ClassCRNN;
import com.example.carrec2.rec.ClassLOGO;
import com.example.carrec2.rec.ClassTYPE;
import com.example.carrec2.rec.ClassYOLO;
import com.example.carrec2.rec.MyUtils;
import com.example.carrec2.ui.main_ticket.ticket.TicketViewModel;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tech.huqi.smartopencv.SmartOpenCV;
import tech.huqi.smartopencv.core.preview.CameraConfiguration;

public class TicketFragment extends Fragment {

    private TicketViewModel galleryViewModel;
    int is_detect_live=0;
    Button commandIsDetect,commandSingerDetect,commandChangeWeight;
    public TicketFragment() {
        mainActivity = this;
    }
    public static TicketFragment getMainActivity() {
        return mainActivity;
    }
    private static TicketFragment mainActivity;
    private CameraBridgeViewBase mOpenCvCameraView;
    //    String modelConfiguration = "/yolov3_1.cfg";
//    String modelWeights = "/latest_plate.weights";
    Mat dst= new Mat();
    View root;
    public Bitmap bitmap_allcar;
    int now_load=1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(com.example.carrec2.ui.main_ticket.ticket.TicketViewModel.class);
        root = inflater.inflate(R.layout.fragement_car_ticket, container, false);


        TextView sp_car_color = root.findViewById(R.id.ticket_sp_color);
        sp_car_color.setText(MyUtils.color_out);

        TextView sp_car_type = root.findViewById(R.id.ticket_sp_type);
        sp_car_type.setText(MyUtils.type_out);

        EditText car_crnn= root.findViewById(R.id.ticket_et_result_plate);
        car_crnn.setText(MyUtils.crnn_out);

        Calendar c = Calendar.getInstance();
        EditText year= root.findViewById(R.id.ticket_et_time_year);
        year.setText((String.valueOf(c.get(Calendar.YEAR))));

        EditText month= root.findViewById(R.id.ticket_et_time_month);
        month.setText(String.valueOf(c.get(Calendar.YEAR)));

        EditText day= root.findViewById(R.id.ticket_et_time_day);
        day.setText(String.valueOf(c.get(Calendar.YEAR)));

        EditText hour= root.findViewById(R.id.ticket_et_time_hour);
        hour.setText(String.valueOf(c.get(Calendar.YEAR)));

        EditText minute= root.findViewById(R.id.ticket_et_time_minute);
        minute.setText(String.valueOf(c.get(Calendar.YEAR)));



//
//        FormBody form = new FormBody.Builder()
//                .add("recordTime", new Date().toString())
//                .add("recordLocation", "茶山")
//                .add("recordMan", "kgg")
//                .add("cost", "100")
//                .add("carType", MyUtils.type_out)
//                .add("carColor", MyUtils.color_out)
//                .add("carPlate", MyUtils.crnn_out)
//                .add("carLogo", MyUtils.logo_out)
//                .add("remark", "").build();
//        Request request = new Request.Builder().url("http://123.57.71.20:5899/ticket/addOneTicket").post(form).build();
//        OkHttpClient client = new OkHttpClient();
//        Call call = client.newCall(request);
//        try {
//            Response response = call.execute();
//            System.out.println(response.body().string());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String url = "http://123.57.71.20:5898/test";
//        System.out.println("++++++++++++++++"+ HttpUtil.getOkHttpBlock("http://123.57.71.20:5899/test" ));
//          HttpUtil.getOkHttpAsyncCall(url, MyUtils.activity, new HttpUtil.SimpleAsyncCall() {
//                @Override
//                public void onFailure(String e) {
//                    System.out.println("++++++++++++++++" + "no" + "++++++++++++++++++++");
//
//                }
//
//                @Override
//                public void onResponse(String body) {
//                    System.out.println("++++++++++++++++" + body + "++++++++++++++++++++");
//                }
//            });
        return root;
    }
//

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
    }



}
