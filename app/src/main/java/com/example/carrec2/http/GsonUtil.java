package com.example.carrec2.http;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GsonUtil {
//
//    public static List<City> getCityListFromJson(String str, int parentId, int level){
//        ArrayList<City> cities = new ArrayList<>();
//        try {
//            JSONArray jsonArray = new JSONArray(str);
//            for (int i = 0; i < jsonArray.length(); i++){
//                //映射
//                City city = new Gson().fromJson(jsonArray.get(i).toString(), City.class);
//                //设置属性
//                city.setParentId(parentId);
//                city.setEnName(PinYinUtil.toPinyin(city.getName()));
//                city.setInitialName(PinYinUtil.toPinyinFirstLetter(city.getName()));
//                city.setLevel(level);
//                cities.add(city);
//            }
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        return cities;
//    }
}
