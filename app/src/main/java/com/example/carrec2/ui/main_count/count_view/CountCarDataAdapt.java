package com.example.carrec2.ui.main_count.count_view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrec2.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class CountCarDataAdapt extends ArrayAdapter<CountCarData> {


    private ArrayList<CountCarData> list;
    private Context context;



    public CountCarDataAdapt(Context context, ArrayList<CountCarData> list) {
        super(context, android.R.layout.simple_list_item_1, list);
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.row, null, false);
        ImageView iv = (ImageView) v.findViewById(R.id.iv1);
        TextView tv_name = (TextView) v.findViewById(R.id.tv_name);
        TextView tv_phone = (TextView) v.findViewById(R.id.chexin);
        TextView time = (TextView) v.findViewById(R.id.time);
        ImageView iv_call = (ImageView) v.findViewById(R.id.iv);
        CountCarData phonedata = list.get(position);
        time.setText(phonedata.getTime());
        tv_name.setText(phonedata.getName());
        final String phone = phonedata.getPhone();
        tv_phone.setText(phone);

        if (phone.length() < 1) {
            iv_call.setVisibility(View.GONE);
        }
        iv.setImageResource(phonedata.getPic_Id());
        iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Calling:"+phone, Toast.LENGTH_SHORT).show();

            }
        });
        return v;
    }

}
