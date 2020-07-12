package com.example.carrec2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.carrec2.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button button=root.findViewById(R.id.crnn_inin_bt);
        final EditText crnn_inin=root.findViewById(R.id.crnn_inin);
        final EditText logo_inin=root.findViewById(R.id.logo_inin);
        final EditText type_inin=root.findViewById(R.id.type_inin);
        final EditText color_inin=root.findViewById(R.id.color_inin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MyUtils.crnn_out_back=crnn_inin.getText().toString();
//                MyUtils.logo_out_back=logo_inin.getText().toString();
//                MyUtils.type_out_back=type_inin.getText().toString();
//                MyUtils.color_out_back=color_inin.getText().toString();
//                String[] s=new String[]{MyUtils.type_out_back,MyUtils.color_out_back,MyUtils.crnn_out_back,MyUtils.logo_out_back};
//                MyUtils.carDatabase.db.execSQL("insert into fake(carType, carColor, carPlate, carLogo) VALUES (?,?,?,?);",s);

            }
        });
        return root;
    }
}
