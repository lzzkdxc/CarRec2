package com.example.carrec2.ui.main_ticket.ticket;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.carrec2.R;
import com.example.carrec2.rec.MyUtils;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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


        EditText sp_car_color = root.findViewById(R.id.ticket_sp_color);
        sp_car_color.setText(MyUtils.color_out);

        EditText sp_car_type = root.findViewById(R.id.ticket_sp_type);
        sp_car_type.setText(MyUtils.type_out);

        EditText car_crnn= root.findViewById(R.id.ticket_et_result_plate);
        car_crnn.setText(MyUtils.crnn_out);

        Calendar c = Calendar.getInstance();
        EditText year= root.findViewById(R.id.ticket_et_time_year);
        year.setText((String.valueOf(c.get(Calendar.YEAR))));

        EditText month= root.findViewById(R.id.ticket_et_time_month);
        month.setText(String.valueOf(c.get(Calendar.MONTH)));

        EditText day= root.findViewById(R.id.ticket_et_time_day);
        day.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));

        EditText hour= root.findViewById(R.id.ticket_et_time_hour);
        hour.setText(String.valueOf(c.get(Calendar.HOUR)));

        EditText minute= root.findViewById(R.id.ticket_et_time_minute);
        minute.setText(String.valueOf(c.get(Calendar.MINUTE)));

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
