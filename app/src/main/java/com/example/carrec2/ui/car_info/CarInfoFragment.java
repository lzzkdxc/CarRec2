package com.example.carrec2.ui.car_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.carrec2.R;
import com.example.carrec2.rec.MyUtils;
import com.example.carrec2.ui.car_info.CarInfoViewModel;

public class CarInfoFragment extends Fragment {

    private CarInfoViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(CarInfoViewModel.class);
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
        return root;
    }
}
