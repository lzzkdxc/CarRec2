package com.example.carrec2.rec;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.FloatBuffer;
import java.util.Arrays;

public class MyUtils {
    static String  alphabet = "-京津冀晋蒙辽吉黑沪苏浙皖闽赣鲁豫鄂湘粤桂琼渝川贵云藏陕甘青宁新警学军空海北沈兰济南广成ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static String[] classes = new String[]{"DS", "JAC", "Jeep", "MG", "MINI", "cnhtc", "三菱", "上海汇众", "东南", "东风", "中兴汽车", "中华", "丰田", "丰田mark", "丰田皇冠",
            "五十铃", "五菱", "众泰", "依维柯", "全球鹰", "凯迪拉克", "别克", "力帆", "北京福田", "北汽", "北汽制造", "北汽幻速", "华泰", "双环", "吉利", "吉利帝豪",
            "启辰", "哈弗", "哈飞", "夏利", "大众", "奇瑞", "奔腾", "奔驰", "奥迪", "威旺", "宝沃", "宝马", "宝骏", "川汽野马", "广汽", "开瑞Karry", "思铭",
            "斯巴鲁", "斯柯达", "日产", "昌河", "本田", "标致", "欧宝", "比亚迪", "江淮", "江铃", "江铃驭胜", "沃尔沃", "海马", "海马郑州", "玛莎拉蒂", "现代",
            "福特", "福田", "福迪", "红旗", "纳智捷", "羊城汽车", "英伦汽车", "英菲尼迪", "荣威", "菲亚特", "讴歌", "起亚", "路虎", "金旅客车", "金杯", "金牛",
            "金龙客车", "铃木", "长城", "长安商用", "长安新", "长安旧", "陆风", "雪佛兰", "雪铁龙", "雷克萨斯", "雷诺", "马自达", "黄海"};
    static String[] TYPEclasses = new String[]{"巴士", "小型巴士", "轿车", "SUV", "大货车", "小货车"};
    static String[] COLORclasses = new String[]{"黑色", "白色", "银灰色", "蓝色", "红色", "黄色"};
    public static Activity activity;
    public static Context context;
    public static ImageView imageView;
    public static Bitmap image_out;
    public static Bitmap image_rec;
    public static String crnn_out,type_out,logo_out,color_out;
    static String  quChong(String input)
    {
        String out="";
        for(int i=0;i<input.length();i++){
            if(i==0||(input.charAt(i)!=input.charAt(i-1)&&input.charAt(i)!='-'))
            {
                out=out+(input.charAt(i));
            }
        }
        return out;
    }
    static void showTextView(final TextView tv, final String s) {
        activity.runOnUiThread(new Runnable() {
            @Override public void run() {
                tv.setText(s);
            }
        });
    }
    static void showBitmap(final ImageView tv, final Bitmap bitmap) {
//        activity.runOnUiThread(new Runnable() {
//            @Override public void run() {
//                tv.setImageBitmap(bitmap);
//            }
//        });
    }
    public static String assetFilePath(String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }
    public static Mat rotateRight(Mat src) {
        Mat tmp = new Mat();
        // 此函数是转置、（即将图像逆时针旋转90度，然后再关于x轴对称）
        Core.transpose(src, tmp);
        Mat result = new Mat();
        // flipCode = 0 绕x轴旋转180， 也就是关于x轴对称
        // flipCode = 1 绕y轴旋转180， 也就是关于y轴对称
        // flipCode = -1 此函数关于原点对称
        Core.flip(tmp, result, 1);
        return result;
    }
    static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
        }
        return newBM;
    }
    static int[] numMax(float[] a,int length) {
        float value=-Float.MAX_VALUE;
        int[] ixs = new int[50];
        Arrays.fill(ixs, -1);
        int j=0,now=0;
        for (int i = 0; i < a.length; i++) {
            if(j<length){
                if(a[i]>value){
                    value=a[i];
                    ixs[now]=j;
                }
                j++;
            }
            else {
                value=a[i];
                j = 0;
                now++;
                ixs[now]=j;
                i--;
            }
        }
        return ixs;
    }
    static int[] numMaxCRNN(float[] a,int length) {
        float value=-Float.MAX_VALUE;
        int[] ixs = new int[10000];
        Arrays.fill(ixs, -1);
        int j=0,now=0;
        for (int i = 0; i < a.length; i++) {
            if(j<length){
                if(a[i]>value){
                    value=a[i];
                    ixs[now]=j;
                }
                j++;
            }
            else {
                value=a[i];
                j = 0;
                now++;
                ixs[now]=j;
                i--;
            }
        }
        return ixs;
    }
    public static int[] topK(float[] a, final int topk) {
        float values[] = new float[topk];
        Arrays.fill(values, -Float.MAX_VALUE);
        int ixs[] = new int[topk];
        Arrays.fill(ixs, -1);

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < topk; j++) {
                if (a[i] > values[j]) {
                    for (int k = topk - 1; k >= j + 1; k--) {
                        values[k] = values[k - 1];
                        ixs[k] = ixs[k - 1];
                    }
                    values[j] = a[i];
                    ixs[j] = i;
                    break;
                }
            }
        }
        return ixs;
    }
    static void bitmapToFloatBuffer(
            final Bitmap bitmap,
            final int width,
            final int height,
            final FloatBuffer outBuffer,
            final int outBufferOffset) {

        final int pixelsCount = height * width;
        final int[] pixels = new int[pixelsCount];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixelsCount; i++) {
            final int c = pixels[i];
            float bF = (((c) & 0xff) / 255.0f - 0.588f) / 0.193f;
            outBuffer.put(outBufferOffset + i, bF);
        }
    }
    public static String saveBitmap(String savePath, Bitmap mBitmap) {
        File filePic;
        try {
            filePic = new File(savePath  + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }
    public byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();

    }
    public int[] convertByteToColor(byte[] data){
        int size = data.length;
        if (size == 0){
            return null;
        }

        int arg = 0;
        if (size % 3 != 0){
            arg = 1;
        }

        int []color = new int[size / 3 + arg];
        int red, green, blue;

        if (arg == 0){
            for(int i = 0; i < color.length; ++i){
                red = convertByteToInt(data[i * 3]);
                green = convertByteToInt(data[i * 3 + 1]);
                blue = convertByteToInt(data[i * 3 + 2]);

                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }
        }else{
            for(int i = 0; i < color.length - 1; ++i){
                red = convertByteToInt(data[i * 3]);
                green = convertByteToInt(data[i * 3 + 1]);
                blue = convertByteToInt(data[i * 3 + 2]);
                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }

            color[color.length - 1] = 0xFF000000;
        }

        return color;
    }
    public static int convertByteToInt(byte data){

        int heightBit = (int) ((data>>4) & 0x0F);
        int lowBit = (int) (0x0F & data);

        return heightBit * 16 + lowBit;
    }

}
