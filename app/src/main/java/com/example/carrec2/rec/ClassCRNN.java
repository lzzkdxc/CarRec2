package com.example.carrec2.rec;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;

import java.nio.FloatBuffer;

public class ClassCRNN extends Thread{
    public static Module module = null;
    public static Bitmap bitmap_plate;
    public static TextView textView;
    @Override
    public void run() {
        final FloatBuffer floatBuffer = Tensor.allocateFloatBuffer( 160 * 32);
        Bitmap bmp1 = MyUtils.scaleBitmap(bitmap_plate, 160, 32);
        MyUtils.showBitmap(MyUtils.imageView,bmp1);
        MyUtils.bitmapToFloatBuffer(bmp1, 160, 32, floatBuffer, 0);
        final Tensor inputTensor = Tensor.fromBlob(floatBuffer, new long[]{1, 1, 32, 160});
        assert module != null;
        Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
        final float[] scores = outputTensor.getDataAsFloatArray();
        int[] maxx = MyUtils.numMaxCRNN(scores, 80);
        final String ixs = jiema(maxx);
        String result = MyUtils.quChong(ixs);
        MyUtils.showTextView(textView,result);
    }
    static private String jiema(int[] ixs) {
        StringBuilder out= new StringBuilder();
        int x;
        for (int ix : ixs) {
            x = ix;
            while (x < 0) {
                x++;
            }
                out.append(MyUtils.alphabet.charAt(x));
        }
        return out.toString();
    }

    public static void go() {
        int ww=160;
        int hh=32;

        final FloatBuffer floatBuffer = Tensor.allocateFloatBuffer( ww * hh);
        Bitmap bmp1 = MyUtils.scaleBitmap(bitmap_plate, ww, hh);
//        MyUtils.showBitmap(MyUtils.imageView,bmp1);
        MyUtils.bitmapToFloatBuffer(bmp1, ww, hh, floatBuffer, 0);
        final Tensor inputTensor = Tensor.fromBlob(floatBuffer, new long[]{1, 1, hh, ww});
        assert module != null;
        Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
        final float[] scores = outputTensor.getDataAsFloatArray();
        int[] maxx = MyUtils.numMaxCRNN(scores, 80);
        final String ixs = jiema(maxx);
        String result = MyUtils.quChong(ixs);
        MyUtils.showTextView(textView,result);
        MyUtils.crnn_out=result;
    }
}
