package com.example.carrec2.ui.main_park.parking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.carrec2.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ParkingFragment extends Fragment {

    private ParkingViewModel slideshowViewModel;
    Button button_out;
    Button button_in;
    Button button_change;
    Button button_list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(ParkingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_parking, container, false);
        button_out =root.findViewById(R.id.stopping_bt_out);
        button_in =root.findViewById(R.id.stopping_bt_in);
        button_list=root.findViewById(R.id.stopping_bt_history);
        button_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.nav_pure_rec_in);
            }
        });
        button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.nav_pure_rec_out);
            }
        });
        button_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(getView());
                navController.navigate(R.id.nav_parking_history);
            }
        });
//
//        View root = inflater.inflate(R.layout.main, container, false);
//        ListView lv=root.findViewById(R.id.mlv);
//        final ArrayList<PhoneData> list = new ArrayList<>();
//        list.add(new PhoneData("浙AD1982", "白色 起亚 轿车","7:20-12:20    5元", R.drawable.e));
//        list.add(new PhoneData("京PQ9w27", "黑色 别克 SUV","6:40-12:33    6元", R.drawable.q));
//        list.add(new PhoneData("浙CG5693", "白色 马自达 轿车","9:06-12:34    4元", R.drawable.r));
//        list.add(new PhoneData("浙AK1333", "黑色 现代 轿车","7:36-12:35    5元", R.drawable.t));
//        list.add(new PhoneData("川BE1662", "红色 铃木 轿车","8:20-12:40    5元", R.drawable.w));
//        list.add(new PhoneData("浙C80335", "白色 大众 轿车","9:50-12:41    4元", R.drawable.y));
//        PhoneDataAdapt adapter = new PhoneDataAdapt(getContext(), list);
//        lv.setAdapter(adapter);
        return root;
    }
}
