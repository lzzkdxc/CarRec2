package com.example.carrec2.ui.main_count.count_view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.carrec2.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class CountViewFragment extends Fragment {

    private CountViewViewModel slideshowViewModel;
    Button button_singer_cam;
    Button button_live_cam;
    Button button_change;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(CountViewViewModel.class);
        View root = inflater.inflate(R.layout.main, container, false);
        ListView lv=root.findViewById(R.id.mlv);
        final ArrayList<CountCarData> list = new ArrayList<>();
        list.add(new CountCarData("浙AD1982", "白色 起亚 轿车","7:20-12:20    5元", R.drawable.e));
        list.add(new CountCarData("京PQ9w27", "黑色 别克 SUV","6:40-12:33    6元", R.drawable.q));
        list.add(new CountCarData("浙CG5693", "白色 马自达 轿车","9:06-12:34    4元", R.drawable.r));
        list.add(new CountCarData("浙AK1333", "黑色 现代 轿车","7:36-12:35    5元", R.drawable.t));
        list.add(new CountCarData("川BE1662", "红色 铃木 轿车","8:20-12:40    5元", R.drawable.w));
        list.add(new CountCarData("浙C80335", "白色 大众 轿车","9:50-12:41    4元", R.drawable.y));
        CountCarDataAdapt adapter = new CountCarDataAdapt(getContext(), list);
        lv.setAdapter(adapter);
        return root;
    }
}
