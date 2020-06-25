package com.example.carrec2.http;

import android.app.Activity;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public static void getOkHttpAsyncCall(String url, final Activity activity, final SimpleAsyncCall simpleAsyncCall) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simpleAsyncCall.onFailure(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    final String body = response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            simpleAsyncCall.onResponse(body);
                        }
                    });
                } catch (IOException e) {
                    onFailure(call, e);
                }

            }
        });

    }

    public interface SimpleAsyncCall{
        public void onFailure(String e);
        public void onResponse(String body);
    }

    public static  String getOkHttpBlock(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get().build();
        Call call = client.newCall(request);
        try {
            Response res = call.execute();
            return res.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getOkHttpAsync(final Activity activity, String url, final SimpleAsyncCall l){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        l.onFailure(e.toString());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res;
                try {
                    res = response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            l.onResponse(res);
                        }
                    });
                }catch (final IOException e){
                    l.onFailure(e.toString());
                }
            }
        });
    }
}
